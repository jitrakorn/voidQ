/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.BookingStatus;
import util.exception.BookingNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author jitra
 */
@Stateless
@Local(BookingSessionBeanLocal.class)
public class BookingSessionBean implements BookingSessionBeanLocal {

    @PersistenceContext(unitName = "voidQ-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public BookingSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public BookingEntity makeBooking(BookingEntity newBooking) throws InputDataValidationException {
        Set<ConstraintViolation<BookingEntity>> constraintViolations = validator.validate(newBooking);
        
        if(constraintViolations.isEmpty()) {
            em.persist(newBooking);
            em.flush();
            
            return newBooking;
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    public void cancelBooking(BookingEntity booking) {
        booking.setStatus(BookingStatus.CANCELLED);
    }

    public void checkIn(BookingEntity booking) {
        booking.setStatus(BookingStatus.CHECKEDIN);
    }

    public void makePayment(BookingEntity booking) {
        booking.setStatus(BookingStatus.PAID);
    }

    public List<BookingEntity> getBookings(ClinicEntity clinic) {
        Query query = em.createQuery("SELECT b FROM BookingEntity b WHERE b.clinicEntity = :inClinic");
        query.setParameter("inClinic", clinic);
        return query.getResultList();
    }

    public BookingEntity retrieveBookingByBookingId(Long bookingId) throws BookingNotFoundException {
        BookingEntity booking = em.find(BookingEntity.class, bookingId);

        if (booking != null) {
            return booking;
        } else {
            throw new BookingNotFoundException("Booking ID " + bookingId + " does not exist!");
        }
    }

    public void updateBookingStatus(BookingEntity booking, BookingStatus bookingStatus) {
        booking.setStatus(bookingStatus);
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<BookingEntity>> constraintViolations) {
        String msg = "Input data validation error!:";
        
        for(ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }

}
