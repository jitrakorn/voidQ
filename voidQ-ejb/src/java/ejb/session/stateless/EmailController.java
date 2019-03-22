/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.AdminEntity;
import ejb.entity.BookingEntity;
import ejb.entity.StaffEntity;
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import util.email.EmailManager;
import util.exception.StaffEntityNotFoundException;

/**
 *
 * @author mingxuan
 */
@Stateless
@Local(EmailControllerLocal.class)
public class EmailController implements EmailControllerLocal {

    @EJB(name = "ClinicSessionBeanLocal")
    private ClinicSessionBeanLocal clinicSessionBeanLocal;
    private final String UNIX_USERNAME = "ohmx93@comp.nus.edu.sg";
    private final String UNIX_PASSWORD = "CIJGAHkoi987";

    @Asynchronous
    @Override
    public Future<Boolean> emailResetPassword(AdminEntity admin, String newPW) {
        EmailManager emailManager = new EmailManager(UNIX_USERNAME, UNIX_PASSWORD);
        Boolean result = emailManager.emailResetPassword(admin, "ohmx93@comp.nus.edu.sg", newPW);

        return new AsyncResult<>(result);
    }

    @Asynchronous
    @Override
    public Future<Boolean> emailClinic(BookingEntity bookingEntity, Long clinicId) {
        EmailManager emailManager = new EmailManager(UNIX_USERNAME, UNIX_PASSWORD);
        Boolean result = null;

        try {
            List<StaffEntity> staffEntity = clinicSessionBeanLocal.retrieveStaffByClinicId(clinicId);
             result = emailManager.emailClinic(bookingEntity, staffEntity);

        } catch (StaffEntityNotFoundException ex) {

        }

        return new AsyncResult<>(result);
    }

}
