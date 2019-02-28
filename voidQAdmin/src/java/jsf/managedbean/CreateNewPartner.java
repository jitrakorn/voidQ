package jsf.managedbean;

import ejb.entity.Partner;
import ejb.helper.Geocoding;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.json.JSONArray;
import org.json.JSONObject;
import util.enumeration.AccountStatus;

import util.exception.InputDataValidationException;

@Named(value = "createNewPartnerManagedBean")
@RequestScoped
public class CreateNewPartner {

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;
    private String postalcode;
    private Partner newPartner;

    public CreateNewPartner() {
        newPartner = new Partner();
    }

    @PostConstruct
    public void postConstruct() {

    }

    public void loadAddress(ActionEvent event) {

        try {
            String hahaha = Geocoding.getJSONByGoogle(postalcode);
            System.out.println(hahaha);
            JSONObject jsonObj = new JSONObject(hahaha);
            //JSONArray jsonArr = new JSONArray(jsonObj.getJSONArray("results"));

            JSONObject loc = jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            String lat  =loc.get("lat").toString();
            String lng = loc.get("lng").toString();
            String addressJSON = Geocoding.addressLookUp(lat+","+lng);
             JSONObject locObj = new JSONObject(addressJSON);
            JSONObject locName = locObj.getJSONArray("results").getJSONObject(0);
            String name = locName.getString("formatted_address");
            newPartner.setClinicAddress(name);
        } catch (IOException ex) {

        }

    }

    public void createNewPartner(ActionEvent event) {

        try {
            newPartner.setAccountStatus(AccountStatus.ACTIVATED);
            Partner partner = partnerSessionBeanLocal.createNewPartner(newPartner);
            newPartner = new Partner();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Partner created successfully (Staff ID: " + partner.getPartnerId() + ")", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new partner: " + ex.getMessage(), null));
        }
    }

    public Partner getNewPartner() {
        return newPartner;
    }

    public void setNewPartner(Partner newPartner) {
        this.newPartner = newPartner;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

}
