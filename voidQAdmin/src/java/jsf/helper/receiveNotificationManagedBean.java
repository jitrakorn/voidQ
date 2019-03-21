package jsf.helper;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named(value = "receiveNotificationManagedBean")
@ViewScoped
public class receiveNotificationManagedBean implements Serializable {

 /*   @Inject
    @Push(channel = "partner")
    private PushContext newPartnerChannel;
*/
    String notify2 = "New partner application for approval";

    public void execute2() {
        StringBuilder sb = new StringBuilder("<html><body>");
        sb.append("<a href='www.comp.nus.edu.sg'>Click here for more</a>");
        sb.append("</body></html>");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, notify2, sb.toString()));
    }
    /*
                                                                            <br /><a href="#" onclick=\"" + test + "\"> Next</a>
     */
}
