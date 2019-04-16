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
import util.exception.StaffEntityNotFoundException;

/**
 *
 * @author mingxuan
 */
@Local
public interface ClinicSessionBeanLocal {

    public List<StaffEntity> retrieveStaffByClinicId(Long clinicId) throws StaffEntityNotFoundException;

    public List<ClinicEntity> retrieveAllActivatedClinics();
    
    public Integer retrieveCurrentClinicCurrentDayCurrentQueue(Long clinicId);
}
