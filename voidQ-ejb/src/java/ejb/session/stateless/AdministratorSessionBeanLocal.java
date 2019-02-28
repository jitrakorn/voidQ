/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.Administrator;
import java.util.List;
import javax.ejb.Local;
import util.exception.AdministratorNotFoundException;
import util.exception.DeleteAdminException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateAdminException;

/**
 *
 * @author mingxuan
 */
@Local
public interface AdministratorSessionBeanLocal {

    public Administrator createNewAdmin(Administrator newAdmin) throws InputDataValidationException;

    public Administrator retrieveAdminByUsername(String username) throws AdministratorNotFoundException;

    public List<Administrator> retrieveAllAdministrators();

    public Administrator retrieveAdminByAdminId(Long adminId) throws AdministratorNotFoundException;

    public void updateAdmin(Administrator admin) throws InputDataValidationException, AdministratorNotFoundException, UpdateAdminException;

    public Administrator userNameLogin(String email, String password) throws InvalidLoginCredentialException;

    public void deleteAdmin(Long adminId) throws AdministratorNotFoundException, DeleteAdminException;

  

    public Administrator resetPassword(Long adminId);
    
}
