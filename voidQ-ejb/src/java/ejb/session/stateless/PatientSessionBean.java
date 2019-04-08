/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.PatientEntity;
import ejb.entity.StaffEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.PatientNotFoundException;
import util.exception.UpdatePatientException;

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

    @Override
    public List<PatientEntity> retrieveAllPatientsByClinic(StaffEntity staffEntity) {
        Query query = em.createQuery("SELECT p FROM PatientEntity p where p.clinicEntity = :inClinic");
        query.setParameter("inClinic", staffEntity.getClinicEntity());
        return query.getResultList();
    }

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
    public void updatePatient(PatientEntity patient) throws InputDataValidationException, PatientNotFoundException, UpdatePatientException {
        // Updated in v4.1 to update selective attributes instead of merging the entire state passed in from the client
        // Also check for existing staff before proceeding with the update

        // Updated in v4.2 with bean validation
        Set<ConstraintViolation<PatientEntity>> constraintViolations = validator.validate(patient);

        if (constraintViolations.isEmpty()) {
            if (patient.getUserId() != null) {
                PatientEntity patientToUpdate = retrievePatientByPatientId(patient.getUserId());

                if (patientToUpdate.getEmail().equals(patient.getEmail())) {
                    patientToUpdate.setFirstName(patient.getFirstName());
                    patientToUpdate.setLastName(patient.getLastName());

                } else {
                    throw new UpdatePatientException("Username of patient record to be updated does not match the existing record");
                }
            } else {
                throw new PatientNotFoundException("Patient ID not provided for patient to be updated");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PatientEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
