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
public class UpdatePatientPasswordReq {
    private PatientEntity patientEntity;
    private String oldPassword;
    private String newPassword;

    public UpdatePatientPasswordReq() {
    }

    public UpdatePatientPasswordReq(String oldPassword, String newPassword, PatientEntity patientEntity) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.patientEntity = patientEntity;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }

  

    
    
}