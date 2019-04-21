/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.StaffEntity;
import ejb.entity.TransactionEntity;
import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.session.stateless.TransactionSessionBeanLocal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import util.enumeration.BookingStatus;

/**
 *
 * @author terencetay
 */
@Named(value = "partnerBookingManagedBean")
@ViewScoped
public class partnerBookingManagedBean implements Serializable {

    @EJB
    private TransactionSessionBeanLocal transactionSessionBean;

    @EJB
    private PartnerSessionBeanLocal partnerSessionBean;

    @EJB
    private BookingSessionBeanLocal bookingSessionBean;

    private List<TransactionEntity> filteredTransactionEntities;
    private List<TransactionEntity> transactions;
    private final ScheduleModel schedule;
    private StaffEntity currentStaff;
    private ClinicEntity currentClinic;
    private List<BookingEntity> bookings;
    private List<BookingEntity> filteredBookingEntities;

    private ScheduleEvent event = new DefaultScheduleEvent();
    private BookingEntity booking;
    private String patientNameFromEvent;
    private String patientEmailFromEvent;
    private String bookingStatusFromEvent;
    private String doctorNameFromEvent;
    private String nurseNameFromEvent;

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
        transactions = transactionSessionBean.getAllTransactionsByClinicId(currentClinic.getClinicId());

        if (!bookingSessionBean.getBookingsByClinicId(currentClinic.getClinicId()).isEmpty()) {
            bookings = bookingSessionBean.getBookingsByClinicId(currentClinic.getClinicId());
            for (BookingEntity booking : bookings) {
                event = new DefaultScheduleEvent("Booking: " + booking.getBookingId(), booking.getTransactionDateTime(), booking.getTransactionDateTime(), booking);
                schedule.addEvent(event);
            }
        }
    }

    public void checkinPatient() {
        booking = (BookingEntity) event.getData();

        if (booking.getStatus() != BookingStatus.BOOKED) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Booking status isn't 'booked'. Unable to park patient as checked in.", null));
        } else {
            booking.setStatus(BookingStatus.CHECKED_IN);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, booking.getPatientEntity().getFirstName() + " " + booking.getPatientEntity().getLastName() + " has checked in!", null));

            if (partnerSessionBean.hasAvailableDoctors(currentClinic)) {
                booking.setStatus(BookingStatus.VISITING);
                DoctorEntity appointedDoctor = partnerSessionBean.appointAvailableDoctor(currentClinic, booking);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, booking.getPatientEntity().getFirstName() + " " + booking.getPatientEntity().getLastName() + " is now visiting " + appointedDoctor.getFirstName() + " " + booking.getDoctorEntity().getLastName() + "!", null));
            }

            bookingSessionBean.updateBooking(booking);
        }
    }

    public void visitedPatient() {
        booking = (BookingEntity) event.getData();

        if (booking.getStatus() != BookingStatus.VISITING) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Booking status isn't 'visiting'. Unable to mark patient as visited.", null));
        } else {
            booking.setStatus(BookingStatus.VISITED);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, booking.getPatientEntity().getFirstName() + " " + booking.getPatientEntity().getLastName() + " has been visited by " + booking.getDoctorEntity().getFirstName() + " " + booking.getDoctorEntity().getLastName() + "!", null));
            
            DoctorEntity appointedDoctor = booking.getDoctorEntity();
            partnerSessionBean.availDoctor(appointedDoctor);
            bookingSessionBean.updateBooking(booking);
            
            // get next check-in
            for(ScheduleEvent event : schedule.getEvents()) {
                booking = (BookingEntity) event.getData();
                if(booking.getStatus().equals(BookingStatus.CHECKED_IN)) {
                    booking.setStatus(BookingStatus.VISITING);
                    appointedDoctor = partnerSessionBean.appointAvailableDoctor(currentClinic, booking);
                    bookingSessionBean.updateBooking(booking);
                    schedule.updateEvent(event);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Next available patient " + booking.getPatientEntity().getFirstName() + " " + booking.getPatientEntity().getLastName() + " is now visiting " + appointedDoctor.getFirstName() + " " + booking.getDoctorEntity().getLastName() + "!", null));
                    break;
                }
            }
        }
    }

    public void noShowPatient() {
        booking = (BookingEntity) event.getData();

        if (booking.getStatus() != BookingStatus.BOOKED) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Booking status isn't 'booked'. Unable to mark patient as no-show.", null));
        } else {
            booking.setStatus(BookingStatus.NO_SHOW);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, booking.getPatientEntity().getFirstName() + " " + booking.getPatientEntity().getLastName() + " has been marked as no-show!", null));

            bookingSessionBean.updateBooking(booking);
        }
    }

    public void paidPatient() {
        booking = (BookingEntity) event.getData();

        if (booking.getStatus() != BookingStatus.VISITED) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Booking status isn't 'visited'. Unable to mark patient as paid.", null));
        } else {
            // perhaps some real payment methods here
            booking.setStatus(BookingStatus.PAID);
            booking.setTransactionEntity(new TransactionEntity("PAID", new Date(), booking));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, booking.getPatientEntity().getFirstName() + " " + booking.getPatientEntity().getLastName() + " has paid!", null));

            bookingSessionBean.updateBooking(booking);
        }
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public String getPatientNameFromEvent() {
        booking = (BookingEntity) event.getData();
        patientNameFromEvent = booking.getPatientEntity().getFirstName() + " " + booking.getPatientEntity().getLastName();
        return patientNameFromEvent;
    }

