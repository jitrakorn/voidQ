/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.MessageOfTheDayEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;

/**
 *
 * @author mingxuan
 */
@Local
public interface MessageOfTheDayControllerLocal {

    public MessageOfTheDayEntity createNewMessageOfTheDay(MessageOfTheDayEntity newMessageOfTheDayEntity) throws InputDataValidationException;

    public List<MessageOfTheDayEntity> retrieveAllMessagesOfTheDay();
    
}
