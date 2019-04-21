/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.DoctorEntity;
import ejb.entity.NurseEntity;
import ejb.entity.StaffEntity;

import java.util.List;
import javax.ejb.Local;
import util.exception.ClinicNotActivatedException;
import util.exception.DeletePartnerException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;
import util.exception.UpdatePartnerException;
import util.exception.UpdatePasswordException;

/**
 *
 * @author mingxuan
 */
@Local
public interface PartnerSessionBeanLocal {

    public ClinicEntity createNewPartner(ClinicEntity newClinic) throws InputDataValidationException;

    public StaffEntity retrievePartnerByEmail(String email) throws PartnerNotFoundException;

    public StaffEntity emailLogin(String email, String password) throws InvalidLoginCredentialException, ClinicNotActivatedException;

    public List<ClinicEntity> retrieveAllPartners();

    public ClinicEntity retrievePartnerByPartnerId(Long clinicId) throws PartnerNotFoundException;

    public ClinicEntity getPartnerById(Long clinicId);

    public void updatePartner(ClinicEntity clinic) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException;

    public void deletePartner(Long clinicId) throws PartnerNotFoundException, DeletePartnerException;

    public StaffEntity createNewStaff(StaffEntity newStaff);

    public StaffEntity retrieveStaffByStaffId(Long staffId) throws PartnerNotFoundException;

    public void updateStaff(StaffEntity staff) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException;

   public List<DoctorEntity> getDoctorsByClinicId(Long clinicId);
   
   public List<NurseEntity> getNursesByClinicId(Long clinicId);

    public List<ClinicEntity> retrieveUnApprovedApplications();

    public Boolean hasAvailableDoctors(ClinicEntity currentClinic);

    public DoctorEntity appointAvailableDoctor(ClinicEntity currentClinic, BookingEntity booking);

    public DoctorEntity availDoctor(DoctorEntity appointedDoctor);

    public DoctorEntity retrieveDoctorByStaffId(Long staffId) throws PartnerNotFoundException;

    public NurseEntity retrieveNurseByStaffId(Long staffId) throws PartnerNotFoundException;

    public void updateNursePassword(NurseEntity staff, String oldPassword, String newPassword) throws UpdatePasswordException, PartnerNotFoundException;

    public void updateDoctorPassword(DoctorEntity staff, String oldPassword, String newPassword) throws UpdatePasswordException, PartnerNotFoundException;

    public void deleteStaff(Long partnerId) throws PartnerNotFoundException, DeletePartnerException;

    public List<DoctorEntity> retrieveAllDoctors();

    public List<NurseEntity> retrieveAllNurses();

    public void updateNurse(NurseEntity staff) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException;

    public void updateDoctor(DoctorEntity staff) throws InputDataValidationException, PartnerNotFoundException, UpdatePartnerException;

    public void deleteNurse(Long partnerId) throws PartnerNotFoundException, DeletePartnerException;

    public void deleteDoctor(Long partnerId) throws PartnerNotFoundException, DeletePartnerException;

    public DoctorEntity createNewDoctor(DoctorEntity newStaff);

    public NurseEntity createNewNurse(NurseEntity newStaff);
}
