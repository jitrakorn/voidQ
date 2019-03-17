package jsf.managedbean;

import ejb.entity.MessageOfTheDayEntity;
import ejb.session.stateless.MessageOfTheDayControllerLocal;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;



@Named(value = "partnerIndexManagedBean")
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
   public void redirect(ActionEvent event) throws IOException
    {
        Long messageIdToView = (Long)event.getComponent().getAttributes().get("newsID");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("newsId", messageIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("news/editAnnouncement.xhtml");
    }
    
    
    public List<MessageOfTheDayEntity> getMessageOfTheDayEntities() {
        return messageOfTheDayEntities;
    }

    public void setMessageOfTheDayEntities(List<MessageOfTheDayEntity> messageOfTheDayEntities) {
        this.messageOfTheDayEntities = messageOfTheDayEntities;
    }
}
