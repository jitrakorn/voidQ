/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.Administrator;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import util.email.EmailManager;

/**
 *
 * @author mingxuan
 */
@Stateless
public class EmailController implements EmailControllerLocal {
 private final String UNIX_USERNAME = "ohmx93@comp.nus.edu.sg";
    private final String UNIX_PASSWORD = "CIJGAHkoi987";
    
    
    
     @Asynchronous
     @Override
    public Future<Boolean> emailResetPassword(Administrator admin,String newPW)
    {
        EmailManager emailManager = new EmailManager(UNIX_USERNAME, UNIX_PASSWORD);
        Boolean result = emailManager.emailResetPassword(admin, "ohmx93@comp.nus.edu.sg",newPW);
        
         return new AsyncResult<>(result);
    } 
    
    
    
    
}
