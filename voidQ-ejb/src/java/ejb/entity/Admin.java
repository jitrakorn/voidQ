package ejb.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.security.CryptographicHelper;



@Entity

public class Admin implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lastName;    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(min = 4, max = 32)
    private String username;
    // Updated in v4.4 to use CHAR instead of VARCHAR
    //@Column(nullable = false, length = 32)
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    // The following bean validation constraint is not applicable since we are only storing the password hashsum which is always 128 bit represented as 32 characters (16 hexadecimal digits)
    //@Size(min = 8, max = 32)
    private String password;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    private String salt;
  
    
    
    public Admin()
    {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
        
       
    }

    
    
    public Admin(String firstName, String lastName, String username, String password) 
    {
        this();
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        
        setPassword(password);
    }
    
    
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (this.adminId != null ? this.adminId.hashCode() : 0);
        
        return hash;
    }

    
    
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Admin)) 
        {
            return false;
        }
        
        Admin other = (Admin) object;
        
        if ((this.adminId == null && other.adminId != null) || (this.adminId != null && !this.adminId.equals(other.adminId))) 
        {
            return false;
        }
        
        return true;
    }

    
    
    @Override
    public String toString() 
    {
        return "ejb.entity.Admin[ adminId=" + this.adminId + " ]";
    }

    
    
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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

   

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password)
    {
        if(password != null)
        {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        }
        else
        {
            this.password = null;
        }
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
}