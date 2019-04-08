/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.BookingEntity;
import java.util.List;

/**
 *
 * @author terencetay
 */
public interface BookingSessionBeanLocal {

    public BookingEntity createBooking(BookingEntity newBooking);

    public List<BookingEntity> getBookingsByClinicId(Long clinicId);

    public Integer getCurrentQueue(Long clinicId);
    
}
