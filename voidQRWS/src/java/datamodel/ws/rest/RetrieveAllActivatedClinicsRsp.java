/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.ws.rest;

import ejb.entity.ClinicEntity;
import java.util.List;

/**
 *
 * @author pamela
 */
public class RetrieveAllActivatedClinicsRsp {
    private List<ClinicEntity> clinicEntities;


public RetrieveAllActivatedClinicsRsp() {
}

public RetrieveAllActivatedClinicsRsp(List<ClinicEntity> clinicEntities) {
this.clinicEntities = clinicEntities;
}

 public List<ClinicEntity> getClinicEntities() {
        return clinicEntities;
    }

    public void setClinicEntities(List<ClinicEntity> clinicEntities) {
        this.clinicEntities = clinicEntities;
    }

}
