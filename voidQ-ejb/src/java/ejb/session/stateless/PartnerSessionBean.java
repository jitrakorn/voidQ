/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.ClinicEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.StaffEntity;
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
import util.exception.DeletePartnerException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;
import util.exception.UpdatePartnerException;
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
    public StaffEntity retrievePartnerByEmail(String email) throws PartnerNotFoundException {
        Query query = em.createQuery("SELECT d FROM DoctorEntity d WHERE d.email = :inEmail");
        query.setParameter("inEmail", email);

        try {
            return (StaffEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            Query query2 = em.createQuery("SELECT n FROM NurseEntity n WHERE n.email = :inEmail");
            query2.setParameter("inEmail", email);
            
            try{
                return (StaffEntity) query2.getSingleResult();
            }
            catch (NoResultException | NonUniqueResultException ex2) {
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
        Set<ConstraintViolation<StaffEntity>> constraintViolations = validator.validate(staff);

        if (constraintViolations.isEmpty()) {
            if (staff.getUserId() != null) {
                StaffEntity staffToUpdate = retrieveStaffByStaffId(staff.getUserId());

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
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessagea(constraintViolations));
        }
    }

    @Override
    public StaffEntity emailLogin(String email, String password) throws InvalidLoginCredentialException {
        try {
            StaffEntity staffEntity = retrievePartnerByEmail(email);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + staffEntity.getSalt()));

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
