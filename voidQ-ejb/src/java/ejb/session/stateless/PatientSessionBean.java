/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.PatientEntity;
import ejb.entity.UserEntity;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.BookingStatus;
import util.exception.BookingNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PatientNotFoundException;
import util.exception.UpdatePasswordException;
import util.exception.UpdatePatientException;
import util.security.CryptographicHelper;

/**
 *
 * @author mingxuan
 */
@Stateless
@Local(PatientSessionBeanLocal.class)
public class PatientSessionBean implements PatientSessionBeanLocal {

    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    @PersistenceContext(unitName = "voidQ-ejbPU")
    private EntityManager em;

    public PatientSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public PatientEntity createNewPatient(PatientEntity newPatient) throws InputDataValidationException {
        Set<ConstraintViolation<PatientEntity>> constraintViolations = validator.validate(newPatient);

        if (constraintViolations.isEmpty()) {
            em.persist(newPatient);
            em.flush();

            return newPatient;
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

//    @Override
//    public List<PatientEntity> retrieveAllPatientsByClinic(StaffEntity staffEntity) {
//        Query query = em.createQuery("SELECT p FROM PatientEntity p where p.clinicEntity = :inClinic");
//        query.setParameter("inClinic", staffEntity.getClinicEntity());
//        return query.getResultList();
//    }
    @Override
    public PatientEntity retrievePatientByPatientId(Long patientId) throws PatientNotFoundException {
        PatientEntity patient = em.find(PatientEntity.class, patientId);

        if (patient != null) {
            return patient;
        } else {
            throw new PatientNotFoundException("Patient ID " + patientId + " does not exist!");
        }
    }

    @Override
    public PatientEntity retrievePatientByEmail(String email) throws PatientNotFoundException {
        try {
            return (PatientEntity) em.createQuery("SELECT p FROM PatientEntity p WHERE p.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NonUniqueResultException | NoResultException ex) {
            throw new PatientNotFoundException("Patient does not exist!");
        }
    }

   @Override
    public void updatePatient(PatientEntity patient) throws InputDataValidationException, PatientNotFoundException, UpdatePatientException {
        // Updated in v4.1 to update selective attributes instead of merging the entire state passed in from the client
        // Also check for existing staff before proceeding with the update

        // Updated in v4.2 with bean validation
        //   Set<ConstraintViolation<PatientEntity>> constraintViolations = validator.validate(patient);
//
        //  if (constraintViolations.isEmpty()) {
        if (patient.getUserId() != null) {
            PatientEntity patientToUpdate = retrievePatientByPatientId(patient.getUserId());

            if (patientToUpdate.getEmail().equals(patient.getEmail())) {
                patientToUpdate.setFirstName(patient.getFirstName());
                patientToUpdate.setLastName(patient.getLastName());
                patientToUpdate.setPhoneNumber(patient.getPhoneNumber());

            } else {
                throw new UpdatePatientException("Username of patient record to be updated does not match the existing record");
            }
        } else {
            throw new PatientNotFoundException("Patient ID not provided for patient to be updated");
        }
//        } else {
//            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
//        }
    }

    
     @Override
    public void updatePassword(UserEntity patient, String oldPassword, String newPassword) throws UpdatePasswordException {
        try {
            PatientEntity patientToUpdate = null;
            
            Long patientId = patient.getUserId();
            System.out.println("CHECKoldpw" + oldPassword);
            patientToUpdate = retrievePatientByPatientId(patientId);
            String oldPasswordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(oldPassword + patientToUpdate.getSalt()));
            System.out.println("CHECK" + oldPasswordHash);
            System.out.println("OLDHASH" + patientToUpdate.getPassword());
            if (oldPasswordHash.equalsIgnoreCase(patientToUpdate.getPassword())) {
                if (!oldPassword.equals(newPassword)) {
                    System.out.println("HI im working");
                    patientToUpdate.setPassword(newPassword);
                } else {
                    throw new UpdatePasswordException("new password must be different!");
                }
            } else {
                throw new UpdatePasswordException("Old password does not match original password!");

            }
        } catch (PatientNotFoundException ex) {
          
        }

     
    }


    @Override
    public PatientEntity patientLogin(String email, String password) throws InvalidLoginCredentialException {
        try {
            PatientEntity patientEntity = retrievePatientByEmail(email);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + patientEntity.getSalt()));

            if (patientEntity.getPassword().equals(passwordHash)) {
                return patientEntity;
            } else {
                throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
            }
        } catch (PatientNotFoundException ex) {
            throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
        }
    }

    @Override
    public Integer retrieveCurrentBookingQueuePosition(Long bookingId, Long clinicId) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        ClinicEntity clinic = em.find(ClinicEntity.class, clinicId);
        List<BookingEntity> bookings = em.createQuery("SELECT b FROM BookingEntity b WHERE b.clinicEntity = :clinic AND b.transactionDateTime > :date ORDER BY b.transactionDateTime ASC")
                .setParameter("clinic", clinic)
                .setParameter("date", today.getTime(), TemporalType.TIMESTAMP)
                .getResultList();

        Integer position = 1;
        BookingEntity currentBooking = em.find(BookingEntity.class, bookingId);
        for (BookingEntity booking : bookings) {
            if (booking.getBookingId().equals(currentBooking.getBookingId())) {
                break;
            }
            if (booking.getStatus().equals(BookingStatus.CHECKED_IN) || booking.getStatus().equals(BookingStatus.BOOKED)) {
                position++;
            }
        }

        return position;
    }

    @Override
    public BookingEntity retrieveCurrentBooking(Long patientId) throws BookingNotFoundException {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        BookingEntity booking;

        try {
            booking = (BookingEntity) em.createQuery("SELECT b FROM BookingEntity b WHERE b.patientEntity.userId= :patient AND b.transactionDateTime > :date ORDER BY b.transactionDateTime ASC")
                    .setParameter("patient", patientId)
                    .setParameter("date", today.getTime(), TemporalType.TIMESTAMP)
                    .getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new BookingNotFoundException("Booking not found for patient Id " + patientId + " does not exist!");
        }

        booking.getClinicEntity();
        booking.getPatientEntity();

        return booking;

    }
    
    @Override
    public List<BookingEntity> retrievePastBookings(Long patientId) throws BookingNotFoundException {
        List<BookingEntity> bookings;
        
        try {
            bookings = em.createQuery("SELECT b FROM BookingEntity b WHERE b.patientEntity.userId= :patient")
                    .setParameter("patient", patientId)
                    .getResultList();
            
            for (BookingEntity booking:bookings) {
            booking.getDoctorEntity();
            booking.getClinicEntity();
            booking.getPatientEntity();
        }
        } catch (NoResultException ex) {
            throw new BookingNotFoundException("No bookings found for patient Id " + patientId + "!"); 
        }
        
        return bookings;
        
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PatientEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
