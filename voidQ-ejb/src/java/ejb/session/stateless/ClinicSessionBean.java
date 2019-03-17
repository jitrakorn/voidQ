/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.ClinicEntity;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
