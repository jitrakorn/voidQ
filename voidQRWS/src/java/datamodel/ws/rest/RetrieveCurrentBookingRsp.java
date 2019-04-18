/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.ws.rest;

import ejb.entity.BookingEntity;

/**
 *
 * @author terencetay
 */
public class RetrieveCurrentBookingRsp {
    private BookingEntity bookingEntity;
    
    public RetrieveCurrentBookingRsp(){
        
    }
    
    public RetrieveCurrentBookingRsp(BookingEntity bookingEntity) {
        this.bookingEntity = bookingEntity;
    }

    public BookingEntity getBookingEntity() {
        return bookingEntity;
    }

    public void setBookingEntity(BookingEntity bookingEntity) {
        this.bookingEntity = bookingEntity;
    }

  
}
