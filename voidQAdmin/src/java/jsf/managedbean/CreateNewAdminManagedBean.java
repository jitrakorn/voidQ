package jsf.managedbean;


import ejb.entity.AdminEntity;
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

 
    
    
    private AdminEntity newAdmin;
    
    
    
    public CreateNewAdminManagedBean() 
    {
        newAdmin = new AdminEntity();
    }
    
 

    
    @PostConstruct
    public void postConstruct()
    {
   
    }
    
    
    
    public void createNewAdmin(ActionEvent event)
    {
       
        try
        {
            AdminEntity admin = administratorSessionBeanLocal.createNewAdmin(newAdmin);
            newAdmin = new AdminEntity();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Admin created successfully (Admin ID: " + admin.getUserId()+ ")", null));
        }
        catch(InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new admin: " + ex.getMessage(), null));
        }
    }

    public AdminEntity getNewAdmin() {
        return newAdmin;
    }

    public void setNewAdmin(AdminEntity newAdmin) {
        this.newAdmin = newAdmin;
    }

    
    
    
   
    
}
