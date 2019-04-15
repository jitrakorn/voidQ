/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

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

    @Override
    public List<BookingEntity> getClinicCurrentDayBookings(ClinicEntity selectedClinic) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        List<BookingEntity> clinicCurrentDayBookings = em.createQuery("SELECT b FROM BookingEntity b WHERE b.clinicEntity = :clinic AND b.transactionDateTime > :dateTime")
                .setParameter("clinic", selectedClinic)
                .setParameter("dateTime", today.getTime(), TemporalType.TIMESTAMP)
                .getResultList();

        return clinicCurrentDayBookings;
    }

    @Override
    public BookingEntity updateBooking(BookingEntity bookingEntity) {
        em.merge(bookingEntity);
        em.flush();
        return bookingEntity;
    }
    
    @Override
    public BookingEntity getBookingById(Long bookingId) {
        return em.find(BookingEntity.class, bookingId);   
    }
}
