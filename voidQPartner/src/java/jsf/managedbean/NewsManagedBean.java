package jsf.managedbean;

import ejb.entity.MessageOfTheDayEntity;
import ejb.entity.StaffEntity;
import ejb.session.stateless.MessageOfTheDayControllerLocal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;


import org.primefaces.PrimeFaces;

import util.exception.InputDataValidationException;

@Named(value = "newsManagedBean")
@ViewScoped
public class NewsManagedBean implements Serializable {

    @EJB(name = "MessageOfTheDayControllerLocal")
    private MessageOfTheDayControllerLocal messageOfTheDayControllerLocal;

    
    @Inject @Push
private PushContext someChannel;


    private StaffEntity staffToUpdate;
    private MessageOfTheDayEntity messageOfTheDayEntity;

    public NewsManagedBean() {
        messageOfTheDayEntity = new MessageOfTheDayEntity();
    }

    @PostConstruct
    public void postConstruct() {
        staffToUpdate = (StaffEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPartner");

    }

public void sendMessage(Object message) {
    someChannel.send(message);
}

public void execute()
{
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Announcement posted", null));
}

    public void addAnnouncement() {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("d/M/y");
        messageOfTheDayEntity.setStaffEntity(staffToUpdate); //need update to persistence 
        Date date = new Date();
        messageOfTheDayEntity.setMessageDate(date);

        try {
            messageOfTheDayControllerLocal.createNewMessageOfTheDay(messageOfTheDayEntity);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Announcement created successfully", null));
            messageOfTheDayEntity.setMessage("");
            messageOfTheDayEntity.setTitle("");
           sendMessage("ccb");
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void reset() {
        PrimeFaces.current().resetInputs("cb");
    }

    public MessageOfTheDayEntity getMessageOfTheDayEntity() {
        return messageOfTheDayEntity;
    }

    public void setMessageOfTheDayEntity(MessageOfTheDayEntity messageOfTheDayEntity) {
        this.messageOfTheDayEntity = messageOfTheDayEntity;
    }

    public StaffEntity getStaffToUpdate() {
        return staffToUpdate;
    }

    public void setStaffToUpdate(StaffEntity staffToUpdate) {
        this.staffToUpdate = staffToUpdate;
    }

  

  

}
