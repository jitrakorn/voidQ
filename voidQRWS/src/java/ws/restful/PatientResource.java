package ws.restful;

import datamodel.ws.rest.ErrorRsp;
import datamodel.ws.rest.PatientLoginRsp;
import ejb.entity.PatientEntity;
import ejb.session.stateless.PatientSessionBeanLocal;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InvalidLoginCredentialException;



@Path("Patient")
public class PatientResource 
{

    PatientSessionBeanLocal patientSessionBean1 = lookupPatientSessionBeanLocal();

    PatientSessionBeanLocal patientSessionBean = lookupPatientSessionBeanLocal();
    @Context
    private UriInfo context;
    
  
    
    private final PatientSessionBeanLocal patientSessionBeanLocal;

    
    
    public PatientResource() 
    {
        patientSessionBeanLocal = lookupPatientSessionBeanLocal();
    }
    
    
    
    @Path("patientLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response patientLogin(@QueryParam("username") String username, 
                                @QueryParam("password") String password)
    {
        try
        {
            System.out.println("username" + username);
            PatientEntity patientEntity = patientSessionBeanLocal.patientLogin(username, password);
            
        
            System.out.println("********** PatientResource.patientLogin(): Patient " + patientEntity.getEmail()+ " login remotely via web service");

            patientEntity.setPassword(null);
            patientEntity.setSalt(null);
            patientEntity.getBookingEntities().clear(); 
          
            
            
            return Response.status(Status.OK).entity(new PatientLoginRsp(patientEntity)).build();
        }
        catch(InvalidLoginCredentialException ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        }
        catch(Exception ex)
            
        {
            System.out.println("exxx" + ex.getMessage());
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
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
