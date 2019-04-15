package ws.restful;

import ejb.session.stateless.BookingSessionBeanLocal;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;

@Path("Booking")
public class BookingResource {

    BookingSessionBeanLocal bookingSessionBean = lookupBookingSessionBeanLocal();

    @Context
    private UriInfo context;

    private final BookingSessionBeanLocal bookingSessionBeanLocal;

    public BookingResource() {
        bookingSessionBeanLocal = lookupBookingSessionBeanLocal();
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

}
