package jsf.managedbean;


import ejb.entity.StaffEntity;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import util.enumeration.ApplicationStatus;
import util.exception.PartnerNotFoundException;



@Named(value = "viewClinicDetailsManagedBean")
@ViewScoped

public class ViewClinicDetailsManagedBean implements Serializable
{

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

  


    
    private Long staffIdToView;
    private StaffEntity staffToView;
    
    
      private boolean isDisabled = true;

    private String textValue = "Enable Edit";
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
    
     public void reset() {
        PrimeFaces.current().resetInputs("formUser:userPanel");
    }
    public void enableEdit() 
    {
         PrimeFaces.current().resetInputs("formUser:userPanel");
        System.out.println("beeofre" + isDisabled);
         isDisabled = !isDisabled;
         System.out.println("after" + isDisabled);
         if(isDisabled)
         {
             
             textValue = "Enable Edit";
            
         }
         else
         {
             textValue = "Disable Edit";
             
         }
            System.out.println(textValue);
    }
    
    
    public void updateClinic() 
            
    {
         try
        {
            partnerSessionBeanLocal.updatePartner(staffToView.getClinicEntity());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Clinic updated successfully", null));
            isDisabled = !isDisabled;
             textValue = "Enable Edit";
            
        }
        catch(PartnerNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating Clinic: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
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
