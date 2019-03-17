package ejb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity

public class StaffEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lastName;
    @Column(nullable = false, length = 6)
    @NotNull
    @Size(max = 6)
    private String title;

    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String status;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private ClinicEntity clinicEntity;
    
    @OneToMany(mappedBy = "staffEntity")
    private List<BookingEntity> bookingEntities;
    
    @OneToMany(mappedBy = "staffEntity")
    private List<MessageOfTheDayEntity> messageOfTheDayEntities;

     @OneToOne(optional=true)
    private MessageOfTheDayEntity messageOfTheDayEntity;
    
    public StaffEntity() {
        
       
        super();
        messageOfTheDayEntity= new MessageOfTheDayEntity();
         bookingEntities=new ArrayList<>();
         messageOfTheDayEntities=new ArrayList<>();

    }

    public StaffEntity(String email, String password, String firstName, String lastName, String title, String status,ClinicEntity clinicEntity) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.status = status;
        this.clinicEntity=clinicEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.userId != null ? this.userId.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StaffEntity)) {
            return false;
        }

        StaffEntity other = (StaffEntity) object;

        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.StaffEntity[ Id=" + this.userId + " ]";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ClinicEntity getClinicEntity() {
        return clinicEntity;
    }

    public void setClinicEntity(ClinicEntity clinicEntity) {
        this.clinicEntity = clinicEntity;
    }

    public List<BookingEntity> getBookingEntities() {
        return bookingEntities;
    }

    public void setBookingEntities(List<BookingEntity> bookingEntities) {
        this.bookingEntities = bookingEntities;
    }

    public List<MessageOfTheDayEntity> getMessageOfTheDayEntities() {
        return messageOfTheDayEntities;
    }

    public void setMessageOfTheDayEntities(List<MessageOfTheDayEntity> messageOfTheDayEntities) {
        this.messageOfTheDayEntities = messageOfTheDayEntities;
    }

    public MessageOfTheDayEntity getMessageOfTheDayEntity() {
        return messageOfTheDayEntity;
    }

    public void setMessageOfTheDayEntity(MessageOfTheDayEntity messageOfTheDayEntity) {
        this.messageOfTheDayEntity = messageOfTheDayEntity;
    }

}
