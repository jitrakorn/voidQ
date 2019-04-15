/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.ws.rest;

/**
 *
 * @author pamela
 */
public class MakePaymentReq {
    private String bookingId;
   

    public MakePaymentReq() {
    }

    public MakePaymentReq(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    
    
}
