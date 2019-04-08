package jsf.managedbean;

import ejb.entity.AdminEntity;
import ejb.entity.PatientEntity;
import ejb.entity.StaffEntity;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import ejb.session.stateless.PatientSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import util.exception.InputDataValidationException;
import util.exception.PatientNotFoundException;

@Named(value = "patientManagementManagedBean")
@ViewScoped

public class PatientManagementManagedBean implements Serializable {

    @EJB(name = "PatientSessionBeanLocal")
    private PatientSessionBeanLocal patientSessionBeanLocal;

    
    private List<PatientEntity> patients;
    private PatientEntity newPatient;
    private PatientEntity selectedPatientEntityToView;
    private PatientEntity selectedPatientEntityToUpdate;
    private List<PatientEntity> filteredPatientEntities;

    public PatientManagementManagedBean() {
        newPatient = new PatientEntity();
    }

    @PostConstruct
    public void postConstruct() {
        StaffEntity se  = (StaffEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPartner");
        patients = patientSessionBeanLocal.retrieveAllPatientsByClinic(se);
    }

    public void doUpdatePatient(ActionEvent event) {
        selectedPatientEntityToUpdate = (PatientEntity) event.getComponent().getAttributes().get("patientEntityToUpdate");

    }
    
     public void updatePatient(ActionEvent event)
    {
       
        try
        {
            patientSessionBeanLocal.updatePatient(selectedPatientEntityToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin updated successfully", null));
        }
        catch(PatientNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating admin: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

   
    
   /*  public void deletePatient(ActionEvent event)
    {
        try
        {
            AdminEntity adminEntityToDelete = (AdminEntity)event.getComponent().getAttributes().get("adminEntityToDelete");
            administratorSessionBeanLocal.deleteAdmin(adminEntityToDelete.getUserId());
            
            patients.remove(adminEntityToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin deleted successfully", null));
        }
        catch(AdministratorNotFoundException | DeleteAdminException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting product: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
     */

    public void viewAdminDetails(ActionEvent event) throws IOException {
        Long productIdToView = (Long) event.getComponent().getAttributes().get("adminId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("adminIdToView", productIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewAdminDetails.xhtml");
    }

    public List<PatientEntity> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientEntity> patients) {
        this.patients = patients;
    }

    public PatientEntity getNewPatient() {
        return newPatient;
    }

    public void setNewPatient(PatientEntity newPatient) {
        this.newPatient = newPatient;
    }

    public PatientEntity getSelectedPatientEntityToView() {
        return selectedPatientEntityToView;
    }

    public void setSelectedPatientEntityToView(PatientEntity selectedPatientEntityToView) {
        this.selectedPatientEntityToView = selectedPatientEntityToView;
    }

    public PatientEntity getSelectedPatientEntityToUpdate() {
        return selectedPatientEntityToUpdate;
    }

    public void setSelectedPatientEntityToUpdate(PatientEntity selectedPatientEntityToUpdate) {
        this.selectedPatientEntityToUpdate = selectedPatientEntityToUpdate;
    }

    public List<PatientEntity> getFilteredPatientEntities() {
        return filteredPatientEntities;
    }

    public void setFilteredPatientEntities(List<PatientEntity> filteredPatientEntities) {
        this.filteredPatientEntities = filteredPatientEntities;
    }


}
