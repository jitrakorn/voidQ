package jsf.managedbean;

import ejb.entity.ClinicEntity;
import ejb.entity.StaffEntity;
import ejb.helper.Geocoding;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

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

@Named(value = "partnerApplicationManagedBean")
@ViewScoped
public class PartnerApplicationManagedBean implements Serializable {

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    private ClinicEntity newClinic;
    private StaffEntity newStaff;
    private String postalcode;
    private boolean skip;

    public PartnerApplicationManagedBean() {
        newClinic = new ClinicEntity();
        newStaff = new StaffEntity();
    }

    @PostConstruct
    public void postConstruct() {

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
        System.out.println("run");
        try {
            newClinic.setApplicationStatus(ApplicationStatus.NOTACTIVATED);

            ClinicEntity partner = partnerSessionBeanLocal.createNewPartner(newClinic);
            newStaff.setClinicEntity(partner);
            newStaff.setTitle("doctor");
            newStaff.setStatus("not taken");
            StaffEntity staff = partnerSessionBeanLocal.createNewStaff(newStaff);
            partner.getStaffEntities().add(staff);
            newClinic = new ClinicEntity();
            newStaff = new StaffEntity();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Your account  : " + staff.getEmail() + " is being created and application is being processed", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new partner: " + ex.getMessage(), null));
        }
    }
 public void save() {        
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + newStaff.getFirstName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public boolean isSkip() {
        return skip;
    }
 
    public void setSkip(boolean skip) {
        this.skip = skip;
    }
     
    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
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

    public String getPostalcode() {
        return postalcode;
    }

    
    public void setPostalcode(String postalcode) {
          System.out.println(postalcode);
        this.postalcode = postalcode;
    }

}
