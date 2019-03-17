/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.MessageOfTheDayEntity;
import ejb.entity.StaffEntity;
import java.util.Date;
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
import util.exception.UpdateNewsException;

/**
 *
 * @author mingxuan
 */
@Stateless
@Local(MessageOfTheDayControllerLocal.class)
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
    
     @Override
    public MessageOfTheDayEntity retrieveMessageByID(Long msgId) 
    {
        Query query = entityManager.createQuery("SELECT m FROM MessageOfTheDayEntity m WHERE m.motdId = :inMsgId");
        query.setParameter("inMsgId", msgId);   
            return (MessageOfTheDayEntity)query.getSingleResult();
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
  @Override
    public void updateNews(MessageOfTheDayEntity msg,StaffEntity staff) throws InputDataValidationException, UpdateNewsException
    {
        // Updated in v4.1 to update selective attributes instead of merging the entire state passed in from the client
        // Also check for existing staff before proceeding with the update
        
        // Updated in v4.2 with bean validation
         Date date = new Date();
        Set<ConstraintViolation<MessageOfTheDayEntity>>constraintViolations = validator.validate(msg);
        
        if(constraintViolations.isEmpty())
        {        
            if(msg.getMotdId()!= null)
            {
                MessageOfTheDayEntity msgToUpdate = retrieveMessageByID(msg.getMotdId());
                
                if(msgToUpdate.getMotdId().equals(msg.getMotdId()))
                {
                   msgToUpdate.setMessage(msg.getMessage());
                   msgToUpdate.setTitle(msg.getTitle());
                   msgToUpdate.setLastEditedMessageDate(date);
                   msgToUpdate.setLastEditedStaffEntity(staff);
                   
                }
                else
                {
                    throw new UpdateNewsException("ID of News to be updated does not match the existing record");
                }
            }
            else
            {
                throw new UpdateNewsException("News ID not provided for news to be updated");
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
}
