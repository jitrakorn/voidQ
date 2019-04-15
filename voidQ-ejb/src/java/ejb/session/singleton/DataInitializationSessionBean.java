package ejb.session.singleton;

import ejb.entity.AdminEntity;
import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.PatientEntity;
import ejb.entity.NurseEntity;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.session.stateless.PatientSessionBeanLocal;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.ApplicationStatus;
import util.enumeration.Availability;
import util.enumeration.BookingStatus;
import util.exception.AdministratorNotFoundException;
import util.exception.InputDataValidationException;

@Singleton
@LocalBean
@Startup

public class DataInitializationSessionBean {

    @EJB
    private BookingSessionBeanLocal bookingSessionBean;

    @EJB
    private PatientSessionBeanLocal patientSessionBeanLocal;

    @EJB(name = "AdministratorSessionBeanLocal")
    private AdministratorSessionBeanLocal administratorSessionBeanLocal;

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    public DataInitializationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            administratorSessionBeanLocal.retrieveAdminByUsername("Lovemx93@gmail.com");
        } catch (AdministratorNotFoundException ex) {
            initializeData();
        }
    }

    private void initializeData() {
        try {
            administratorSessionBeanLocal.createNewAdmin(new AdminEntity("Mingxuan", "Oh", "Lovemx93@gmail.com", "password", 96658673));
            administratorSessionBeanLocal.createNewAdmin(new AdminEntity("Terence", "Tay", "Terencetay95@gmail.com", "password", 98337834));
            administratorSessionBeanLocal.createNewAdmin(new AdminEntity("Jitrakorn", "Tan", "Jitrakorntan@gmail.com", "password", 81132795));
            administratorSessionBeanLocal.createNewAdmin(new AdminEntity("Pamela", "Teo", "Pamelameteo@gmail.com", "password", 98198418));

            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Etern Medical Clinic", "General Medical Practioner", "70 Punggol Central, #01–05, Punggol Mrt Station, Singapore – 828868","91833223", new BigDecimal(10),"1.404701,103.902205", ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Healthmark Medical Clinic", "General Medical Practioner", "639 Punggol Drive, #01–06, Singapore – 820639", "91833212", new BigDecimal(20),"1.399476,103.915997", ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Healthway Medical Clinic", "General Medical Practioner", "273c Punggol Place, #01–874, Singapore – 823273","91833290", new BigDecimal(30),"1.402249,103.901362", ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Mutual Healthcare Medical Clinic (Punggol)", "General Medical Practioner", "106A Punggol Field, #01–546, Singapore – 821106", "91833236", new BigDecimal(40),"1.403033,103.897750", ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("My Family Clinic (Punggol Central)", "General Medical Practioner", "301 Punggol Central, #01–02, Singapore – 820301", "91833281", new BigDecimal(50), "1.403583,103.905993", ApplicationStatus.ACTIVATED));

            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor1A@gmail.com", "password", "Doctor", "OneA", partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor1B@gmail.com", "password", "Doctor", "OneB", partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(2L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor2A@gmail.com", "password", "Doctor", "TwoA", partnerSessionBeanLocal.retrievePartnerByPartnerId(2L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(2L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor2B@gmail.com", "password", "Doctor", "TwoB", partnerSessionBeanLocal.retrievePartnerByPartnerId(2L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(3L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor3A@gmail.com", "password", "Doctor", "ThreeA", partnerSessionBeanLocal.retrievePartnerByPartnerId(3L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(3L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor3B@gmail.com", "password", "Doctor", "ThreeB", partnerSessionBeanLocal.retrievePartnerByPartnerId(3L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(4L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor4A@gmail.com", "password", "Doctor", "FourA", partnerSessionBeanLocal.retrievePartnerByPartnerId(4L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(4L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor4B@gmail.com", "password", "Doctor", "FourB", partnerSessionBeanLocal.retrievePartnerByPartnerId(4L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(5L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor5A@gmail.com", "password", "Doctor", "FiveA", partnerSessionBeanLocal.retrievePartnerByPartnerId(5L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(5L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor5B@gmail.com", "password", "Doctor", "FiveB", partnerSessionBeanLocal.retrievePartnerByPartnerId(5L), Availability.AVAILABLE)));

            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse1A@gmail.com", "password", "Nurse", "OneA", partnerSessionBeanLocal.retrievePartnerByPartnerId(1L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse1B@gmail.com", "password", "Staff", "OneB", partnerSessionBeanLocal.retrievePartnerByPartnerId(1L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(2L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse2A@gmail.com", "password", "Nurse", "TwoA", partnerSessionBeanLocal.retrievePartnerByPartnerId(2L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(2L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse2B@gmail.com", "password", "Staff", "TwoB", partnerSessionBeanLocal.retrievePartnerByPartnerId(2L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(3L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse3A@gmail.com", "password", "Nurse", "ThreeA", partnerSessionBeanLocal.retrievePartnerByPartnerId(3L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(3L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse3B@gmail.com", "password", "Staff", "ThreeB", partnerSessionBeanLocal.retrievePartnerByPartnerId(3L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(4L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse4A@gmail.com", "password", "Nurse", "FourA", partnerSessionBeanLocal.retrievePartnerByPartnerId(4L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(4L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse4B@gmail.com", "password", "Staff", "FourB", partnerSessionBeanLocal.retrievePartnerByPartnerId(4L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(5L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse5A@gmail.com", "password", "Nurse", "FiveA", partnerSessionBeanLocal.retrievePartnerByPartnerId(5L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(5L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse5B@gmail.com", "password", "Staff", "FiveB", partnerSessionBeanLocal.retrievePartnerByPartnerId(5L))));

            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(1L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(2L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(3L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(4L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(5L));

            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient1@gmail.com", "password", "Patient", "One", 91833264));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient2@gmail.com", "password", "Patient", "Two", 90987856));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient3@gmail.com", "password", "Patient", "Three", 80123267));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient4@gmail.com", "password", "Patient", "Four", 98745321));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient5@gmail.com", "password", "Patient", "Five", 99182034));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient6@gmail.com", "password", "Patient", "Six", 91833265));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient7@gmail.com", "password", "Patient", "Seven", 90987856));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient8@gmail.com", "password", "Patient", "Eight", 80123265));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient9@gmail.com", "password", "Patient", "Nine", 98745325));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient0@gmail.com", "password", "Patient", "Ten", 99182534));

            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.VISITED, new Date(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(1L), "Fever")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.BOOKED, new Date(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(2L), "Stomach Flu")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.BOOKED, new Date(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(3L), "Headache")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.BOOKED, new Date(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(4L), "Cramps")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.BOOKED, new Date(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(5L), "Suspected Influenza A")));

            // Simulating past bookings
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DATE, -1);
            yesterday.set(Calendar.HOUR_OF_DAY, 0);
            yesterday.set(Calendar.MINUTE, 0);
            yesterday.set(Calendar.SECOND, 0);

            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.PAID, yesterday.getTime(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(6L), "Fever")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.PAID, yesterday.getTime(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(7L), "Stomach Flu")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.PAID, yesterday.getTime(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(8L), "Headache")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.PAID, yesterday.getTime(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(9L), "Cramps")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.PAID, yesterday.getTime(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(10L), "Suspected Influenza A")));

            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(1L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(2L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(3L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(4L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(5L));
            
        } catch (InputDataValidationException ex) {
            System.err.println("********** DataInitializationSessionBean.initializeData(): " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("********** DataInitializationSessionBean.initializeData(): An error has occurred while loading initial test data: " + ex.getMessage());
        }
    }

}
