package datamodel.ws.rest;

import ejb.entity.PatientEntity;




public class PatientLoginRsp
{
    private PatientEntity patientEntity;

    
    
    public PatientLoginRsp()
    {
    }

    
    
    public PatientLoginRsp(PatientEntity patientEntity)
    {
        this.patientEntity = patientEntity;
    }

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }

    
    
   
}