package jsf.managedbean;

import ejb.entity.Administrator;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.AdministratorNotFoundException;



@Named(value = "updateAdminManagedBean")
@ViewScoped

public class UpdateAdminManagedBean implements Serializable
{

    @EJB(name = "AdministratorSessionBeanLocal")
    private AdministratorSessionBeanLocal administratorSessionBeanLocal;


   private Long adminIdToUpdate;
    private Administrator adminToUpdate;
   

    
    
    
    public UpdateAdminManagedBean() 
    {        
    }
    
       
    
    @PostConstruct
    public void postConstruct()
    {
        adminIdToUpdate = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("adminIdToUpdate");
        
        try
        {            
           adminToUpdate = administratorSessionBeanLocal.retrieveAdminByAdminId(adminIdToUpdate);
           
        }
        catch(AdministratorNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the Admin details: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    
    
    public void back(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("adminIdToView", adminIdToUpdate);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewAdminDetails.xhtml");
    }
    
    
    
    public void foo()
    {        
    }
    
    
    
    public void updateAdmin(ActionEvent event)
    {
       
        try
        {
            administratorSessionBeanLocal.updateAdmin(adminToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin updated successfully", null));
        }
        catch(AdministratorNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating admin: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public Administrator getAdminToUpdate() {
        return adminToUpdate;
    }

    public void setAdminToUpdate(Administrator adminToUpdate) {
        this.adminToUpdate = adminToUpdate;
    }

    
    
    
    
   
}
