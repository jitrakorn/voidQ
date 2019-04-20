package jsf.managedbean;

import ejb.entity.AdminEntity;
import ejb.entity.NurseEntity;
import ejb.entity.NurseEntity;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.sms.SMS;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.AdministratorNotFoundException;
import util.exception.DeleteAdminException;
import util.exception.DeletePartnerException;
import util.exception.InputDataValidationException;
import util.exception.PartnerNotFoundException;
import util.exception.UpdatePasswordException;

@Named(value = "nurseManagementManagedBean")
@ViewScoped

public class nurseManagementManagedBean implements Serializable {

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;


    private List<NurseEntity> nurses;
    private NurseEntity newNurse;
    private NurseEntity selectedNurseEntityToView;
    private NurseEntity selectedNurseEntityToUpdate;
    private NurseEntity selectedNurseEntityToUpdatePassword;
    private List<NurseEntity> filteredNurseEntities;
    private NurseEntity nurseToUpdate;
    private String oldPassword;
    private String newPassword;

    public nurseManagementManagedBean() {
        newNurse = new NurseEntity();
    }

    @PostConstruct
    public void postConstruct() {
        nurses = partnerSessionBeanLocal.retrieveAllNurses();
    }

    public void doUpdateNurse(ActionEvent event) {
        selectedNurseEntityToUpdate = (NurseEntity) event.getComponent().getAttributes().get("nurseEntityToUpdate");

    }
    
     public void doUpdateNursePassword(ActionEvent event) {
        selectedNurseEntityToUpdatePassword = (NurseEntity) event.getComponent().getAttributes().get("nurseEntityToUpdatePassword");

    }
    
     
     public void updatePassword(ActionEvent event) {
          
            try {
                partnerSessionBeanLocal.updateNursePassword(selectedNurseEntityToUpdatePassword, oldPassword, newPassword);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password updated successfully", null));

            } catch (UpdatePasswordException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating password: " + ex.getMessage(), null));
            } catch (PartnerNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating password: " + ex.getMessage(), null));
            }
    
    }

    public void resetPassword(ActionEvent event)
            
    {
         NurseEntity nurseEntityToReset = (NurseEntity)event.getComponent().getAttributes().get("nurseEntityToReset");
        try {
              
            String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
        StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
             nurseEntityToReset.setPassword(password);
            //SMS.sendPost(password,nurseEntityToReset.getPhoneNumber());
           
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Password sent to registered phone number successfully", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while resetting password: " + ex.getMessage(), null));
        }
    }
     public void updateNurse(ActionEvent event)
    {
       
        try
        {
            partnerSessionBeanLocal.updateNurse(selectedNurseEntityToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin updated successfully", null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

//    public void createNewNurse(ActionEvent event) {
//
//        try {
//            AdminEntity admin = administratorSessionBeanLocal.createNewAdmin(newAdmin);
//            newAdmin = new AdminEntity();
//
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Admin created successfully (Admin ID: " + admin.getUserId() + ")", null));
//        } catch (InputDataValidationException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new admin: " + ex.getMessage(), null));
//        }
//    }
    
     public void deleteNurse(ActionEvent event)
    {
        try
        {
            NurseEntity nurseEntityToDelete = (NurseEntity)event.getComponent().getAttributes().get("nurseEntityToDelete");
            partnerSessionBeanLocal.deleteNurse(nurseEntityToDelete.getUserId());
            
            nurses.remove(nurseEntityToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nurse deleted successfully", null));
        }
        catch( DeletePartnerException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting product: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    

    public void viewNurseDetails(ActionEvent event) throws IOException {
        Long nurseIdToView = (Long) event.getComponent().getAttributes().get("nurseId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("nurseIdToView", nurseIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewNurseDetails.xhtml");
    }

    public List<NurseEntity> getNurses() {
        return nurses;
    }

    public void setNurses(List<NurseEntity> nurses) {
        this.nurses = nurses;
    }

    public List<NurseEntity> getFilteredNurseEntities() {
        return filteredNurseEntities;
    }

    public void setFilteredNurseEntities(List<NurseEntity> filteredNurseEntities) {
        this.filteredNurseEntities = filteredNurseEntities;
    }
    public NurseEntity getNewNurse() {
        return newNurse;
    }

    public void setNewNurse(NurseEntity newNurse) {
        this.newNurse = newNurse;
    }

    public NurseEntity getSelectedNurseEntityToView() {
        return selectedNurseEntityToView;
    }

    public void setSelectedNurseEntityToView(NurseEntity selectedNurseEntityToView) {
        this.selectedNurseEntityToView = selectedNurseEntityToView;
    }

    public NurseEntity getSelectedNurseEntityToUpdate() {
        return selectedNurseEntityToUpdate;
    }

    public void setSelectedNurseEntityToUpdate(NurseEntity selectedNurseEntityToUpdate) {
        this.selectedNurseEntityToUpdate = selectedNurseEntityToUpdate;
    }

    public NurseEntity getNurseToUpdate() {
        return nurseToUpdate;
    }

    public void setNurseToUpdate(NurseEntity nurseToUpdate) {
        this.nurseToUpdate = nurseToUpdate;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public NurseEntity getSelectedNurseEntityToUpdatePassword() {
        return selectedNurseEntityToUpdatePassword;
    }

    public void setSelectedNurseEntityToUpdatePassword(NurseEntity selectedNurseEntityToUpdatePassword) {
        this.selectedNurseEntityToUpdatePassword = selectedNurseEntityToUpdatePassword;
    }

    
 

}
