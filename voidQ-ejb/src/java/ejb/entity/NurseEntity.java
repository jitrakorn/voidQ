/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author terencetay
 */
@Entity
public class NurseEntity extends StaffEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "nurseEntity")
    private List<BookingEntity> bookingEntities;

    public NurseEntity() {
        super();
    }

    public NurseEntity(String email, String password, String firstName, String lastName, ClinicEntity clinicEntity) {
        super(email, password, firstName, lastName, clinicEntity);
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
        if (!(object instanceof NurseEntity)) {
            return false;
        }
        NurseEntity other = (NurseEntity) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.NurseEntity[ id=" + userId + " ]";
    }

    public List<BookingEntity> getBookingEntities() {
        return bookingEntities;
    }

    public void setBookingEntities(List<BookingEntity> bookingEntities) {
        this.bookingEntities = bookingEntities;
    }
}
