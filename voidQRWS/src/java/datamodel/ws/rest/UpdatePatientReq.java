/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.ws.rest;

import ejb.entity.PatientEntity;
import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author pamela
 */
public class UpdatePatientReq {
    private String email;
    private String password;
    private PatientEntity patientEntity;

    public UpdatePatientReq() {
    }

    public UpdatePatientReq(String email, String password, PatientEntity patientEntity) {
        this.email = email;
        this.password = password;
        this.patientEntity = patientEntity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }

  

    
    
}
