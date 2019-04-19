/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.TransactionEntity;
import java.util.List;

/**
 *
 * @author terencetay
 */
public interface BookingSessionBeanLocal {

    public BookingEntity createBooking(BookingEntity newBooking);

    public List<BookingEntity> getBookingsByClinicId(Long clinicId);

    public Integer getCurrentQueue(Long clinicId);
    
    public BookingEntity updateBooking(BookingEntity bookingEntity);
    
    public List<BookingEntity> getClinicCurrentDayBookings(ClinicEntity selectedClinic);

    public BookingEntity getBookingById(Long bookingId);

    public TransactionEntity createTransaction(TransactionEntity newTransaction);

}
