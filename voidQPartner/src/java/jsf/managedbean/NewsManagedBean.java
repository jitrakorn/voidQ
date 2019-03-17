package jsf.managedbean;

import ejb.entity.MessageOfTheDayEntity;
import ejb.entity.StaffEntity;
import ejb.session.stateless.MessageOfTheDayControllerLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import util.exception.InputDataValidationException;

@Named(value = "newsManagedBean")
@ViewScoped
public class NewsManagedBean implements Serializable {

    @EJB(name = "MessageOfTheDayControllerLocal")
    private MessageOfTheDayControllerLocal messageOfTheDayControllerLocal;

    private StaffEntity staffToUpdate;
    private MessageOfTheDayEntity messageOfTheDayEntity;

    public NewsManagedBean() {
        messageOfTheDayEntity = new MessageOfTheDayEntity();
    }

    @PostConstruct
    public void postConstruct() {
        staffToUpdate = (StaffEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPartner");

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
