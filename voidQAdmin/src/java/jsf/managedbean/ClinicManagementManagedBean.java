/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.entity.ClinicEntity;
import ejb.session.stateless.ClinicSessionBeanLocal;
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

/**
 *
 * @author terencetay
 */
@Named(value = "clinicManagementManagedBean")
@ViewScoped
public class ClinicManagementManagedBean implements Serializable {

    @EJB
    private ClinicSessionBeanLocal clinicSessionBean;

    
    private List<ClinicEntity> clinics;
    private List<ClinicEntity> filteredClinicEntities;
    private ClinicEntity selectedClinicEntityToUpdate;
    private ClinicEntity clinicEntityToDelete;
    
    /**
     * Creates a new instance of ClinicManagementManagedBean
     */
    public ClinicManagementManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        clinics = clinicSessionBean.retrieveAllClinics();
    }
    
    public void doUpdateClinic(ActionEvent event) {
        selectedClinicEntityToUpdate = (ClinicEntity) event.getComponent().getAttributes().get("clinicEntityToUpdate");
    }
    
    public void updateClinic() {
        clinicSessionBean.updateClinic(selectedClinicEntityToUpdate);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Clinic updated successfully", null));
    }
    
    public void deleteClinic(ActionEvent event) {
        clinicEntityToDelete = (ClinicEntity) event.getComponent().getAttributes().get("clinicEntityToDelete");
        clinicSessionBean.deleteClinic(clinicEntityToDelete);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Clinic deleted successfully", null));
    }
    
    public void redirectNewClinic() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/clinicAdministration/createNewPartner.xhtml");
    }

    public List<ClinicEntity> getClinics() {
        return clinics;
    }

    public void setClinics(List<ClinicEntity> clinics) {
        this.clinics = clinics;
    }

    public List<ClinicEntity> getFilteredClinicEntities() {
        return filteredClinicEntities;
    }

    public void setFilteredClinicEntities(List<ClinicEntity> filteredClinicEntities) {
        this.filteredClinicEntities = filteredClinicEntities;
    }

    public ClinicEntity getSelectedClinicEntityToUpdate() {
        return selectedClinicEntityToUpdate;
    }

    public void setSelectedClinicEntityToUpdate(ClinicEntity selectedClinicEntityToUpdate) {
        this.selectedClinicEntityToUpdate = selectedClinicEntityToUpdate;
    }
    
    
}
