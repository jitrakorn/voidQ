/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.ws.rest;

import ejb.entity.PatientEntity;

/**
 *
 * @author pamela
 */
public class CreateBookingReq {
    private String email;
    private String visitReason;
    private String clinicId;

    public CreateBookingReq() {
    }

    public CreateBookingReq(String email, String visitReason, String clinicId) {
        this.email = email;
        this.visitReason = visitReason;
        this.clinicId = clinicId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }
    
}
