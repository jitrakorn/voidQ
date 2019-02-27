/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.Partner;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeletePartnerException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;
import util.exception.UpdatePartnerException;

/**
 *
 * @author mingxuan
 */
@Local
public interface PartnerSessionBeanLocal {

 
    public Partner createNewPartner(Partner newPartner) throws InputDataValidationException;

    public Partner retrievePartnerByEmail(String email) throws PartnerNotFoundException;

    public Partner emailLogin(String email, String password) throws InvalidLoginCredentialException;

    public List<Partner> retrieveAllPartners();

    public Partner retrievePartnerByPartnerId(Long partnerId) throws PartnerNotFoundException;

    public void updatePartner(Partner partner) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException;

    public void deletePartner(Long partnerId) throws PartnerNotFoundException, DeletePartnerException;
    
}
