/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.TransactionEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author terencetay
 */
@Stateless
@Local(TransactionSessionBeanLocal.class)
public class TransactionSessionBean implements TransactionSessionBeanLocal {

    @PersistenceContext(unitName = "voidQ-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public List<TransactionEntity> getAllTransactionsByClinicId(Long clinicId) {
        ClinicEntity clinic = (ClinicEntity) em.find(ClinicEntity.class, clinicId);
        List<BookingEntity> bookings = clinic.getBookingEntities();
        List<TransactionEntity> transactions = new ArrayList<>();
        for (BookingEntity booking: bookings) {
            transactions.add(booking.getTransactionEntity());
        }
        
        return transactions;
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
