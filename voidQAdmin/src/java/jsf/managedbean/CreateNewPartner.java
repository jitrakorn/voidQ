package jsf.managedbean;

import ejb.entity.ClinicEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.StaffEntity;
import ejb.helper.Geocoding;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.json.JSONObject;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;
import util.enumeration.ApplicationStatus;

import util.exception.InputDataValidationException;

@Named(value = "createNewPartnerManagedBean")
@ViewScoped
public class CreateNewPartner implements Serializable {

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;
    private String postalcode;
    private ClinicEntity newClinic;
    private DoctorEntity newDoctor;
    private boolean skip;
    private String company;
    private List<SelectItem> companies;
    private UploadedFile file;
    private boolean renderFile;
    private Integer getUnApproved;
    private List<ClinicEntity> unapprovedList;
    private String rejectReason;
    private ClinicEntity selectedClinicEntityToView;

    public CreateNewPartner() {
        newClinic = new ClinicEntity();
        newDoctor = new DoctorEntity();
    }

    @PostConstruct
    public void postConstruct() {
        companies = new ArrayList<>();
        companies.add(new SelectItem("hahaha"));
        unapprovedList = partnerSessionBeanLocal.retrieveUnApprovedApplications();
        getUnApproved = partnerSessionBeanLocal.retrieveUnApprovedApplications().size();

    }

    public void renderFileUpload(AjaxBehaviorEvent Event) {

        // if selected item == NO
        //do nothing else 
        renderFile = true;
        System.out.println("run" + renderFile);
    }

    public void doUpdateClinic(ActionEvent event) {

        selectedClinicEntityToView = (ClinicEntity) event.getComponent().getAttributes().get("clinicEntityToUpdate");

    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void reject() {
        //call clicnic entity set message (rejectreason) selectedClinicEntityToView
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void loadAddress(AjaxBehaviorEvent event) {

        try {
            String hahaha = Geocoding.getJSONByGoogle(postalcode);
            System.out.println(hahaha);
            JSONObject jsonObj = new JSONObject(hahaha);
            //JSONArray jsonArr = new JSONArray(jsonObj.getJSONArray("results"));

            JSONObject loc = jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            String lat = loc.get("lat").toString();
            String lng = loc.get("lng").toString();
            String addressJSON = Geocoding.addressLookUp(lat + "," + lng);
            JSONObject locObj = new JSONObject(addressJSON);
            JSONObject locName = locObj.getJSONArray("results").getJSONObject(0);
            String name = locName.getString("formatted_address");
            newClinic.setAddress(name);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void createNewPartner() {

        try {
            newClinic.setApplicationStatus(ApplicationStatus.ACTIVATED);
            ClinicEntity partner = partnerSessionBeanLocal.createNewPartner(newClinic);
            partner.getDoctorEntities().add(newDoctor);
            newClinic = new ClinicEntity();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Clinic created successfully (Clinic ID: " + partner.getClinicId() + ")", null));
        } catch (InputDataValidationException ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new partner: " + ex.getMessage(), null));
        }
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public ClinicEntity getNewClinic() {
        return newClinic;
    }

    public void setNewClinic(ClinicEntity newClinic) {
        this.newClinic = newClinic;
    }

    public DoctorEntity getNewDoctor() {
        return newDoctor;
    }

    public void setNewDoctor(DoctorEntity newDoctor) {
        this.newDoctor = newDoctor;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<SelectItem> getCompanies() {
        return companies;
    }

    public void setCompanies(List<SelectItem> companies) {
        this.companies = companies;
    }

    public boolean isRenderFile() {
        return renderFile;
    }

    public void setRenderFile(boolean renderFile) {
        this.renderFile = renderFile;
    }

    public Integer getGetUnApproved() {
        return getUnApproved;
    }

    public void setGetUnApproved(Integer getUnApproved) {
        this.getUnApproved = getUnApproved;
    }

    public List<ClinicEntity> getUnapprovedList() {
        return unapprovedList;
    }

    public void setUnapprovedList(List<ClinicEntity> unapprovedList) {
        this.unapprovedList = unapprovedList;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public ClinicEntity getSelectedClinicEntityToView() {
        return selectedClinicEntityToView;
    }

    public void setSelectedClinicEntityToView(ClinicEntity selectedClinicEntityToView) {
        this.selectedClinicEntityToView = selectedClinicEntityToView;
    }

}
