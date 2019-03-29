/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.ClinicEntity;
import ejb.entity.StaffEntity;

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

 
    public ClinicEntity createNewPartner(ClinicEntity newClinic) throws InputDataValidationException;

    public StaffEntity retrievePartnerByEmail(String email) throws PartnerNotFoundException;

    public StaffEntity emailLogin(String email, String password) throws InvalidLoginCredentialException;

    public List<ClinicEntity> retrieveAllPartners();

    public ClinicEntity retrievePartnerByPartnerId(Long clinicId) throws PartnerNotFoundException;

    public void updatePartner(ClinicEntity clinic) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException;

    public void deletePartner(Long clinicId) throws PartnerNotFoundException, DeletePartnerException;

    public StaffEntity createNewStaff(StaffEntity newStaff) throws InputDataValidationException;

    
    public StaffEntity retrieveStaffByStaffId(Long staffId) throws PartnerNotFoundException;

    public void updateStaff(StaffEntity staff) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException;

   public List<ClinicEntity> retrieveUnApprovedApplications() ;
    
}
