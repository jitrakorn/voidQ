package ejb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class StaffEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lastName;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private ClinicEntity clinicEntity;

    public StaffEntity() {
        super();
    }

    public StaffEntity(String email, String password, String firstName, String lastName, ClinicEntity clinicEntity) {
        super(email, password);

        this.firstName = firstName;
        this.lastName = lastName;
        this.clinicEntity = clinicEntity;
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
    
    public ClinicEntity getClinicEntity() {
        return clinicEntity;
    }

    public void setClinicEntity(ClinicEntity clinicEntity) {
        this.clinicEntity = clinicEntity;
    }
}
