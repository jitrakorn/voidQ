package jsf.helper;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

@Named(value = "notificationManagedBean")
@ViewScoped
public class NotificationManagedBean implements Serializable {

    @Inject
    @Push
    private PushContext someChannel;
    String notify = "A new announcement has been posted";

    public void sendMessage(Object message) {
        someChannel.send(message);
    }
 public void execute() {
     StringBuilder sb = new StringBuilder("<html><body>");
sb.append("<a href='www.comp.nus.edu.sg'>Click here for more</a>");
sb.append("</body></html>");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, notify, sb.toString()));
    }
 /*
                                                                            <br /><a href="#" onclick=\"" + test + "\"> Next</a>
 */
}
