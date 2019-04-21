/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.ws.rest;

import ejb.entity.BookingEntity;
import java.util.List;

/**
 *
 * @author terencetay
 */
public class RetrievePastBookingsRsp {
    private List<BookingEntity> bookingEntities;
    
    public RetrievePastBookingsRsp(){
        
    }
    
    public RetrievePastBookingsRsp(List<BookingEntity> bookingEntities) {
        this.bookingEntities = bookingEntities;
    }

    public List<BookingEntity> getBookingEntities() {
        return bookingEntities;
    }

    public void setBookingEntities(List<BookingEntity> bookingEntities) {
        this.bookingEntities = bookingEntities;
    }

  
}
