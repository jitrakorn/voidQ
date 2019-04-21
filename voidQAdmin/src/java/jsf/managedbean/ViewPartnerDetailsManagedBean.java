package jsf.managedbean;


import ejb.entity.AdminEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.StaffEntity;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.AdministratorNotFoundException;



@Named(value = "viewPartnerDetailsManagedBean")
@ViewScoped

public class ViewPartnerDetailsManagedBean implements Serializable
{

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    
    private Long doctorIdToView;
    private DoctorEntity doctorToView;
    private boolean isDoctor;
    private boolean isNurse;
    private List<DoctorEntity> doctors;
    
     private boolean isDisabled = true;
      private String textValue = "Enable Edit";
    public ViewPartnerDetailsManagedBean() 
    {
         
    }
    
    
    
    @PostConstruct
    public void postConstruct()
    {
        doctorIdToView = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("doctorIdToView");
        
        try
        {            
            doctorToView = partnerSessionBeanLocal.retrieveDoctorByStaffId(doctorIdToView);
            DoctorEntity doctor = new DoctorEntity();
            if (doctorToView.getClass().equals(doctor.getClass())) {
              isDoctor = true;  
            } else {
                isNurse = true;
            }
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
    
      public void updateDoctor(ActionEvent event)
    {
       
        try
        {
            partnerSessionBeanLocal.updateDoctor(doctorToView);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin updated successfully", null));
            isDisabled = !isDisabled;
             textValue = "Enable Edit";
        }
       
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        
    }
    
    public void back(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllDoctors.xhtml");
    }
    
    
    
    public void foo()
    {        
    }
    
    
    
  
    
   
    
    
    public void deleteDoctor(ActionEvent event) throws IOException
    {
       // FacesContext.getCurrentInstance().getExternalContext().getFlash().put("adminIdToDelete", adminIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("deleteDoctor.xhtml");
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

    public Long getDoctorIdToView() {
        return doctorIdToView;
    }

    public void setDoctorIdToView(Long doctorIdToView) {
        this.doctorIdToView = doctorIdToView;
    }

    public DoctorEntity getDoctorToView() {
        return doctorToView;
    }

    public void setDoctorToView(DoctorEntity doctorToView) {
        this.doctorToView = doctorToView;
    }

    public List<DoctorEntity> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorEntity> doctors) {
        this.doctors = doctors;
    }

  
    
    

}
