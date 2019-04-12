package datamodel.ws.rest;

import ejb.entity.PatientEntity;
import java.util.List;



public class CreatePatientReq
{
    
    private PatientEntity patientEntity;
   
    
    
    public CreatePatientReq()
    {        
    }

    
    
    public CreatePatientReq(PatientEntity patientEntity) 
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