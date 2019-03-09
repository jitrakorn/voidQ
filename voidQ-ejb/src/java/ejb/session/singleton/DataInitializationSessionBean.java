package ejb.session.singleton;

import ejb.entity.AdminEntity;
import ejb.entity.ClinicEntity;
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
import util.enumeration.AccountStatus;
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
         ClinicEntity ce =   partnerSessionBeanLocal.createNewPartner(new ClinicEntity("mx clinic", "best clinic", "geylang hotel 81", new BigDecimal(100)));
             StaffEntity se =  new StaffEntity("lovemx93@gmail.com","password","mx","mx","doctor","not taken",ce);
            em.persist(se);
        
        ce.getStaffEntities().add(se);
            
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