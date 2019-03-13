package jsf.managedbean;


import ejb.entity.AdminEntity;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
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
    private AdminEntity adminToView;
    
     private boolean isDisabled = true;
      private String textValue = "Enable Edit";
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
    
     public void enableEdit(ActionEvent event)
    {
        isDisabled = !isDisabled;
         if(isDisabled)
         {
             textValue = "Enable Edit";
             
         }
         else
         {
             textValue = "Disable Edit";
         }
    }
    
      public void updateAdmin(ActionEvent event)
    {
       
        try
        {
            administratorSessionBeanLocal.updateAdmin(adminToView);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin updated successfully", null));
            isDisabled = !isDisabled;
             textValue = "Enable Edit";
        }
        catch(AdministratorNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating staff: " + ex.getMessage(), null));
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

    

    public AdminEntity getAdminToView() {
        return adminToView;
    }

    public void setAdminToView(AdminEntity adminToView) {
        this.adminToView = adminToView;
    }

    public Long getAdminIdToView() {
        return adminIdToView;
    }

    public void setAdminIdToView(Long adminIdToView) {
        this.adminIdToView = adminIdToView;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

  
    
    

}
