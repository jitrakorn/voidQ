package ejb.session.singleton;

import ejb.entity.Administrator;
import ejb.entity.Partner;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
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
   
    
    
    public DataInitializationSessionBean()
    {
    }
    
    
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            administratorSessionBeanLocal.retrieveAdminByUsername("admin");
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
            administratorSessionBeanLocal.createNewAdmin(new Administrator("mx","mx","admin","password","lovemx93@gmail.com"));
            partnerSessionBeanLocal.createNewPartner(new Partner("mx", "mx clinic", "geylang hotel 81", "lovemx93@gmail.com", "password",AccountStatus.ACTIVATED));
          
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