package jsf.managedbean;

import ejb.entity.Administrator;
import ejb.session.stateless.AdministratorSessionBeanLocal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import util.exception.InputDataValidationException;



@Named(value = "createNewAdminManagedBean")
@RequestScoped
public class CreateNewAdminManagedBean 
{

    @EJB(name = "AdministratorSessionBeanLocal")
    private AdministratorSessionBeanLocal administratorSessionBeanLocal;

 
    
    
    private Administrator newAdmin;
    
    
    
    public CreateNewAdminManagedBean() 
    {
        newAdmin = new Administrator();
    }
    
 

    
    @PostConstruct
    public void postConstruct()
    {
   
    }
    
    
    
    public void createNewAdmin(ActionEvent event)
    {
       
        try
        {
            Administrator admin = administratorSessionBeanLocal.createNewAdmin(newAdmin);
            newAdmin = new Administrator();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Admin created successfully (Staff ID: " + admin.getAdministratorId()+ ")", null));
        }
        catch(InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new admin: " + ex.getMessage(), null));
        }
    }

    public Administrator getNewAdmin() {
        return newAdmin;
    }

    public void setNewAdmin(Administrator newAdmin) {
        this.newAdmin = newAdmin;
    }

    
    
    
   
    
}
