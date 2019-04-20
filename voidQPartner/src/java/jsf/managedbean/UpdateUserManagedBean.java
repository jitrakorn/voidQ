package jsf.managedbean;

import ejb.entity.DoctorEntity;
import ejb.entity.NurseEntity;
import ejb.entity.StaffEntity;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import util.exception.PartnerNotFoundException;
import util.exception.UpdatePasswordException;

@Named(value = "updateUserManagedBean")
@ViewScoped

public class UpdateUserManagedBean implements Serializable {

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    private Long staffIdToUpdate;
    private StaffEntity staffToUpdate;
    private DoctorEntity doctorToUpdate;
    private String oldPassword;
    private String newPassword;
    private boolean isDisabled = true;
    private boolean showForm = false;
    private boolean isDoctor = false;
    private boolean isNurse = false;

    private String textValue = "Enable Edit";

    public UpdateUserManagedBean() {

    }

    @PostConstruct
    public void postConstruct() {
        staffToUpdate = (StaffEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPartner");
        DoctorEntity doctor = new DoctorEntity();
        if (staffToUpdate.getClass().equals(doctor.getClass())) {
            isDoctor = true;
        } else {
            isNurse = true;
        }
    }

    public void togglePassword(ActionEvent event) {
        if (showForm) {
            showForm = false;
        } else {
            showForm = true;

        }
    }

    public void back(ActionEvent event) throws IOException {
        //  FacesContext.getCurrentInstance().getExternalContext().getFlash().put("adminIdToView", adminIdToUpdate);
        // FacesContext.getCurrentInstance().getExternalContext().redirect("viewAdminDetails.xhtml");
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

    public void updateStaff() {

        try {
            partnerSessionBeanLocal.updateStaff(staffToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff updated successfully", null));
            isDisabled = !isDisabled;
            textValue = "Enable Edit";

        } catch (PartnerNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating staff: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void updatePassword() {

        if (isDoctor) {
            try {
                partnerSessionBeanLocal.updateDoctorPassword((DoctorEntity) staffToUpdate, oldPassword, newPassword);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password updated successfully", null));

            } catch (UpdatePasswordException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating password: " + ex.getMessage(), null));
            } catch (PartnerNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating password: " + ex.getMessage(), null));
            }
        } else {
            try {
                partnerSessionBeanLocal.updateNursePassword((NurseEntity) staffToUpdate, oldPassword, newPassword);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password updated successfully", null));

            } catch (UpdatePasswordException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating password: " + ex.getMessage(), null));
            } catch (PartnerNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating password: " + ex.getMessage(), null));
            }
        }

    }

    public Long getStaffIdToUpdate() {
        return staffIdToUpdate;
    }

    public void setStaffIdToUpdate(Long staffIdToUpdate) {
        this.staffIdToUpdate = staffIdToUpdate;
    }

    public StaffEntity getStaffToUpdate() {
        return staffToUpdate;
    }

    public void setStaffToUpdate(StaffEntity staffToUpdate) {
        this.staffToUpdate = staffToUpdate;
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

    public boolean isShowForm() {
        return showForm;
    }

    public void setShowForm(boolean showForm) {
        this.showForm = showForm;
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

}
