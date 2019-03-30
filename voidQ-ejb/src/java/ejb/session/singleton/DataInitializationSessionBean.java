package ejb.session.singleton;

import ejb.entity.AdminEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.PatientEntity;
import ejb.entity.StaffEntity;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
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

public class DataInitializationSessionBean
{    

    @EJB(name = "AdministratorSessionBeanLocal")
    private AdministratorSessionBeanLocal administratorSessionBeanLocal;

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;
    @PersistenceContext(unitName = "voidQ-ejbPU")
    private EntityManager em;
   
    
    
    public DataInitializationSessionBean()
    {
    }
    
    
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            administratorSessionBeanLocal.retrieveAdminByUsername("lovemx93@gmail.com");
        }
        catch(AdministratorNotFoundException ex)
        {
            initializeData();
        }
    }
    
    
    
    
    private void initializeData()
    {
        try
        {
            administratorSessionBeanLocal.createNewAdmin(new AdminEntity("mx","mx","lovemx93@gmail.com","password"));
         ClinicEntity ce =   partnerSessionBeanLocal.createNewPartner(new ClinicEntity("mx clinic", "best clinic", "geylang hotel 81", new BigDecimal(100),ApplicationStatus.ACTIVATED));
             StaffEntity se =  new StaffEntity("lovemx93@gmail.com","password","mx","mx","doctor","not taken",ce);
            em.persist(se);
             StaffEntity se2 =  new StaffEntity("pam@gmail.com","password","pam","pam","doctor","not taken",ce);
            em.persist(se2);
            PatientEntity pe = new PatientEntity("lovemx93@gmail.com","password","mx","mx",958673,ce);
            em.persist(pe);
            ce.getPatientEntity().add(pe);
            ce.getStaffEntities().add(se2);
        

            
        }
        catch(InputDataValidationException ex)
        {
            System.err.println("********** DataInitializationSessionBean.initializeData(): " + ex.getMessage());
        }
        catch(Exception ex)
        {
            System.err.println("********** DataInitializationSessionBean.initializeData(): An error has occurred while loading initial test data: " + ex.getMessage());
        }
    }

   
}