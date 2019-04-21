/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.NurseEntity;
import ejb.entity.PatientEntity;
import ejb.entity.StaffEntity;
import ejb.entity.UserEntity;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.ApplicationStatus;
import util.enumeration.Availability;
import util.exception.ClinicNotActivatedException;
import util.exception.DeletePartnerException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;
import util.exception.PatientNotFoundException;
import util.exception.UpdatePartnerException;
import util.exception.UpdatePasswordException;
import util.security.CryptographicHelper;

/**
 *
 * @author mingxuan
 */
@Stateless
@Local(PartnerSessionBeanLocal.class)
public class PartnerSessionBean implements PartnerSessionBeanLocal {

    @PersistenceContext(unitName = "voidQ-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PartnerSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public ClinicEntity createNewPartner(ClinicEntity newClinic) throws InputDataValidationException {

        Set<ConstraintViolation<ClinicEntity>> constraintViolations = validator.validate(newClinic);

        if (constraintViolations.isEmpty()) {

            em.persist(newClinic);
            em.flush();

            return newClinic;
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public StaffEntity createNewStaff(StaffEntity newStaff) {
        em.persist(newStaff);
        em.flush();

        return newStaff;
    }
    
    @Override
   public DoctorEntity createNewDoctor(DoctorEntity newStaff) {
        em.persist(newStaff);
        em.flush();

        return newStaff;
    }
   
   @Override
     public NurseEntity createNewNurse(NurseEntity newStaff) {
        em.persist(newStaff);
        em.flush();

        return newStaff;
    }
   
    @Override
    public StaffEntity retrievePartnerByEmail(String email) throws PartnerNotFoundException {
        Query query = em.createQuery("SELECT d FROM DoctorEntity d WHERE d.email = :inEmail");
        query.setParameter("inEmail", email);

        try {
            return (StaffEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            Query query2 = em.createQuery("SELECT n FROM NurseEntity n WHERE n.email = :inEmail");
            query2.setParameter("inEmail", email);

            try {
                return (StaffEntity) query2.getSingleResult();
            } catch (NoResultException | NonUniqueResultException ex2) {
                throw new PartnerNotFoundException("Partner Email " + email + " does not exist!");
            }
        }
    }

    @Override
    public List<ClinicEntity> retrieveAllPartners() {
        Query query = em.createQuery("SELECT c FROM ClinicEntity c");

        return query.getResultList();
    }

    @Override
    public List<DoctorEntity> retrieveAllDoctors() {
        Query query = em.createQuery("SELECT c FROM DoctorEntity c");

        return query.getResultList();
    }

    @Override
    public List<NurseEntity> retrieveAllNurses() {
        Query query = em.createQuery("SELECT c FROM NurseEntity c");

        return query.getResultList();
    }

    @Override
    public ClinicEntity retrievePartnerByPartnerId(Long clinicId) throws PartnerNotFoundException {
        ClinicEntity clinic = em.find(ClinicEntity.class, clinicId);

        if (clinic != null) {
            return clinic;
        } else {
            throw new PartnerNotFoundException("Clinic ID " + clinicId + " does not exist!");
        }
    }

    @Override
    public ClinicEntity getPartnerById(Long clinicId) {
        return em.find(ClinicEntity.class, clinicId);
    }

    @Override
    public List<ClinicEntity> retrieveUnApprovedApplications() {
        Query query = em.createQuery("SELECT c FROM ClinicEntity c where c.applicationStatus = :inEnum");
        query.setParameter("inEnum", ApplicationStatus.NOTACTIVATED);

        return query.getResultList();

    }

    @Override
    public StaffEntity retrieveStaffByStaffId(Long staffId) throws PartnerNotFoundException {
        StaffEntity staff = em.find(StaffEntity.class, staffId);

        if (staff != null) {
            return staff;
        } else {
            throw new PartnerNotFoundException("Staff ID " + staffId + " does not exist!");
        }
    }

    @Override
    public DoctorEntity retrieveDoctorByStaffId(Long staffId) throws PartnerNotFoundException {
        DoctorEntity doctor = em.find(DoctorEntity.class, staffId);

        if (doctor != null) {
            return doctor;
        } else {
            throw new PartnerNotFoundException("Staff ID " + staffId + " does not exist!");
        }
    }

    @Override
    public NurseEntity retrieveNurseByStaffId(Long staffId) throws PartnerNotFoundException {
        NurseEntity nurse = em.find(NurseEntity.class, staffId);

        if (nurse != null) {
            return nurse;
        } else {
            throw new PartnerNotFoundException("Staff ID " + staffId + " does not exist!");
        }
    }

    @Override
    public void updatePartner(ClinicEntity clinic) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException {
        // Updated in v4.1 to update selective attributes instead of merging the entire state passed in from the client
        // Also check for existing staff before proceeding with the update

        // Updated in v4.2 with bean validation
        Set<ConstraintViolation<ClinicEntity>> constraintViolations = validator.validate(clinic);

        if (constraintViolations.isEmpty()) {
            if (clinic.getClinicId() != null) {
                ClinicEntity partnerToUpdate = retrievePartnerByPartnerId(clinic.getClinicId());

                if (partnerToUpdate.getClinicId().equals(clinic.getClinicId())) {
                    partnerToUpdate.setAddress(clinic.getAddress());
                    partnerToUpdate.setClinicName(clinic.getClinicName());
                    partnerToUpdate.setDescription(clinic.getDescription());
                    partnerToUpdate.setUnitPrice(clinic.getUnitPrice());

                } else {
                    throw new UpdatePartnerException("ID of partner record to be updated does not match the existing record");
                }
            } else {
                throw new PartnerNotFoundException("Partner ID not provided for partner to be updated");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public void updateStaff(StaffEntity staff) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException {
        // Updated in v4.1 to update selective attributes instead of merging the entire state passed in from the client
        // Also check for existing staff before proceeding with the update

        // Updated in v4.2 with bean validation
        DoctorEntity doctorCheck = new DoctorEntity();
        NurseEntity nurseCheck = new NurseEntity();

        Set<ConstraintViolation<StaffEntity>> constraintViolations = validator.validate(staff);

        if (constraintViolations.isEmpty()) {
            if (staff.getUserId() != null) {
                Object staffClass = staff.getClass();
                System.out.println(staffClass);

                if (staff.getClass().equals(doctorCheck.getClass())) {
                    doctorCheck = (DoctorEntity) retrievePartnerByEmail(staff.getEmail());

                    if (doctorCheck.getUserId().equals(staff.getUserId())) {
                        doctorCheck.setEmail(staff.getEmail());
                        doctorCheck.setFirstName(staff.getFirstName());
                        doctorCheck.setLastName(staff.getLastName());
                    } else {
                        throw new UpdatePartnerException("Email of doctor record to be updated does not match the existing record");
                    }
                } else if (staff.getClass().equals(nurseCheck.getClass())) {
                    nurseCheck = (NurseEntity) retrievePartnerByEmail(staff.getEmail());

                    if (nurseCheck.getUserId().equals(staff.getUserId())) {
                        nurseCheck.setEmail(staff.getEmail());
                        nurseCheck.setFirstName(staff.getFirstName());
                        nurseCheck.setLastName(staff.getLastName());
                    } else {
                        throw new UpdatePartnerException("Email of nurse record to be updated does not match the existing record");
                    }
                }
            } else {
                throw new PartnerNotFoundException("Staff ID not provided for staff to be updated");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessagea(constraintViolations));
        }
    }

    @Override
    public StaffEntity emailLogin(String email, String password) throws InvalidLoginCredentialException, ClinicNotActivatedException {
        try {
            StaffEntity staffEntity = retrievePartnerByEmail(email);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + staffEntity.getSalt()));

            if (staffEntity.getClinicEntity().getApplicationStatus().equals(ApplicationStatus.NOTACTIVATED)) {
                throw new ClinicNotActivatedException("Clinic is not activated");
            }

            if (staffEntity.getPassword().equals(passwordHash)) {
                return staffEntity;
            } else {
                throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
            }
        } catch (PartnerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
        }
    }

    @Override
    public void deletePartner(Long partnerId) throws PartnerNotFoundException, DeletePartnerException {
        ClinicEntity partner = retrievePartnerByPartnerId(partnerId);

        /*if(partner.getSaleTransactionEntities().isEmpty())
        {
            em.remove(partner);
        } 
        else
        {
            // New in v4.1 to prevent deleting staff with existing sale transaction(s)
            throw new DeletePartnerException("Partner ID " + partnerId + " is associated with existing sale transaction(s) and cannot be deleted!");
        }*/
        // have to check if partner is associated with something 
        em.remove(partner);
    }

    @Override
    public void deleteStaff(Long partnerId) throws PartnerNotFoundException, DeletePartnerException {
        StaffEntity partner = retrieveStaffByStaffId(partnerId);

        /*if(partner.getSaleTransactionEntities().isEmpty())
        {
            em.remove(partner);
        } 
        else
        {
            // New in v4.1 to prevent deleting staff with existing sale transaction(s)
            throw new DeletePartnerException("Partner ID " + partnerId + " is associated with existing sale transaction(s) and cannot be deleted!");
        }*/
        // have to check if partner is associated with something 
        em.remove(partner);
    }
    
    @Override
     public void deleteDoctor(Long partnerId) throws PartnerNotFoundException, DeletePartnerException {
        DoctorEntity partner = retrieveDoctorByStaffId(partnerId);

        /*if(partner.getSaleTransactionEntities().isEmpty())
        {
            em.remove(partner);
        } 
        else
        {
            // New in v4.1 to prevent deleting staff with existing sale transaction(s)
            throw new DeletePartnerException("Partner ID " + partnerId + " is associated with existing sale transaction(s) and cannot be deleted!");
        }*/
        // have to check if partner is associated with something 
        em.remove(partner);
    }

     @Override
      public void deleteNurse(Long partnerId) throws PartnerNotFoundException, DeletePartnerException {
        NurseEntity partner = retrieveNurseByStaffId(partnerId);

        /*if(partner.getSaleTransactionEntities().isEmpty())
        {
            em.remove(partner);
        } 
        else
        {
            // New in v4.1 to prevent deleting staff with existing sale transaction(s)
            throw new DeletePartnerException("Partner ID " + partnerId + " is associated with existing sale transaction(s) and cannot be deleted!");
        }*/
        // have to check if partner is associated with something 
        em.remove(partner);
    }
    @Override
    public Boolean hasAvailableDoctors(ClinicEntity currentClinic) {
        List<DoctorEntity> doctors = em.createQuery("SELECT d FROM DoctorEntity d WHERE d.status = :status AND d.clinicEntity = :clinic")
                .setParameter("status", Availability.AVAILABLE)
                .setParameter("clinic", currentClinic)
                .getResultList();

        if (doctors.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public DoctorEntity appointAvailableDoctor(ClinicEntity currentClinic, BookingEntity booking) {
        List<DoctorEntity> doctors = em.createQuery("SELECT d FROM DoctorEntity d WHERE d.status = :status AND d.clinicEntity = :clinic")
                .setParameter("status", Availability.AVAILABLE)
                .setParameter("clinic", currentClinic)
                .getResultList();

        DoctorEntity appointedDoctor = doctors.get(0);

        booking.setDoctorEntity(appointedDoctor);
        appointedDoctor.setStatus(Availability.BUSY);

        em.merge(booking);
        em.merge(currentClinic);
        em.merge(appointedDoctor);
        em.flush();

        return appointedDoctor;
    }

    @Override
    public DoctorEntity availDoctor(DoctorEntity appointedDoctor) {
        appointedDoctor.setStatus(Availability.AVAILABLE);
        em.merge(appointedDoctor);
        em.flush();

        return appointedDoctor;
    }

    @Override
    public List<DoctorEntity> getDoctorsByClinicId(Long clinicId) {
        ClinicEntity clinic = em.find(ClinicEntity.class, clinicId);
        return em.createQuery("SELECT d FROM DoctorEntity d WHERE d.clinicEntity = :clinic")
                .setParameter("clinic", clinic)
                .getResultList();
    }

    @Override
    public List<NurseEntity> getNursesByClinicId(Long clinicId) {
        ClinicEntity clinic = em.find(ClinicEntity.class, clinicId);
        return em.createQuery("SELECT n FROM NurseEntity n WHERE n.clinicEntity = :clinic")
                .setParameter("clinic", clinic)
                .getResultList();
    }
  
  public void updateDoctor(DoctorEntity staff) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException {
        // Updated in v4.1 to update selective attributes instead of merging the entire state passed in from the client
        // Also check for existing staff before proceeding with the update

        // Updated in v4.2 with bean validation
        Set<ConstraintViolation<DoctorEntity>> constraintViolations = validator.validate(staff);

        if (constraintViolations.isEmpty()) {
            if (staff.getUserId() != null) {
                DoctorEntity staffToUpdate = retrieveDoctorByStaffId(staff.getUserId());

                if (staffToUpdate.getUserId().equals(staff.getUserId())) {
                    staffToUpdate.setEmail(staff.getEmail());
                    staffToUpdate.setFirstName(staff.getFirstName());
                    staffToUpdate.setLastName(staff.getLastName());
                } else {
                    throw new UpdatePartnerException("Email of staff record to be updated does not match the existing record");
                }
            } else {
                throw new PartnerNotFoundException("Staff ID not provided for stadf to be updated");
            }
        } else {
            // throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public void updateNurse(NurseEntity staff) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException {
        // Updated in v4.1 to update selective attributes instead of merging the entire state passed in from the client
        // Also check for existing staff before proceeding with the update

        // Updated in v4.2 with bean validation
        Set<ConstraintViolation<NurseEntity>> constraintViolations = validator.validate(staff);

        if (constraintViolations.isEmpty()) {
            if (staff.getUserId() != null) {
                NurseEntity staffToUpdate = retrieveNurseByStaffId(staff.getUserId());

                if (staffToUpdate.getUserId().equals(staff.getUserId())) {
                    staffToUpdate.setEmail(staff.getEmail());
                    staffToUpdate.setFirstName(staff.getFirstName());
                    staffToUpdate.setLastName(staff.getLastName());
                } else {
                    throw new UpdatePartnerException("Email of staff record to be updated does not match the existing record");
                }
            } else {
                throw new PartnerNotFoundException("Staff ID not provided for stadf to be updated");
            }
        } else {
            // throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public void updateDoctorPassword(DoctorEntity staff, String oldPassword, String newPassword) throws UpdatePasswordException, PartnerNotFoundException {
        try {
            Set<ConstraintViolation<DoctorEntity>> constraintViolations = validator.validate(staff);

            if (constraintViolations.isEmpty()) {
                if (staff.getUserId() != null) {
                    DoctorEntity staffToUpdate = retrieveDoctorByStaffId(staff.getUserId());
                    System.out.println("CHECKoldpw" + oldPassword);
                    String oldPasswordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(oldPassword + staffToUpdate.getSalt()));
                    System.out.println("CHECK" + oldPasswordHash);
                    System.out.println("OLDHASH" + staffToUpdate.getPassword());
                    if (oldPasswordHash.equalsIgnoreCase(staffToUpdate.getPassword())) {
                        if (!oldPassword.equals(newPassword)) {
                            System.out.println("HI im working");
                            staffToUpdate.setPassword(newPassword);
                        } else {
                            throw new UpdatePasswordException("new password must be different!");
                        }
                    } else {
                        throw new UpdatePasswordException("Old password does not match original password!");

                    }
                }
            }
        } catch (PartnerNotFoundException ex) {

        }

    }

    @Override
    public void updateNursePassword(NurseEntity staff, String oldPassword, String newPassword) throws UpdatePasswordException, PartnerNotFoundException {
        try {
            Set<ConstraintViolation<NurseEntity>> constraintViolations = validator.validate(staff);

            if (constraintViolations.isEmpty()) {
                if (staff.getUserId() != null) {
                    NurseEntity staffToUpdate = retrieveNurseByStaffId(staff.getUserId());
                    System.out.println("CHECKoldpw" + oldPassword);
                    String oldPasswordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(oldPassword + staffToUpdate.getSalt()));
                    System.out.println("CHECK" + oldPasswordHash);
                    System.out.println("OLDHASH" + staffToUpdate.getPassword());
                    if (oldPasswordHash.equalsIgnoreCase(staffToUpdate.getPassword())) {
                        if (!oldPassword.equals(newPassword)) {
                            System.out.println("HI im working");
                            staffToUpdate.setPassword(newPassword);
                            System.out.println("HI");
                        } else {
                            throw new UpdatePasswordException("new password must be different!");
                        }
                    } else {
                        throw new UpdatePasswordException("Old password does not match original password!");

                    }
                }
            }
        } catch (PartnerNotFoundException ex) {

        }

    }

    @Override
    public StaffEntity emailLogin(String email, String password) throws InvalidLoginCredentialException {

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ClinicEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    private String prepareInputDataValidationErrorsMessagea(Set<ConstraintViolation<StaffEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
