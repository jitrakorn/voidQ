package jsf.managedbean;


import ejb.entity.StaffEntity;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import util.exception.InvalidLoginCredentialException;



@Named(value = "partnerLoginManagedBean")
@RequestScoped
public class PartnerLoginManagedBean {

    @EJB
    private PartnerSessionBeanLocal partnerSessionBeanLocal;
    
    private String username;
    private String password;
    
    
    
    public PartnerLoginManagedBean() 
    {
    }
    
    public void login(ActionEvent event) throws IOException
    {
        try
        {
            StaffEntity currentPartner = partnerSessionBeanLocal.emailLogin(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentPartner", currentPartner);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("partnerClass", currentPartner.getClass());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        }
        catch(InvalidLoginCredentialException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }
    
    public void logout(ActionEvent event) throws IOException
    {
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
