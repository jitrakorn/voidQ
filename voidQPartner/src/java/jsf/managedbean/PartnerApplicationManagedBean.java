package jsf.managedbean;

import ejb.entity.ClinicEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.StaffEntity;
import ejb.helper.Geocoding;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.json.JSONObject;

import org.primefaces.event.FlowEvent;
import util.enumeration.ApplicationStatus;
import util.enumeration.Availability;
import util.exception.InputDataValidationException;

@Named(value = "partnerApplicationManagedBean")
@ViewScoped
public class PartnerApplicationManagedBean implements Serializable {

   /* @Inject
    @Push(channel = "partner")
    private PushContext newPartnerChannel;
*/
    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    private ClinicEntity newClinic;
    private DoctorEntity newDoctor;
    private String postalcode;
    private boolean skip;

    public void sendMessage(Object message) {
     //   newPartnerChannel.send(message);
    }
    String notify = "New partner application for approval";

    public void execute() {

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, notify, "ccb"));
    }

    public PartnerApplicationManagedBean() {
        newClinic = new ClinicEntity();
        newDoctor = new DoctorEntity();
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

        try {
            newClinic.setApplicationStatus(ApplicationStatus.NOTACTIVATED);

            ClinicEntity partner = partnerSessionBeanLocal.createNewPartner(newClinic);
            newDoctor.setClinicEntity(partner);
            newDoctor.setStatus(Availability.AVAILABLE);
            StaffEntity staff = partnerSessionBeanLocal.createNewStaff(newDoctor);
            partner.getDoctorEntities().add((DoctorEntity)staff);
            newClinic = new ClinicEntity();
            newDoctor = new DoctorEntity();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Your account  : " + staff.getEmail() + " is being created and application is being processed", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new partner: " + ex.getMessage(), null));
        }
    }

    public void save() {
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + newDoctor.getFirstName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public ClinicEntity getNewClinic() {
        return newClinic;
    }

    public void setNewClinic(ClinicEntity newClinic) {
        this.newClinic = newClinic;
    }

    public DoctorEntity getNewDoctor() {
        return newDoctor;
    }

    public void setNewDoctor(DoctorEntity newDoctor) {
        this.newDoctor = newDoctor;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }
    
    

    /*  private void sendJMSMessageToQueueCheckoutNotification() throws JMSException 
    {
        Connection connection = null;
        Session session = null;
        try 
        {
            connection = queueCheckoutNotificationFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("message", "A new partner application has been submitted!");
            MessageProducer messageProducer = session.createProducer(queueCheckoutNotification);
            messageProducer.send(mapMessage);
        }
        finally
        {
            if (session != null) 
            {
                try 
                {
                    session.close();
                } 
                catch (JMSException ex) 
                {
                    ex.printStackTrace();
                }
            }
            
            if (connection != null) 
            {
                connection.close();
            }
        }
    } */
}
