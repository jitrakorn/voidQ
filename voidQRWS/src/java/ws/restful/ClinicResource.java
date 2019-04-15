package ws.restful;

import datamodel.ws.rest.ErrorRsp;
import datamodel.ws.rest.RetrieveAllActivatedClinicsRsp;
import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.NurseEntity;
import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.ClinicSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
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
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("Clinic")
public class ClinicResource {

    ClinicSessionBeanLocal clinicSessionBean = lookupClinicSessionBeanLocal();

    @Context
    private UriInfo context;

    private final ClinicSessionBeanLocal clinicSessionBeanLocal;

    public ClinicResource() {

        clinicSessionBeanLocal = lookupClinicSessionBeanLocal();
    }

    @Path("retrieveAllActivatedClinics")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllClinics() {
        try {
            List<ClinicEntity> clinicEntities = clinicSessionBeanLocal.retrieveAllActivatedClinics();
            System.out.println("****** ran retrieveAllActivatedClinic****");
            for (ClinicEntity clinicEntity : clinicEntities) {
                // Unable to set the opposite side to null instead due to the one-to-many relationship foreign key
                clinicEntity.getBookingEntities().clear();
                clinicEntity.getDoctorEntities().clear();
                clinicEntity.getNurseEntities().clear();
            }

            return Response.status(Status.OK).entity(new RetrieveAllActivatedClinicsRsp(clinicEntities)).build();
        } catch (Exception ex) {
            System.out.println("*****ERROR LA****");
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private ClinicSessionBeanLocal lookupClinicSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ClinicSessionBeanLocal) c.lookup("java:global/voidQ/voidQ-ejb/ClinicSessionBean!ejb.session.stateless.ClinicSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
