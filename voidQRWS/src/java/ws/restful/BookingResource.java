package ws.restful;

import datamodel.ws.rest.CheckInReq;
import datamodel.ws.rest.CreateBookingReq;
import datamodel.ws.rest.ErrorRsp;
import datamodel.ws.rest.GetAllBookingsByClinicRsp;
import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.PatientEntity;
import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.session.stateless.PatientSessionBeanLocal;
import java.util.Date;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.enumeration.BookingStatus;
import util.exception.PatientNotFoundException;

@Path("Booking")
public class BookingResource {

    PatientSessionBeanLocal patientSessionBean = lookupPatientSessionBeanLocal();

    PartnerSessionBeanLocal partnerSessionBean = lookupPartnerSessionBeanLocal();

    BookingSessionBeanLocal bookingSessionBean = lookupBookingSessionBeanLocal();
    

    @Context
    private UriInfo context;

    private final BookingSessionBeanLocal bookingSessionBeanLocal;

    public BookingResource() {
        bookingSessionBeanLocal = lookupBookingSessionBeanLocal();
    }

    @Path("getAllBookingsByClinic")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookingsByClinic(@QueryParam("clinicEntityId") String clinicEntityId) {
        try {
            List<BookingEntity> bookings = bookingSessionBeanLocal.getBookingsByClinicId(Long.parseLong(clinicEntityId));

            for (BookingEntity booking : bookings) {
                booking.setDoctorEntity(null);
                booking.setNurseEntity(null);
                booking.setPatientEntity(null);
                booking.setClinicEntity(null);
            }

            return Response.status(Status.OK).entity(new GetAllBookingsByClinicRsp(bookings)).build();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("createBooking")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(CreateBookingReq createBookingReq) {

        if (createBookingReq != null) {
            try {
                PatientEntity patient = patientSessionBean.retrievePatientByEmail(createBookingReq.getEmail());
                ClinicEntity clinic = partnerSessionBean.getPartnerById(Long.parseLong(createBookingReq.getClinicId()));
                bookingSessionBean.createBooking(new BookingEntity(BookingStatus.BOOKED, new Date(), clinic, patient, createBookingReq.getVisitReason()));
            
                return Response.status(Status.OK).build();
            } catch (PatientNotFoundException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.NOT_FOUND).entity(errorRsp).build();
            }
        } 
        else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new booking");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build(); 
        }
    }
    
    @Path("checkIn")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response checkin(CheckInReq checkInReq) {
        if (checkInReq != null) {
            BookingEntity currentBooking = bookingSessionBean.getBookingById(Long.parseLong(checkInReq.getBookingId()));
            
            if (partnerSessionBean.hasAvailableDoctors(currentBooking.getClinicEntity())) {
                currentBooking.setStatus(BookingStatus.VISITING);
                partnerSessionBean.appointAvailableDoctor(currentBooking.getClinicEntity(), currentBooking);
            }

            bookingSessionBean.updateBooking(currentBooking);
            return Response.status(Status.OK).build();
        }
        else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new booking");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build(); 
        }
    }

    private BookingSessionBeanLocal lookupBookingSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (BookingSessionBeanLocal) c.lookup("java:global/voidQ/voidQ-ejb/BookingSessionBean!ejb.session.stateless.BookingSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PartnerSessionBeanLocal lookupPartnerSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PartnerSessionBeanLocal) c.lookup("java:global/voidQ/voidQ-ejb/PartnerSessionBean!ejb.session.stateless.PartnerSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
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
