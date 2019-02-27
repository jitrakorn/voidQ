/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.MessageOfTheDayEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;

/**
 *
 * @author mingxuan
 */
@Stateless
public class MessageOfTheDayController implements MessageOfTheDayControllerLocal {

    
        @PersistenceContext(unitName = "voidQ-ejbPU")
    private EntityManager entityManager;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;


    
    
    public MessageOfTheDayController() 
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    
    
    @Override
    public MessageOfTheDayEntity createNewMessageOfTheDay(MessageOfTheDayEntity newMessageOfTheDayEntity) throws InputDataValidationException
    {
        Set<ConstraintViolation<MessageOfTheDayEntity>>constraintViolations = validator.validate(newMessageOfTheDayEntity);
        
        if(constraintViolations.isEmpty())
        {
            entityManager.persist(newMessageOfTheDayEntity);
            entityManager.flush();
            
            return newMessageOfTheDayEntity;
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    
    
    @Override
    public List<MessageOfTheDayEntity> retrieveAllMessagesOfTheDay()
    {
        Query query = entityManager.createQuery("SELECT motd FROM MessageOfTheDayEntity motd ORDER BY motd.motdId ASC");
        
        return query.getResultList();
    }
    
    
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<MessageOfTheDayEntity>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }

    
}
