/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.BookingEntity;
import ejb.entity.PatientEntity;
import ejb.entity.StaffEntity;
import ejb.entity.UserEntity;
import java.util.List;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PatientNotFoundException;
import util.exception.UpdatePasswordException;
import util.exception.UpdatePatientException;

/**
 *
 * @author mingxuan
 */
public interface PatientSessionBeanLocal {

//    public List<PatientEntity> retrieveAllPatientsByClinic(StaffEntity staffEntity);

    public void updatePatient(PatientEntity patient) throws InputDataValidationException, PatientNotFoundException, UpdatePatientException;

    public PatientEntity retrievePatientByPatientId(Long patientId) throws PatientNotFoundException;
    
    public PatientEntity createNewPatient(PatientEntity newPatient) throws InputDataValidationException;
    
    public PatientEntity retrievePatientByEmail(String email) throws PatientNotFoundException;
    
    public PatientEntity patientLogin(String email, String password) throws InvalidLoginCredentialException;
    
    public Integer retrieveCurrentBookingQueuePosition(Long bookingId, Long clinicId);
    
    public BookingEntity retrieveCurrentBooking(Long patientId);

    public void updatePassword(UserEntity patient, String oldPassword, String newPassword) throws UpdatePasswordException;
    
}
