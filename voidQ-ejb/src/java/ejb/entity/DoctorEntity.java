/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import util.enumeration.Availability;

/**
 *
 * @author terencetay
 */
@Entity
public class DoctorEntity extends StaffEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private Availability status;
    
    @OneToMany(mappedBy = "doctorEntity")
    private List<BookingEntity> bookingEntities;
    
    @OneToMany(mappedBy = "doctorEntity")
    private List<MessageOfTheDayEntity> messageOfTheDayEntities;
    @OneToOne(mappedBy="lastEditedDoctorEntity")
    private MessageOfTheDayEntity messageOfTheDayEntity;
    
    public DoctorEntity() {
        super();
        messageOfTheDayEntities = new ArrayList<>();
        bookingEntities = new ArrayList<>();
    }
    
    public DoctorEntity(String email, String password, String firstName, String lastName, ClinicEntity clinicEntity, Availability status) {
        super(email, password, firstName, lastName, clinicEntity);
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DoctorEntity)) {
            return false;
        }
        DoctorEntity other = (DoctorEntity) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.DoctorEntity[ id=" + userId + " ]";
    }

    public Availability getStatus() {
        return status;
    }

    public void setStatus(Availability status) {
        this.status = status;
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
