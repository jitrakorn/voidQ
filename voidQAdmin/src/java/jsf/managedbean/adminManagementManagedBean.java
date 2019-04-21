package jsf.managedbean;

import ejb.entity.AdminEntity;
import ejb.session.stateless.AdministratorSessionBeanLocal;
import ejb.sms.SMS;
import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.AdministratorNotFoundException;
import util.exception.DeleteAdminException;
import util.exception.InputDataValidationException;

@Named(value = "adminManagementManagedBean")
@ViewScoped

public class adminManagementManagedBean implements Serializable {

    @EJB(name = "AdministratorSessionBeanLocal")
    private AdministratorSessionBeanLocal administratorSessionBeanLocal;

    private static SecureRandom random = new SecureRandom();

    private List<AdminEntity> admins;
    private AdminEntity newAdmin;
    private AdminEntity selectedAdminEntityToView;
    private AdminEntity selectedAdminEntityToUpdate;
    private List<AdminEntity> filteredAdminEntities;

    public adminManagementManagedBean() {
        newAdmin = new AdminEntity();
    }

    @PostConstruct
    public void postConstruct() {
        admins = administratorSessionBeanLocal.retrieveAllAdministrators();
    }

    public void doUpdateAdmin(ActionEvent event) {
        selectedAdminEntityToUpdate = (AdminEntity) event.getComponent().getAttributes().get("adminEntityToUpdate");
    }

    public static String generatePassword(int len, String dic) {
        String result = "";
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(dic.length());
            result += dic.charAt(index);
        }
        return result;
    }

    public void resetPassword(ActionEvent event) {
        AdminEntity adminEntityToReset = (AdminEntity) event.getComponent().getAttributes().get("adminEntityToReset");
        try {

            String password = generatePassword(6, "abcdefghijklmnopqrstuvwxyz");
            administratorSessionBeanLocal.updateAdminPassword(adminEntityToReset.getUserId(), password);
            SMS.sendPost(password, adminEntityToReset.getPhoneNumber());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Password sent to registered phone number successfully", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while resetting password: " + ex.getMessage(), null));
        }
    }

    public void updateAdmin(ActionEvent event) {

        try {
            administratorSessionBeanLocal.updateAdmin(selectedAdminEntityToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin updated successfully", null));
        } catch (AdministratorNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating admin: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void createNewAdmin(ActionEvent event) {

        try {
            AdminEntity admin = administratorSessionBeanLocal.createNewAdmin(newAdmin);
            newAdmin = new AdminEntity();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Admin created successfully (Admin ID: " + admin.getUserId() + ")", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new admin: " + ex.getMessage(), null));
        }
    }

    public void deleteAdmin(ActionEvent event) {
        try {
            AdminEntity adminEntityToDelete = (AdminEntity) event.getComponent().getAttributes().get("adminEntityToDelete");
            administratorSessionBeanLocal.deleteAdmin(adminEntityToDelete.getUserId());

            admins.remove(adminEntityToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin deleted successfully", null));
        } catch (AdministratorNotFoundException | DeleteAdminException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting product: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void viewAdminDetails(ActionEvent event) throws IOException {
        Long productIdToView = (Long) event.getComponent().getAttributes().get("adminId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("adminIdToView", productIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewAdminDetails.xhtml");
    }

    public List<AdminEntity> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AdminEntity> admins) {
        this.admins = admins;
    }

    public AdminEntity getNewAdmin() {
        return newAdmin;
    }

    public void setNewAdmin(AdminEntity newAdmin) {
        this.newAdmin = newAdmin;
    }

    public AdminEntity getSelectedAdminEntityToView() {
        return selectedAdminEntityToView;
    }

    public void setSelectedAdminEntityToView(AdminEntity selectedAdminEntityToView) {
        this.selectedAdminEntityToView = selectedAdminEntityToView;
    }

    public List<AdminEntity> getFilteredAdminEntities() {
        return filteredAdminEntities;
    }

    public void setFilteredAdminEntities(List<AdminEntity> filteredAdminEntities) {
        this.filteredAdminEntities = filteredAdminEntities;
    }

    public AdminEntity getSelectedAdminEntityToUpdate() {
        return selectedAdminEntityToUpdate;
    }

    public void setSelectedAdminEntityToUpdate(AdminEntity selectedAdminEntityToUpdate) {
        this.selectedAdminEntityToUpdate = selectedAdminEntityToUpdate;
    }

}
