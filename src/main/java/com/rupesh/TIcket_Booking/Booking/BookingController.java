package com.rupesh.TIcket_Booking.Booking;

import com.rupesh.TIcket_Booking.Booking.DTO.BookingRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Long> createBooking(@RequestBody BookingRequestDTO request) {

        return ResponseEntity.ok(bookingService.createBooking(request));
    }
}