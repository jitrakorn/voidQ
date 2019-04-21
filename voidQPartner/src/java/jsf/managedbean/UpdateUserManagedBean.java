package jsf.managedbean;

import ejb.entity.DoctorEntity;
import ejb.entity.NurseEntity;
import ejb.entity.StaffEntity;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import util.exception.PartnerNotFoundException;

@Named(value = "updateUserManagedBean")
@ViewScoped

public class UpdateUserManagedBean implements Serializable {

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    private List<DoctorEntity> doctors;
    private List<NurseEntity> nurses;
    private Long staffIdToUpdate;
    private StaffEntity staffToUpdate;
    private boolean isDisabled = true;
    private String textValue = "Enable Edit";

    public UpdateUserManagedBean() {

    }

    @PostConstruct
    public void postConstruct() {
        staffToUpdate = (StaffEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPartner");
        doctors = partnerSessionBeanLocal.getDoctorsByClinicId(staffToUpdate.getClinicEntity().getClinicId());
        nurses = partnerSessionBeanLocal.getNursesByClinicId(staffToUpdate.getClinicEntity().getClinicId());
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

    public List<DoctorEntity> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorEntity> doctors) {
        this.doctors = doctors;
    }

    public List<NurseEntity> getNurses() {
        return nurses;
    }

    public void setNurses(List<NurseEntity> nurses) {
        this.nurses = nurses;
    }

}
