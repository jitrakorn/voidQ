/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.AdminEntity;
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

    public AdminEntity createNewAdmin(AdminEntity newAdmin) throws InputDataValidationException;

    public AdminEntity retrieveAdminByUsername(String username) throws AdministratorNotFoundException;

    public List<AdminEntity> retrieveAllAdministrators();

    public AdminEntity retrieveAdminByAdminId(Long adminId) throws AdministratorNotFoundException;

    public void updateAdmin(AdminEntity admin) throws InputDataValidationException, AdministratorNotFoundException, UpdateAdminException;

    public AdminEntity userNameLogin(String email, String password) throws InvalidLoginCredentialException;

    public void deleteAdmin(Long adminId) throws AdministratorNotFoundException, DeleteAdminException;

  

    public AdminEntity resetPassword(Long adminId);
    
}
