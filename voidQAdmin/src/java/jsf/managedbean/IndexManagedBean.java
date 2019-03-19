package jsf.managedbean;

import ejb.entity.MessageOfTheDayEntity;
import ejb.session.stateless.MessageOfTheDayControllerLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;


@PushEndpoint("/notify")
@Named(value = "indexManagedBean")
@RequestScoped

public class IndexManagedBean 
{
    @EJB
    private MessageOfTheDayControllerLocal messageOfTheDayControllerLocal;
    
    private List<MessageOfTheDayEntity> messageOfTheDayEntities;
    
    
    
    public IndexManagedBean() 
    {
    }

    

    @PostConstruct
    public void postConstruct()
    {
        messageOfTheDayEntities = messageOfTheDayControllerLocal.retrieveAllMessagesOfTheDay();
    }

    @OnMessage(encoders = { JSONEncoder.class })
	public FacesMessage onMessage(FacesMessage message) {
            System.out.println(message);
		return message;
	}
    
    public List<MessageOfTheDayEntity> getMessageOfTheDayEntities() {
        return messageOfTheDayEntities;
    }

    public void setMessageOfTheDayEntities(List<MessageOfTheDayEntity> messageOfTheDayEntities) {
        this.messageOfTheDayEntities = messageOfTheDayEntities;
    }
}
