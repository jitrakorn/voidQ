package ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity

public class PatientEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lastName;
    @Column(nullable = false, length = 8)
    @NotNull
    @Size(max = 8)
    private int phoneNumber;

    public PatientEntity() {
        super();

    }

    public PatientEntity(String username, String password, String firstName, String lastName, int phoneNumber) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber=phoneNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.userId != null ? this.userId.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PatientEntity)) {
            return false;
        }

        PatientEntity other = (PatientEntity) object;

        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.PatientEntity[ Id=" + this.userId + " ]";
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

  
    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
