package jsf.managedbean;


import ejb.entity.AdminEntity;
import ejb.entity.StaffEntity;
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
import util.enumeration.ApplicationStatus;
import util.exception.AdministratorNotFoundException;



@Named(value = "viewClinicDetailsManagedBean")
@ViewScoped

public class ViewClinicDetailsManagedBean implements Serializable
{

  


    
    private Long staffIdToView;
    private StaffEntity staffToView;
    
    
    
    public ViewClinicDetailsManagedBean() 
    {
    }
    
    
    
    @PostConstruct
    public void postConstruct()
    {
        staffToView = (StaffEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPartner");
      
       
    }
    

    
    
    
    public void foo()
    {        
    }
    
    
    
    public void updateClinic(ActionEvent event) throws IOException
    {
        if(staffToView.getClinicEntity().getApplicationStatus().equals(ApplicationStatus.ACTIVATED))
        {
           FacesContext.getCurrentInstance().getExternalContext().getFlash().put("staffIdToUpdate", staffIdToView);
       FacesContext.getCurrentInstance().getExternalContext().redirect("updateClinic.xhtml");   
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fuck off your application status not granted", null));
        }
      
    }
    
    
     public void resetPassword(ActionEvent event) throws IOException
    {
       
       
        //System.out.println(adminIdToView);

     //  administratorSessionBeanLocal.resetPassword(adminIdToView);
     //   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Password sent to email", null));

    }
    
    
    public void deleteAdmin(ActionEvent event) throws IOException
    {
      //  FacesContext.getCurrentInstance().getExternalContext().getFlash().put("adminIdToDelete", adminIdToView);
       // FacesContext.getCurrentInstance().getExternalContext().redirect("deleteAdmin.xhtml");
    }

    public Long getStaffIdToView() {
        return staffIdToView;
    }

    public void setStaffIdToView(Long staffIdToView) {
        this.staffIdToView = staffIdToView;
    }

    public StaffEntity getStaffToView() {
        return staffToView;
    }

    public void setStaffToView(StaffEntity staffToView) {
        this.staffToView = staffToView;
    }

    

   

  
    
    

}
