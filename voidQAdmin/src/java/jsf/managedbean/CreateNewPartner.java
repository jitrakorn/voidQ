package jsf.managedbean;

import ejb.entity.ClinicEntity;
import ejb.entity.StaffEntity;
import ejb.helper.Geocoding;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.json.JSONObject;
import org.primefaces.event.FlowEvent;
import util.enumeration.ApplicationStatus;

import util.exception.InputDataValidationException;

@Named(value = "createNewPartnerManagedBean")
@ViewScoped
public class CreateNewPartner implements Serializable {

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;
    private String postalcode;
    private ClinicEntity newClinic;
    private StaffEntity newStaff;
    private boolean skip;

    public CreateNewPartner() {
        newClinic = new ClinicEntity();
        newStaff = new StaffEntity();
    }

    @PostConstruct
    public void postConstruct() {

    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void loadAddress(AjaxBehaviorEvent event) {

        try {
            String hahaha = Geocoding.getJSONByGoogle(postalcode);
            System.out.println(hahaha);
            JSONObject jsonObj = new JSONObject(hahaha);
            //JSONArray jsonArr = new JSONArray(jsonObj.getJSONArray("results"));

            JSONObject loc = jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            String lat = loc.get("lat").toString();
            String lng = loc.get("lng").toString();
            String addressJSON = Geocoding.addressLookUp(lat + "," + lng);
            JSONObject locObj = new JSONObject(addressJSON);
            JSONObject locName = locObj.getJSONArray("results").getJSONObject(0);
            String name = locName.getString("formatted_address");
            newClinic.setAddress(name);
        } catch (IOException ex) {

        }

    }

    public void createNewPartner() {

        try {
            newClinic.setApplicationStatus(ApplicationStatus.ACTIVATED);
            ClinicEntity partner = partnerSessionBeanLocal.createNewPartner(newClinic);
            partner.getStaffEntities().add(newStaff);
            newClinic = new ClinicEntity();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Clinic created successfully (Clinic ID: " + partner.getClinicId() + ")", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new partner: " + ex.getMessage(), null));
        }
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public ClinicEntity getNewClinic() {
        return newClinic;
    }

    public void setNewClinic(ClinicEntity newClinic) {
        this.newClinic = newClinic;
    }

    public StaffEntity getNewStaff() {
        return newStaff;
    }

    public void setNewStaff(StaffEntity newStaff) {
        this.newStaff = newStaff;
    }

}
