/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.Partner;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.DeletePartnerException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;
import util.exception.UpdatePartnerException;
import util.security.CryptographicHelper;

/**
 *
 * @author mingxuan
 */
@Stateless
@Local(PartnerSessionBeanLocal.class)
public class PartnerSessionBean implements PartnerSessionBeanLocal {

    @PersistenceContext(unitName = "voidQ-ejbPU")
    private EntityManager em;

   private final ValidatorFactory validatorFactory;
    private final Validator validator;

 public PartnerSessionBean() 
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
 
 
  @Override
    public Partner createNewPartner(Partner newPartner) throws InputDataValidationException
    {        
        Set<ConstraintViolation<Partner>>constraintViolations = validator.validate(newPartner);
        
        if(constraintViolations.isEmpty())
        {
            em.persist(newPartner);
            em.flush();

            return newPartner;
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
     @Override
    public Partner retrievePartnerByEmail(String email) throws PartnerNotFoundException
    {
        Query query = em.createQuery("SELECT p FROM Partner p WHERE p.email = :inEmail");
        query.setParameter("inEmail", email);
        
        try
        {
            return (Partner)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new PartnerNotFoundException("Partner Email " + email + " does not exist!");
        }
    }
    
     @Override
    public List<Partner> retrieveAllPartners()
    {
        Query query = em.createQuery("SELECT p FROM Partner p");
        
        return query.getResultList();
    }
      @Override
    public Partner retrievePartnerByPartnerId(Long partnerId) throws PartnerNotFoundException
    {
        Partner partner = em.find(Partner.class, partnerId);
        
        if(partner != null)
        {
            return partner;
        }
        else
        {
            throw new PartnerNotFoundException("Partner ID " + partnerId + " does not exist!");
        }
    }
    
     @Override
    public void updatePartner(Partner partner) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException
    {
        // Updated in v4.1 to update selective attributes instead of merging the entire state passed in from the client
        // Also check for existing staff before proceeding with the update
        
        // Updated in v4.2 with bean validation
        
        Set<ConstraintViolation<Partner>>constraintViolations = validator.validate(partner);
        
        if(constraintViolations.isEmpty())
        {        
            if(partner.getPartnerId()!= null)
            {
                Partner partnerToUpdate = retrievePartnerByPartnerId(partner.getPartnerId());
                
                if(partnerToUpdate.getEmail().equals(partner.getEmail()))
                {
                    partnerToUpdate.setClinicAddress(partner.getClinicAddress());
                     partnerToUpdate.setClinicName(partner.getClinicName());
                   partnerToUpdate.setPartnerName(partner.getPartnerName());
                    // Username and password are deliberately NOT updated to demonstrate that client is not allowed to update account credential through this business method
                }
                else
                {
                    throw new UpdatePartnerException("Email of partner record to be updated does not match the existing record");
                }
            }
            else
            {
                throw new PartnerNotFoundException("Partner ID not provided for partner to be updated");
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public Partner emailLogin(String email, String password) throws InvalidLoginCredentialException
    {
        try
        {
            Partner partner = retrievePartnerByEmail(email);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + partner.getSalt()));
            
            if(partner.getPassword().equals(passwordHash))
            {
                //load partner stuff .size
                                
                return partner;
            }
            else
            {
                throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
            }
        }
        catch(PartnerNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
        }
    }
    
     @Override
    public void deletePartner(Long partnerId) throws PartnerNotFoundException, DeletePartnerException
    {
        Partner partner = retrievePartnerByPartnerId(partnerId);
        
        /*if(partner.getSaleTransactionEntities().isEmpty())
        {
            em.remove(partner);
        } 
        else
        {
            // New in v4.1 to prevent deleting staff with existing sale transaction(s)
            throw new DeletePartnerException("Partner ID " + partnerId + " is associated with existing sale transaction(s) and cannot be deleted!");
        }*/
        
        // have to check if partner is associated with something 
        em.remove(partner);
    }
    
    
    
     private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Partner>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
 
}
