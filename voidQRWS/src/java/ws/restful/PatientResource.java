package ws.restful;

import datamodel.ws.rest.CreatePatientReq;
import datamodel.ws.rest.CreatePatientRsp;
import datamodel.ws.rest.ErrorRsp;
import datamodel.ws.rest.PatientLoginRsp;
import datamodel.ws.rest.RetrieveAllActivatedClinicsRsp;
import datamodel.ws.rest.UpdatePatientReq;

import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.PatientEntity;
import ejb.session.stateless.ClinicSessionBeanLocal;
import ejb.session.stateless.PatientSessionBeanLocal;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PatientNotFoundException;
import util.exception.UpdatePatientException;

@Path("Patient")
public class PatientResource {

    PatientSessionBeanLocal patientSessionBean = lookupPatientSessionBeanLocal();
    
    @Context
    private UriInfo context;

    private final PatientSessionBeanLocal patientSessionBeanLocal;
   
    public PatientResource() {
        patientSessionBeanLocal = lookupPatientSessionBeanLocal();
    }

    @Path("patientLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response patientLogin(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            System.out.println("username" + username);
            PatientEntity patientEntity = patientSessionBeanLocal.patientLogin(username, password);

//            for (BookingEntity be: patientEntity.getBookingEntities()) {
//                be.getPatientEntity().setBookingEntities(null);
//               // be.setPatientEntity(null);
//            }
            System.out.println("********** PatientResource.patientLogin(): Patient " + patientEntity.getEmail() + " login remotely via web service");

            patientEntity.setPassword(null);
            patientEntity.setSalt(null);
            patientEntity.getBookingEntities().clear();
           
            
            //patientEntity.setBookingEntities(null);

            return Response.status(Status.OK).entity(new PatientLoginRsp(patientEntity)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            System.out.println("exxx" + ex.getMessage());
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPatient(CreatePatientReq createPatientReq) {

        if (createPatientReq != null) {
            System.out.println("run");
            try {

                PatientEntity patientEntity = patientSessionBeanLocal.createNewPatient(createPatientReq.getPatientEntity());
                CreatePatientRsp createPatientRsp = new CreatePatientRsp(patientEntity.getUserId());

                return Response.status(Response.Status.OK).entity(createPatientRsp).build();
            } catch (InputDataValidationException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new patient request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePatientDetails(UpdatePatientReq updatePatientReq) {

        if (updatePatientReq != null) {
            try {
                System.out.println("**********  " );
                
                PatientEntity patientEntity = patientSessionBeanLocal.patientLogin(updatePatientReq.getEmail(), updatePatientReq.getPassword());
                
                System.out.println("********** PatientResource.updatePatientDetails(): Patient " + patientEntity.getFirstName() + " login remotely via web service");
                patientSessionBeanLocal.updatePatient(updatePatientReq.getPatientEntity());

                return Response.status(Response.Status.OK).build();

            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (InputDataValidationException ex) {
                ErrorRsp errorRsp = new ErrorRsp("Invalid data vaidation exception");

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (PatientNotFoundException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (UpdatePatientException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }

        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid patient to update details");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();

        }
    }
    
    

    private PatientSessionBeanLocal lookupPatientSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PatientSessionBeanLocal) c.lookup("java:global/voidQ/voidQ-ejb/PatientSessionBean!ejb.session.stateless.PatientSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

   
    
    

}
