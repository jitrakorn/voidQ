/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.PatientEntity;
import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author terencetay
 */
@Named(value = "clinicManagedBean")
@ViewScoped
public class clinicManagedBean implements Serializable {

    @EJB
    private BookingSessionBeanLocal bookingSessionBeanLocal;

    @EJB
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    private BookingEntity newBooking;
    
    /**
     * Creates a new instance of clinicManagedBean
     */
    public clinicManagedBean() {
        newBooking = new BookingEntity();
    }
    
    public List<ClinicEntity> getAllClinics() {
        return partnerSessionBeanLocal.retrieveAllPartners();
    }

    public BookingEntity getNewBooking() {
        return newBooking;
    }

    public void setNewBooking(BookingEntity newBooking) {
        this.newBooking = newBooking;
    }
    
    public void patientCreateBooking() {
        System.out.println("clinicManagedBean - createBooking:");
        System.out.println(newBooking.getVisitReason());
        System.out.println(newBooking.getClinicEntity().getClinicName());

        newBooking.setPatientEntity((PatientEntity)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPatient"));
        newBooking.setStatus("BOOKED");
        newBooking.setTransactionDateTime(new Date());
        
        newBooking = bookingSessionBeanLocal.createBooking(newBooking);
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentBooking", newBooking);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Your booking with " + newBooking.getClinicEntity().getClinicName() + " is complete! Your current queue number is " + bookingSessionBeanLocal.getCurrentQueue(newBooking.getClinicEntity().getClinicId()), null));

        newBooking.setVisitReason("");
    }
}
