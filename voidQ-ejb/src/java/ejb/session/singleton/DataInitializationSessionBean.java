package ejb.session.singleton;

import ejb.entity.AdminEntity;
import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.PatientEntity;
import ejb.entity.NurseEntity;
import ejb.entity.TransactionEntity;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
    @PersistenceContext(unitName = "voidQ-ejbPU")
    private EntityManager em;

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

            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Etern Medical Clinic", "General Medical Practioner", "70 Punggol Central, #01–05, Punggol Mrt Station, Singapore – 828868", "91833223", new BigDecimal(10), "1.404701", "103.902205", ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Healthmark Medical Clinic", "General Medical Practioner", "639 Punggol Drive, #01–06, Singapore – 820639", "91833212", new BigDecimal(20), "1.399476", "103.915997", ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Healthway Medical Clinic", "General Medical Practioner", "273c Punggol Place, #01–874, Singapore – 823273", "91833290", new BigDecimal(30), "1.402249", "103.901362", ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Mutual Healthcare Medical Clinic (Punggol)", "General Medical Practioner", "106A Punggol Field, #01–546, Singapore – 821106", "91833236", new BigDecimal(40), "1.403033", "103.897750", ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("My Family Clinic (Punggol Central)", "General Medical Practioner", "301 Punggol Central, #01–02, Singapore – 820301", "91833281", new BigDecimal(50), "1.403583", "103.905993", ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("BL Medical Associates Pte Ltd", "General Medical Practioner", "2 Science Park Drive #01-10 Ascent, Singapore – 820301", "62651052", new BigDecimal(55), "1.290410", "103.788727", ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Fetal Care Centre", "Hospital", "5 Lower Kent Ridge Road, Level 3, Kent Ridge Building, 2 National University, Singapore – 119074", "67722230", new BigDecimal(55), "1.296450", "103.782850", ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("University Health Centre", "Hospital", "20 Lower Kent Ridge Road, Singapore - 119080", "66015035", new BigDecimal(55), "1.290475", "103..852036", ApplicationStatus.ACTIVATED));

            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor1A@gmail.com", "password", "Patrick", "Lim", partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor1B@gmail.com", "password", "Eugene", "Tan", partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(2L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor2A@gmail.com", "password", "Eugene", "Eu", partnerSessionBeanLocal.retrievePartnerByPartnerId(2L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(2L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor2B@gmail.com", "password", "Tzykynn", "Tan", partnerSessionBeanLocal.retrievePartnerByPartnerId(2L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(3L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor3A@gmail.com", "password", "Leonardo", "Pang", partnerSessionBeanLocal.retrievePartnerByPartnerId(3L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(3L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor3B@gmail.com", "password", "Zhiyue", "Tan", partnerSessionBeanLocal.retrievePartnerByPartnerId(3L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(4L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor4A@gmail.com", "password", "Dave", "Tan", partnerSessionBeanLocal.retrievePartnerByPartnerId(4L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(4L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor4B@gmail.com", "password", "Zhengkai", "Liaw", partnerSessionBeanLocal.retrievePartnerByPartnerId(4L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(5L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor5A@gmail.com", "password", "Evelyn", "Wong", partnerSessionBeanLocal.retrievePartnerByPartnerId(5L), Availability.AVAILABLE)));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(5L).getDoctorEntities().add((DoctorEntity) partnerSessionBeanLocal.createNewStaff(new DoctorEntity("Doctor5B@gmail.com", "password", "Minli", "Tan", partnerSessionBeanLocal.retrievePartnerByPartnerId(5L), Availability.AVAILABLE)));

            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse1A@gmail.com", "password", "Bella", "Tan", partnerSessionBeanLocal.retrievePartnerByPartnerId(1L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse1B@gmail.com", "password", "Skye", "Ho", partnerSessionBeanLocal.retrievePartnerByPartnerId(1L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(2L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse2A@gmail.com", "password", "Brayden", "Tan", partnerSessionBeanLocal.retrievePartnerByPartnerId(2L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(2L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse2B@gmail.com", "password", "Xuejing", "Lim", partnerSessionBeanLocal.retrievePartnerByPartnerId(2L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(3L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse3A@gmail.com", "password", "James", "Wong", partnerSessionBeanLocal.retrievePartnerByPartnerId(3L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(3L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse3B@gmail.com", "password", "James", "Lim", partnerSessionBeanLocal.retrievePartnerByPartnerId(3L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(4L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse4A@gmail.com", "password", "James", "Tan", partnerSessionBeanLocal.retrievePartnerByPartnerId(4L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(4L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse4B@gmail.com", "password", "Eliza", "Wong", partnerSessionBeanLocal.retrievePartnerByPartnerId(4L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(5L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse5A@gmail.com", "password", "Joshua", "Yap", partnerSessionBeanLocal.retrievePartnerByPartnerId(5L))));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(5L).getNurseEntities().add((NurseEntity) partnerSessionBeanLocal.createNewStaff(new NurseEntity("Nurse5B@gmail.com", "password", "Jay", "Chen", partnerSessionBeanLocal.retrievePartnerByPartnerId(5L))));

            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(1L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(2L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(3L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(4L));
            partnerSessionBeanLocal.updatePartner(partnerSessionBeanLocal.retrievePartnerByPartnerId(5L));

            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient1@gmail.com", "password", "Justus", "Soh", 91833264));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient2@gmail.com", "password", "Constant", "Lim", 90987856));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient3@gmail.com", "password", "Ryan", "Lim", 80123267));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient4@gmail.com", "password", "Yiqun", "Heng", 98745321));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient5@gmail.com", "password", "Junjie", "Chua", 99182034));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient6@gmail.com", "password", "Jiaheng", "Tan", 91833265));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient7@gmail.com", "password", "Aaron", "Tan", 90987856));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient8@gmail.com", "password", "Daryl", "Lim", 80123265));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient9@gmail.com", "password", "Zeke", "Soh", 98745325));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient0@gmail.com", "password", "Jerome", "Lim", 96658673));

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
            
            for(BookingEntity booking : partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities()) {
                booking.setTransactionEntity(new TransactionEntity("PAID", new Date(), booking));
                em.merge(booking);
            }
            
            em.flush();
            
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.BOOKED, new Date(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(1L), "Fever")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.BOOKED, new Date(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(2L), "Stomach Flu")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.BOOKED, new Date(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(3L), "Headache")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.BOOKED, new Date(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(4L), "Cramps")));
            partnerSessionBeanLocal.retrievePartnerByPartnerId(1L).getBookingEntities().add((BookingEntity) bookingSessionBean.createBooking(new BookingEntity(BookingStatus.BOOKED, new Date(), partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), patientSessionBeanLocal.retrievePatientByPatientId(5L), "Suspected Influenza A")));

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

    public void persist(Object object) {
        em.persist(object);
    }

}
