package jsf.managedbean;

import ejb.entity.MessageOfTheDayEntity;
import ejb.entity.StaffEntity;
import ejb.session.stateless.MessageOfTheDayControllerLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import util.enumeration.ApplicationStatus;
import util.exception.PartnerNotFoundException;

@Named(value = "viewNewsDetailsManagedBean")
@ViewScoped

public class ViewNewsDetailsManagedBean implements Serializable {

    @EJB(name = "MessageOfTheDayControllerLocal")
    private MessageOfTheDayControllerLocal messageOfTheDayControllerLocal;

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    private MessageOfTheDayEntity selectedMessageOfTheDayEntity;

    private Long staffIdToView;
    private List<MessageOfTheDayEntity> messageOfTheDayEntity;

    private boolean isDisabled = true;

    private String textValue = "Enable Edit";

    public ViewNewsDetailsManagedBean() {
        messageOfTheDayEntity = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        messageOfTheDayEntity = messageOfTheDayControllerLocal.retrieveAllMessagesOfTheDay();
    }

    public void foo() {
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

    public Long getStaffIdToView() {
        return staffIdToView;
    }

    public void setStaffIdToView(Long staffIdToView) {
        this.staffIdToView = staffIdToView;
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

    public List<MessageOfTheDayEntity> getMessageOfTheDayEntity() {
        return messageOfTheDayEntity;
    }

    public void setMessageOfTheDayEntity(List<MessageOfTheDayEntity> messageOfTheDayEntity) {
        this.messageOfTheDayEntity = messageOfTheDayEntity;
    }

    public MessageOfTheDayEntity getSelectedMessageOfTheDayEntity() {
        return selectedMessageOfTheDayEntity;
    }

    public void setSelectedMessageOfTheDayEntity(MessageOfTheDayEntity selectedMessageOfTheDayEntity) {
        this.selectedMessageOfTheDayEntity = selectedMessageOfTheDayEntity;
    }

}
