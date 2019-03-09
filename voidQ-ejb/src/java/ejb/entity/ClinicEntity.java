package ejb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;




@Entity

public class ClinicEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clinicId;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String clinicName;
    @Column(nullable = false, length = 32)
    @Size(max = 32)
    private String description;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String address;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal unitPrice;
    
    @OneToMany(mappedBy = "clinicEntity")
    private List<StaffEntity> staffEntities;
    
    @OneToMany(mappedBy = "clinicEntity")
    private List<BookingEntity> bookingEntities;
    

    public ClinicEntity() {
        bookingEntities= new ArrayList<>();
         staffEntities = new ArrayList<>();
    }

    
    
    
    public ClinicEntity(String clinicName, String description, String address, BigDecimal unitPrice) {
        this.clinicName = clinicName;
        this.description = description;
        this.address = address;
        this.unitPrice = unitPrice;
    }
    

    
    
   
    
    
   
    
    
    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (this.clinicId != null ? this.clinicId.hashCode() : 0);
        
        return hash;
    }

    
    
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof ClinicEntity)) 
        {
            return false;
        }
        
        ClinicEntity other = (ClinicEntity) object;
        
        if ((this.clinicId == null && other.clinicId != null) || (this.clinicId != null && !this.clinicId.equals(other.clinicId))) 
        {
            return false;
        }
        
        return true;
    }

    
    
    @Override
    public String toString() 
    {
        return "ejb.entity.ClinicEntity[ clinicId=" + this.clinicId + " ]";
    }

    
    public ClinicEntity(Long clinicId, String clinicName, String description, String address, BigDecimal unitPrice) {
        this.clinicId = clinicId;
        this.clinicName = clinicName;
        this.description = description;
        this.address = address;
        this.unitPrice = unitPrice;
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

    public List<StaffEntity> getStaffEntities() {
        return staffEntities;
    }

    public void setStaffEntities(List<StaffEntity> staffEntities) {
        this.staffEntities = staffEntities;
    }

    public List<BookingEntity> getBookingEntities() {
        return bookingEntities;
    }

    public void setBookingEntities(List<BookingEntity> bookingEntities) {
        this.bookingEntities = bookingEntities;
    }

    
    
   
   

  
}