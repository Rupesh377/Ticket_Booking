package com.rupesh.TIcket_Booking.Booking;

import com.rupesh.TIcket_Booking.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository  extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);
}
