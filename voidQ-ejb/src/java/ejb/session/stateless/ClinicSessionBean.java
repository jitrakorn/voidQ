/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.AdminEntity;
import ejb.entity.ClinicEntity;
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
import util.exception.AdministratorNotFoundException;
import util.exception.StaffEntityNotFoundException;

/**
 *
 * @author mingxuan
 */
@Stateless
@Local(ClinicSessionBeanLocal.class)
public class ClinicSessionBean implements ClinicSessionBeanLocal {
     @PersistenceContext(unitName = "voidQ-ejbPU")
    private EntityManager em;

   private final ValidatorFactory validatorFactory;
    private final Validator validator;
 public ClinicSessionBean() 
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
 
  @Override
    public List<StaffEntity> retrieveStaffByClinicId(Long clinicId) throws StaffEntityNotFoundException {
        Query query = em.createQuery("SELECT c FROM ClinicEntity c WHERE c.staffEntities.title = :isDoctor");
        query.setParameter("isDoctor", "doctor");

        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new StaffEntityNotFoundException("List of doctors from clinic Id" + clinicId + " does not exist!"); //weird
        }
    }

 
   private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ClinicEntity>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
