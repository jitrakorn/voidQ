package datamodel.ws.rest;



public class CreatePatientRsp
{
    private Long userId;

    
    
    public CreatePatientRsp()
    {
    }

    public CreatePatientRsp(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    
  
}