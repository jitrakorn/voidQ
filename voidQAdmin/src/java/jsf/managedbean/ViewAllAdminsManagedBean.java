package jsf.managedbean;

import ejb.entity.AdminEntity;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;



@Named(value = "viewAllAdminsManagedBean")
@RequestScoped

public class ViewAllAdminsManagedBean 
{

    @EJB(name = "AdministratorSessionBeanLocal")
    private AdministratorSessionBeanLocal administratorSessionBeanLocal;

    private  List<AdminEntity> admins;
    

  
    public ViewAllAdminsManagedBean() 
    {
    }
    
    
    
    @PostConstruct
    public void postConstruct()
    {
        admins = administratorSessionBeanLocal.retrieveAllAdministrators();
    }
    
    
    
    public void viewAdminDetails(ActionEvent event) throws IOException
    {
        Long productIdToView = (Long)event.getComponent().getAttributes().get("adminId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("adminIdToView", productIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewAdminDetails.xhtml");
    }

    public List<AdminEntity> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AdminEntity> admins) {
        this.admins = admins;
    }

   
  
}
