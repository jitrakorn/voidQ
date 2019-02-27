package ejb.session.singleton;

import ejb.entity.Partner;
import ejb.session.stateless.PartnerSessionBeanLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.exception.InputDataValidationException;
import util.exception.PartnerNotFoundException;




@Singleton
@LocalBean
@Startup

public class DataInitializationSessionBean
{    

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
            partnerSessionBeanLocal.retrievePartnerByEmail("lovemx93@gmail.com");
        }
        catch(PartnerNotFoundException ex)
        {
            initializeData();
        }
    }
    
    
    
    private void initializeData()
    {
        try
        {
            partnerSessionBeanLocal.createNewPartner(new Partner("mx", "mx clinic", "geylang hotel 81", "lovemx93@gmail.com", "password"));
          
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