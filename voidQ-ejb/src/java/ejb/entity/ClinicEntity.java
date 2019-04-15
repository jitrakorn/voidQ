package ejb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.ApplicationStatus;

@Entity

public class ClinicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clinicId;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String clinicName;
    @Column(nullable = false, length = 128)
    @Size(max = 128)
    private String description;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String address;
    @Column(nullable = true, length=10)
    @Size(max = 10)
    private String phoneNum;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal unitPrice;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private ApplicationStatus applicationStatus;
    @Column(nullable = false, length = 128)
    @Size(max = 128)
    private String latlng;
    
    @OneToMany(mappedBy = "clinicEntity")
    private List<DoctorEntity> doctorEntities;
    
    @OneToMany(mappedBy = "clinicEntity")
    private List<NurseEntity> nurseEntities;
    
    @OneToMany(mappedBy = "clinicEntity")
    private List<BookingEntity> bookingEntities;

//    @OneToMany
//    private List<PatientEntity> patientEntities;

    public ClinicEntity() {
        bookingEntities = new ArrayList<>();
        doctorEntities = new ArrayList<>();
        nurseEntities = new ArrayList<>();
       // patientEntities = new ArrayList<>();
    }

    // Just use this everytime
    public ClinicEntity(String clinicName, String description, String address, String phoneNum, BigDecimal unitPrice,String latlng, ApplicationStatus applicationStatus) {
        this.clinicName = clinicName;
        this.description = description;
        this.address = address;
        this.phoneNum= phoneNum;
        this.unitPrice = unitPrice;
        this.latlng=latlng;
        this.applicationStatus = applicationStatus;
    }
    
 

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.clinicId != null ? this.clinicId.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ClinicEntity)) {
            return false;
        }

        ClinicEntity other = (ClinicEntity) object;

        if ((this.clinicId == null && other.clinicId != null) || (this.clinicId != null && !this.clinicId.equals(other.clinicId))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.ClinicEntity[ clinicId=" + this.clinicId + " ]";
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<DoctorEntity> getDoctorEntities() {
        return doctorEntities;
    }

    public void setDoctorEntities(List<DoctorEntity> doctorEntities) {
        this.doctorEntities = doctorEntities;
    }

    public List<NurseEntity> getNurseEntities() {
        return nurseEntities;
    }

    public void setNurseEntities(List<NurseEntity> nurseEntities) {
        this.nurseEntities = nurseEntities;
    }

    public List<BookingEntity> getBookingEntities() {
        return bookingEntities;
    }

    public void setBookingEntities(List<BookingEntity> bookingEntities) {
        this.bookingEntities = bookingEntities;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

//    public List<PatientEntity> getPatientEntities() {
//        return patientEntities;
//    }
//
//    public void setPatientEntities(List<PatientEntity> patientEntities) {
//        this.patientEntities = patientEntities;
//    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }
}
