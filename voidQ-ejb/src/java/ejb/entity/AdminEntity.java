package ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity

public class AdminEntity extends UserEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lastName;    
  
    
    public AdminEntity()
    {
       
       super(); 
      
    }

    public AdminEntity(String firstName, String lastName, String email, String password) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    
    
  
    
    
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (this.userId != null ? this.userId.hashCode() : 0);
        
        return hash;
    }

    
    
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof AdminEntity)) 
        {
            return false;
        }
        
        AdminEntity other = (AdminEntity) object;
        
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) 
        {
            return false;
        }
        
        return true;
    }

    
    
    @Override
    public String toString() 
    {
        return "ejb.entity.AdminEntity[ adminId=" + this.userId + " ]";
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



 

    
    
   
}