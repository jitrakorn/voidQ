/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.BookingEntity;
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
@Local(BookingSessionBeanLocal.class)
public class BookingSessionBean implements BookingSessionBeanLocal {

    @PersistenceContext(unitName = "voidQ-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public BookingEntity createBooking(BookingEntity newBooking) {
        em.persist(newBooking);
        em.flush();
        return newBooking;
    }
    
    @Override
    public List<BookingEntity> getBookingsByClinicId(Long clinicId) {
        return em.createQuery("SELECT b FROM BookingEntity b WHERE b.clinicEntity.clinicId = :clinicId")
                .setParameter("clinicId", clinicId)
                .getResultList();     
    }
    
    @Override
    public Integer getCurrentQueue(Long clinicId) {
        return em.createQuery("SELECT b FROM BookingEntity b WHERE b.clinicEntity.clinicId = :clinicId")
                .setParameter("clinicId", clinicId)
                .getResultList().size(); 
    }
}
