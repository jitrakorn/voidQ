package jsf.managedbean;

import ejb.entity.AdminEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.DoctorEntity;
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

@Named(value = "partnerManagementManagedBean")
@ViewScoped

public class partnerManagementManagedBean implements Serializable {

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;


    private List<DoctorEntity> doctors;
    private DoctorEntity newDoctor;
    private DoctorEntity selectedDoctorEntityToView;
    private DoctorEntity selectedDoctorEntityToUpdate;
    private DoctorEntity selectedDoctorEntityToUpdatePassword;
    private List<DoctorEntity> filteredDoctorEntities;
    private DoctorEntity doctorToUpdate;
    private String oldPassword;
    private String newPassword;

    public partnerManagementManagedBean() {
        newDoctor = new DoctorEntity();
    }

    @PostConstruct
    public void postConstruct() {
        doctors = partnerSessionBeanLocal.retrieveAllDoctors();
    }

    public void doUpdateDoctor(ActionEvent event) {
        selectedDoctorEntityToUpdate = (DoctorEntity) event.getComponent().getAttributes().get("doctorEntityToUpdate");

    }
    
     public void doUpdateDoctorPassword(ActionEvent event) {
        selectedDoctorEntityToUpdatePassword = (DoctorEntity) event.getComponent().getAttributes().get("doctorEntityToUpdatePassword");

    }
    
     
     public void updatePassword(ActionEvent event) {
          
            try {
                partnerSessionBeanLocal.updateDoctorPassword(selectedDoctorEntityToUpdatePassword, oldPassword, newPassword);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password updated successfully", null));

            } catch (UpdatePasswordException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating password: " + ex.getMessage(), null));
            } catch (PartnerNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating password: " + ex.getMessage(), null));
            }
    
    }

    public void resetPassword(ActionEvent event)
            
    {
         DoctorEntity doctorEntityToReset = (DoctorEntity)event.getComponent().getAttributes().get("doctorEntityToReset");
        try {
              
            String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
        StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
             doctorEntityToReset.setPassword(password);
            //SMS.sendPost(password,doctorEntityToReset.getPhoneNumber());
           
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Password sent to registered phone number successfully", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while resetting password: " + ex.getMessage(), null));
        }
    }
     public void updateDoctor(ActionEvent event)
    {
       
        try
        {
            partnerSessionBeanLocal.updateDoctor(selectedDoctorEntityToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin updated successfully", null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

//    public void createNewDoctor(ActionEvent event) {
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
    
     public void deleteDoctor(ActionEvent event)
    {
        try
        {
            DoctorEntity doctorEntityToDelete = (DoctorEntity)event.getComponent().getAttributes().get("doctorEntityToDelete");
            partnerSessionBeanLocal.deleteDoctor(doctorEntityToDelete.getUserId());
            
            doctors.remove(doctorEntityToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Doctor deleted successfully", null));
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
    

    public void viewDoctorDetails(ActionEvent event) throws IOException {
        Long doctorIdToView = (Long) event.getComponent().getAttributes().get("doctorId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("doctorIdToView", doctorIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewDoctorDetails.xhtml");
    }

    public List<DoctorEntity> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorEntity> doctors) {
        this.doctors = doctors;
    }

    public List<DoctorEntity> getFilteredDoctorEntities() {
        return filteredDoctorEntities;
    }

    public void setFilteredDoctorEntities(List<DoctorEntity> filteredDoctorEntities) {
        this.filteredDoctorEntities = filteredDoctorEntities;
    }
    public DoctorEntity getNewDoctor() {
        return newDoctor;
    }

    public void setNewDoctor(DoctorEntity newDoctor) {
        this.newDoctor = newDoctor;
    }

    public DoctorEntity getSelectedDoctorEntityToView() {
        return selectedDoctorEntityToView;
    }

    public void setSelectedDoctorEntityToView(DoctorEntity selectedDoctorEntityToView) {
        this.selectedDoctorEntityToView = selectedDoctorEntityToView;
    }

    public DoctorEntity getSelectedDoctorEntityToUpdate() {
        return selectedDoctorEntityToUpdate;
    }

    public void setSelectedDoctorEntityToUpdate(DoctorEntity selectedDoctorEntityToUpdate) {
        this.selectedDoctorEntityToUpdate = selectedDoctorEntityToUpdate;
    }

    public DoctorEntity getDoctorToUpdate() {
        return doctorToUpdate;
    }

    public void setDoctorToUpdate(DoctorEntity doctorToUpdate) {
        this.doctorToUpdate = doctorToUpdate;
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

    public DoctorEntity getSelectedDoctorEntityToUpdatePassword() {
        return selectedDoctorEntityToUpdatePassword;
    }

    public void setSelectedDoctorEntityToUpdatePassword(DoctorEntity selectedDoctorEntityToUpdatePassword) {
        this.selectedDoctorEntityToUpdatePassword = selectedDoctorEntityToUpdatePassword;
    }

    
 

}