//    public void setPatientNameFromEvent(String patientNameFromEvent) {
//        this.patientNameFromEvent = patientNameFromEvent;
//    }
    public String getPatientEmailFromEvent() {
        booking = (BookingEntity) event.getData();
        patientEmailFromEvent = booking.getPatientEntity().getEmail();
        return patientEmailFromEvent;
    }

//    public void setPatientEmailFromEvent(String patientEmailFromEvent) {
//        this.patientEmailFromEvent = patientEmailFromEvent;
//    }
    public String getDoctorNameFromEvent() {
        booking = (BookingEntity) event.getData();
        if (booking.getDoctorEntity() == null) {
            doctorNameFromEvent = "Not Applicable";
        } else {
            doctorNameFromEvent = booking.getDoctorEntity().getFirstName() + " " + booking.getDoctorEntity().getLastName();
        }
        return doctorNameFromEvent;
    }

//    public void setDoctorNameFromEvent(String doctorNameFromEvent) {
//        this.doctorNameFromEvent = doctorNameFromEvent;
//    }
    public String getNurseNameFromEvent() {
        booking = (BookingEntity) event.getData();
        if (booking.getNurseEntity() == null) {
            nurseNameFromEvent = "Not Applicable";
        } else {
            nurseNameFromEvent = booking.getNurseEntity().getFirstName() + " " + booking.getNurseEntity().getFirstName();
        }
        return nurseNameFromEvent;
    }

//    public void setNurseNameFromEvent(String nurseNameFromEvent) {
//        this.nurseNameFromEvent = nurseNameFromEvent;
//    }
    public String getBookingStatusFromEvent() {
        booking = (BookingEntity) event.getData();
        bookingStatusFromEvent = booking.getStatus().toString().substring(0, 1).toUpperCase() + booking.getStatus().toString().substring(1).toLowerCase();
        return bookingStatusFromEvent;
    }

//    public void setBookingStatusFromEvent(String bookingStatusFromEvent) {
//        this.bookingStatusFromEvent = bookingStatusFromEvent;
//    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public List<BookingEntity> getFilteredBookingEntities() {
        return filteredBookingEntities;
    }

    public void setFilteredBookingEntities(List<BookingEntity> filteredBookingEntities) {
        this.filteredBookingEntities = filteredBookingEntities;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionEntity> getFilteredTransactionEntities() {
        return filteredTransactionEntities;
    }

    public void setFilteredTransactionEntities(List<TransactionEntity> filteredTransactionEntities) {
        this.filteredTransactionEntities = filteredTransactionEntities;
    }
    
    
}
