package jsf.managedbean;

import ejb.entity.ClinicEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.NurseEntity;
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

@Named(value = "createStaffManagedBean")
@ViewScoped
public class CreateStaffManagedBean implements Serializable {

    /* @Inject
    @Push(channel = "partner")
    private PushContext newPartnerChannel;
     */
    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    private ClinicEntity newClinic;
    private StaffEntity staffToUpdate;
    private DoctorEntity newDoctor;
    private NurseEntity newNurse;
    private String emailAdd;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
    
    public void sendMessage(Object message) {
        //   newPartnerChannel.send(message);
    }
    String notify = "New partner application for approval";

    public void execute() {

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, notify, "ccb"));
    }

    public CreateStaffManagedBean() {
        newClinic = new ClinicEntity();
        newDoctor = new DoctorEntity();
        newNurse = new NurseEntity();
    }

    @PostConstruct
    public void postConstruct() {
        staffToUpdate = (StaffEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPartner");
        newClinic = staffToUpdate.getClinicEntity();
    }

    public void updateNewStaff(ActionEvent event) {

        if (role.equals("Doctor")) {
            newDoctor.setEmail(emailAdd);
            newDoctor.setFirstName(firstName);
            newDoctor.setLastName(lastName);
            newDoctor.setClinicEntity(newClinic);
            newDoctor.setPassword(password);
            newDoctor.setStatus(Availability.AVAILABLE);
            
            DoctorEntity doc = partnerSessionBeanLocal.createNewDoctor(newDoctor);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Your account  : " + doc.getEmail() + " is being created and application is being processed", null));
            
            
        } else if (role.equals("Nurse")) {
            newNurse.setEmail(emailAdd);
            newNurse.setFirstName(firstName);
            newNurse.setLastName(lastName);
            newNurse.setClinicEntity(newClinic);
            newNurse.setPassword(password);
            
            NurseEntity nurse = partnerSessionBeanLocal.createNewNurse(newNurse);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Your account  : " + nurse.getEmail() + " is being created and application is being processed", null));
            
        }
    }

    public void save() {
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + newDoctor.getFirstName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
    public NurseEntity getNewNurse() {
        return newNurse;
    }

    public void setNewNurse(NurseEntity newNurse) {
        this.newNurse = newNurse;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StaffEntity getStaffToUpdate() {
        return staffToUpdate;
    }

    public void setStaffToUpdate(StaffEntity staffToUpdate) {
        this.staffToUpdate = staffToUpdate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
