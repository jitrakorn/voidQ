/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.PatientEntity;
import ejb.entity.StaffEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.PatientNotFoundException;
import util.exception.UpdatePatientException;

/**
 *
 * @author mingxuan
 */
@Local
public interface PatientSessionBeanLocal {

    public List<PatientEntity> retrieveAllPatientsByClinic(StaffEntity staffEntity);

    public void updatePatient(PatientEntity patient) throws InputDataValidationException, PatientNotFoundException, UpdatePatientException;

    public PatientEntity retrievePatientByPatientId(Long patientId) throws PatientNotFoundException;
    
}
