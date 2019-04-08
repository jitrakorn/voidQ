/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.StaffEntity;
import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import util.exception.PartnerNotFoundException;

/**
 *
 * @author terencetay
 */
@Named(value = "partnerBookingManagedBean")
@ViewScoped
public class partnerBookingManagedBean implements Serializable {

    @EJB
    private PartnerSessionBeanLocal partnerSessionBean;

    @EJB
    private BookingSessionBeanLocal bookingSessionBean;
    
    private ScheduleModel schedule;
    private StaffEntity currentStaff;
    private ClinicEntity currentClinic;
    private List<BookingEntity> bookings;

    /**
     * Creates a new instance of partnerBookingManagedBean
     */
    public partnerBookingManagedBean() {
        schedule = new DefaultScheduleModel();
    }
    
    @PostConstruct
    public void postConstruct() {
        currentStaff = (StaffEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPartner");
        
        currentClinic = partnerSessionBean.getPartnerById(currentStaff.getClinicEntity().getClinicId());
        
        if(!bookingSessionBean.getBookingsByClinicId(currentClinic.getClinicId()).isEmpty()) {
            bookings = bookingSessionBean.getBookingsByClinicId(currentClinic.getClinicId());
            for (BookingEntity booking : bookings) {
                schedule.addEvent(new DefaultScheduleEvent("Appointment: " + booking.getPatientEntity().getEmail(), booking.getTransactionDateTime(), booking.getTransactionDateTime()));
            }
        } 
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }
}
