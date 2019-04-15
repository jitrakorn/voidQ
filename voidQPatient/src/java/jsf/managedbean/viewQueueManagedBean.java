/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.entity.BookingEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.PatientEntity;
import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.enumeration.BookingStatus;

/**
 *
 * @author terencetay
 */
@Named(value = "viewQueueManagedBean")
@ViewScoped
public class viewQueueManagedBean implements Serializable {

    @EJB
    private PartnerSessionBeanLocal partnerSessionBean;

    @EJB
    private BookingSessionBeanLocal bookingSessionBean;
    

    private BookingEntity currentBooking;
    private PatientEntity currentPatient;
    private List<BookingEntity> currentDayBookings;

    private Integer currentPatientCurrentPosition;

    /**
     * Creates a new instance of viewQueueManagedBean
     */
    public viewQueueManagedBean() {
        currentPatientCurrentPosition = 1;
    }

    @PostConstruct
    public void postConstruct() {
        currentPatient = (PatientEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPatient");
        currentBooking = (BookingEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentBooking");
        currentDayBookings = bookingSessionBean.getClinicCurrentDayBookings(currentBooking.getClinicEntity());
           
        for (BookingEntity currentBooking : currentDayBookings) {
            if (!currentBooking.getPatientEntity().equals(currentPatient) && currentBooking.getStatus().equals(BookingStatus.CHECKED_IN)) {
                currentPatientCurrentPosition += 1;
            }
        }
    }
    
    public void checkin() {
        currentBooking.setStatus(BookingStatus.CHECKED_IN);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, currentBooking.getPatientEntity().getFirstName() + " " + currentBooking.getPatientEntity().getLastName() + " has checked in!", null));

            if (partnerSessionBean.hasAvailableDoctors(currentBooking.getClinicEntity())) {
                currentBooking.setStatus(BookingStatus.VISITING);
                DoctorEntity appointedDoctor = partnerSessionBean.appointAvailableDoctor(currentBooking.getClinicEntity(), currentBooking);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, currentBooking.getPatientEntity().getFirstName() + " " + currentBooking.getPatientEntity().getLastName() + " is now visiting " + appointedDoctor.getFirstName() + " " + currentBooking.getDoctorEntity().getLastName() + "!", null));
            }

            bookingSessionBean.updateBooking(currentBooking);
            
            // needs to send a poll to partner to refresh their screen
    }
    
    public void pay() {
        if (currentBooking.getStatus() != BookingStatus.VISITED) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Booking status isn't 'visited'. Unable to mark patient as paid.", null));
        } else {
            // perhaps some real payment methods here
            currentBooking.setStatus(BookingStatus.PAID);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, currentBooking.getPatientEntity().getFirstName() + " " + currentBooking.getPatientEntity().getLastName() + " has paid!", null));

            bookingSessionBean.updateBooking(currentBooking);
        }
    }

    public BookingEntity getCurrentBooking() {
        return currentBooking;
    }

    public void setCurrentBooking(BookingEntity currentBooking) {
        this.currentBooking = currentBooking;
    }

    public List<BookingEntity> getCurrentDayBookings() {
        return currentDayBookings;
    }

    public void setCurrentDayBookings(List<BookingEntity> currentDayBookings) {
        this.currentDayBookings = currentDayBookings;
    }

    public PatientEntity getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(PatientEntity currentPatient) {
        this.currentPatient = currentPatient;
    }

    public Integer getCurrentPatientCurrentPosition() {
        return currentPatientCurrentPosition;
    }

    public void setCurrentPatientCurrentPosition(Integer currentPatientCurrentPosition) {
        this.currentPatientCurrentPosition = currentPatientCurrentPosition;
    }

}
