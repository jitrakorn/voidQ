package jsf.managedbean;

import ejb.entity.MessageOfTheDayEntity;
import ejb.session.stateless.MessageOfTheDayControllerLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;



@Named(value = "partnerIndexManagedBean")
@RequestScoped

public class IndexManagedBean implements Serializable
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
        System.err.println("********** postConstruct");
        messageOfTheDayEntities = messageOfTheDayControllerLocal.retrieveAllMessagesOfTheDay();
        
        System.err.println("********** postConstruct" + messageOfTheDayEntities.size());
    }
   public void redirect(ActionEvent event) throws IOException
    {
        Long messageIdToView = (Long)event.getComponent().getAttributes().get("newsID");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("newsId", messageIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("news/editAnnouncement.xhtml");
    }
    
   public void delete(ActionEvent event) 
   {
          Long messageIdToDelete = (Long) event.getComponent().getAttributes().get("newsIDa");
          
          System.err.println("********** delete: " + messageIdToDelete);
          
         MessageOfTheDayEntity motd = messageOfTheDayControllerLocal.retrieveMessageByID(messageIdToDelete);
         
         messageOfTheDayControllerLocal.deleteNews(motd);
         messageOfTheDayEntities.remove(motd);
       
   }
    
    public List<MessageOfTheDayEntity> getMessageOfTheDayEntities() {
        return messageOfTheDayEntities;
    }

    public void setMessageOfTheDayEntities(List<MessageOfTheDayEntity> messageOfTheDayEntities) {
        this.messageOfTheDayEntities = messageOfTheDayEntities;
    }
}
