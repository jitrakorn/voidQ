package ejb.session.singleton;

import ejb.entity.AdminEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.PatientEntity;
import ejb.entity.StaffEntity;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.session.stateless.PatientSessionBeanLocal;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.ApplicationStatus;
import util.exception.AdministratorNotFoundException;
import util.exception.InputDataValidationException;

@Singleton
@LocalBean
@Startup

public class DataInitializationSessionBean {

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
            administratorSessionBeanLocal.retrieveAdminByUsername("lovemx93@gmail.com");
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

            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Etern Medical Clinic", "General Medical Practioner", "70 Punggol Central, #01–05, Punggol Mrt Station, Singapore – 828868", new BigDecimal(10), ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Healthmark Medical Clinic", "General Medical Practioner", "639 Punggol Drive, #01–06, Singapore – 820639", new BigDecimal(20), ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Healthway Medical Clinic", "General Medical Practioner", "273c Punggol Place, #01–874, Singapore – 823273", new BigDecimal(30), ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("Mutual Healthcare Medical Clinic (Punggol)", "General Medical Practioner", "106A Punggol Field, #01–546, Singapore – 821106", new BigDecimal(40), ApplicationStatus.ACTIVATED));
            partnerSessionBeanLocal.createNewPartner(new ClinicEntity("My Family Clinic (Punggol Central)", "General Medical Practioner", "301 Punggol Central, #01–02, Singapore – 820301", new BigDecimal(50), ApplicationStatus.ACTIVATED));

            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff1@gmail.com", "password", "Staff", "One", "Doctor", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), 97645232));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff2@gmail.com", "password", "Staff", "Two", "Doctor", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(2L), 91830399));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff3@gmail.com", "password", "Staff", "Three", "Doctor", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(3L), 84756793));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff4@gmail.com", "password", "Staff", "Four", "Doctor", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(4L), 91023456));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff5@gmail.com", "password", "Staff", "Five", "Doctor", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(5L), 81735432));

            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff6@gmail.com", "password", "Staff", "Six", "Nurse", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), 81928475));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff7@gmail.com", "password", "Staff", "Seven", "Nurse", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(2L), 90987754));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff8@gmail.com", "password", "Staff", "Eight", "Nurse", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(3L), 88925432));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff9@gmail.com", "password", "Staff", "Nine", "Nurse", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(4L), 98375431));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff10@gmail.com", "password", "Staff", "Ten", "Nurse", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(5L), 90128907));
            
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff11@gmail.com", "password", "Staff", "Eleven", "Nurse", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(1L), 87987654));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff12@gmail.com", "password", "Staff", "Twelve", "Nurse", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(2L), 81247809));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff13@gmail.com", "password", "Staff", "Thirteen", "Nurse", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(3L), 95861253));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff14@gmail.com", "password", "Staff", "Fourteen", "Nurse", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(4L), 81728294));
            partnerSessionBeanLocal.createNewStaff(new StaffEntity("Staff15@gmail.com", "password", "Staff", "Fifteen", "Nurse", "Available", partnerSessionBeanLocal.retrievePartnerByPartnerId(5L), 88771190));
            
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient1@gmail.com", "password", "Patient", "One", 91833264));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient2@gmail.com", "password", "Patient", "Two", 90987856));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient3@gmail.com", "password", "Patient", "Three", 80123267));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient4@gmail.com", "password", "Patient", "Four", 98745321));
            patientSessionBeanLocal.createNewPatient(new PatientEntity("Patient5@gmail.com", "password", "Patient", "Five", 99182034));
//
//            ClinicEntity ce = partnerSessionBeanLocal.createNewPartner(new ClinicEntity("mx clinic", "best clinic", "geylang hotel 81", new BigDecimal(100), ApplicationStatus.ACTIVATED));
//            StaffEntity se = new StaffEntity("lovemx93@gmail.com", "password", "mx", "mx", "doctor", "not taken", ce, 96658673);
//            em.persist(se);
//            PatientEntity pe = new PatientEntity("lovemx93@gmail.com", "password", "mx", "mx", 958673, ce);
//            em.persist(pe);
//            ce.getPatientEntity().add(pe);

        } catch (InputDataValidationException ex) {
            System.err.println("********** DataInitializationSessionBean.initializeData(): " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("********** DataInitializationSessionBean.initializeData(): An error has occurred while loading initial test data: " + ex.getMessage());
        }
    }

}
