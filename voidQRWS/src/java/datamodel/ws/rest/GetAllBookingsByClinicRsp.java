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
public class GetAllBookingsByClinicRsp {
    private List<BookingEntity> bookings;
    
    public GetAllBookingsByClinicRsp() {
        
    }
    
    public GetAllBookingsByClinicRsp(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }
    
}
