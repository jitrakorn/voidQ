/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.AdminEntity;
import ejb.entity.BookingEntity;

import java.util.concurrent.Future;
import javax.ejb.Local;

/**
 *
 * @author mingxuan
 */
@Local
public interface EmailControllerLocal {

    

    public Future<Boolean> emailResetPassword(AdminEntity admin, String newPW);

    public Future<Boolean> emailClinic(BookingEntity bookingEntity, Long clinicId);
    
}
