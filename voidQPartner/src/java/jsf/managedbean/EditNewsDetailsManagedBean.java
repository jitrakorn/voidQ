package jsf.managedbean;

import ejb.entity.DoctorEntity;
import ejb.entity.MessageOfTheDayEntity;
import ejb.session.stateless.MessageOfTheDayControllerLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import util.exception.InputDataValidationException;
import util.exception.UpdateNewsException;

@Named(value = "editNewsDetailsManagedBean")
@ViewScoped

public class editNewsDetailsManagedBean implements Serializable {

    @EJB(name = "MessageOfTheDayControllerLocal")
    private MessageOfTheDayControllerLocal messageOfTheDayControllerLocal;

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    private MessageOfTheDayEntity selectedMessageOfTheDayEntity;

    private Long messageIdToView;
 

    private boolean isDisabled = true;

    private String textValue = "Enable Edit";

    public editNewsDetailsManagedBean() {
      
    }

    @PostConstruct
    public void postConstruct() {
          
          messageIdToView = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("newsId");
      System.out.println(messageIdToView);
       selectedMessageOfTheDayEntity= messageOfTheDayControllerLocal.retrieveMessageByID(messageIdToView);
     
    }

    public void foo() {
    }

     public void updateNews() {
     DoctorEntity doctor =  (DoctorEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPartner");
        try {
            messageOfTheDayControllerLocal.updateNews(selectedMessageOfTheDayEntity, doctor);
           
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Announcement updated successfully", null));
            
            
        } catch (UpdateNewsException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void reset() {
        PrimeFaces.current().resetInputs("formUser:userPanel");
    }

    public void enableEdit() {
        PrimeFaces.current().resetInputs("formUser:userPanel");
        System.out.println("beeofre" + isDisabled);
        isDisabled = !isDisabled;
        System.out.println("after" + isDisabled);
        if (isDisabled) {

            textValue = "Enable Edit";

        } else {
            textValue = "Disable Edit";

        }
        System.out.println(textValue);
    }

    public void resetPassword(ActionEvent event) throws IOException {

        //System.out.println(adminIdToView);
        //  administratorSessionBeanLocal.resetPassword(adminIdToView);
        //   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Password sent to email", null));
    }

    public void deleteAdmin(ActionEvent event) throws IOException {
        //  FacesContext.getCurrentInstance().getExternalContext().getFlash().put("adminIdToDelete", adminIdToView);
        // FacesContext.getCurrentInstance().getExternalContext().redirect("deleteAdmin.xhtml");
    }

       
 

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public MessageOfTheDayEntity getSelectedMessageOfTheDayEntity() {
        return selectedMessageOfTheDayEntity;
    }

    public void setSelectedMessageOfTheDayEntity(MessageOfTheDayEntity selectedMessageOfTheDayEntity) {
        this.selectedMessageOfTheDayEntity = selectedMessageOfTheDayEntity;
    }

    public Long getMessageIdToView() {
        return messageIdToView;
    }

    public void setMessageIdToView(Long messageIdToView) {
        this.messageIdToView = messageIdToView;
    }

}
