package datamodel.ws.rest;




public class CreateBookingRsp
{
    private Long bookingId;

    
    
    public CreateBookingRsp()
    {
    }

    
    
    public CreateBookingRsp(Long bookingId)
    {
        this.bookingId = bookingId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    
    
   
}