package jsf.managedbean;

import ejb.entity.Administrator;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.AdministratorNotFoundException;



@Named(value = "viewAdminDetailsManagedBean")
@ViewScoped

public class ViewAdminDetailsManagedBean implements Serializable
{

    @EJB(name = "AdministratorSessionBeanLocal")
    private AdministratorSessionBeanLocal administratorSessionBeanLocal;


    
    private Long adminIdToView;
    private Administrator adminToView;
    
    
    
    public ViewAdminDetailsManagedBean() 
    {
    }
    
    
    
    @PostConstruct
    public void postConstruct()
    {
        adminIdToView = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("adminIdToView");
        
        try
        {            
            adminToView = administratorSessionBeanLocal.retrieveAdminByAdminId(adminIdToView);
        }
        catch(AdministratorNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the admin details: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    
    
    public void back(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllAdmins.xhtml");
    }
    
    
    
    public void foo()
    {        
    }
    
    
    
    public void updateAdmin(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("adminIdToUpdate", adminIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("updateAdmin.xhtml");
    }
    
    
     public void resetPassword(ActionEvent event) throws IOException
    {
       
       
        System.out.println(adminIdToView);

       administratorSessionBeanLocal.resetPassword(adminIdToView);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Password sent to email", null));

    }
    
    
    public void deleteAdmin(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("adminIdToDelete", adminIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("deleteAdmin.xhtml");
    }

    

    public Administrator getAdminToView() {
        return adminToView;
    }

    public void setAdminToView(Administrator adminToView) {
        this.adminToView = adminToView;
    }

  
    
    

}
