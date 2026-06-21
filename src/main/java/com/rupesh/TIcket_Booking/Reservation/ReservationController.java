package com.rupesh.TIcket_Booking.Reservation;

import com.rupesh.TIcket_Booking.Exception.ResourceNotFoundException;
import com.rupesh.TIcket_Booking.Reservation.DTO.ReservationRequestDTO;
import com.rupesh.TIcket_Booking.Security.SecurityUtil;
import com.rupesh.TIcket_Booking.User.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final SecurityUtil securityUtil;

    @PostMapping
    public ResponseEntity<String> reserveTicket(@Valid @RequestBody ReservationRequestDTO request) {

        reservationService.reserveTicket(request);

        return ResponseEntity.ok("Ticket reserved for 10 minutes");
    }

    @GetMapping("/{ticketTypeId}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long ticketTypeId) {

        User user=securityUtil.getCurrentUser();
        return ResponseEntity.ok(reservationService.getMyReservation(user.getId(), ticketTypeId));
    }

    @DeleteMapping("/{ticketTypeId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long ticketTypeId) {

        User user=securityUtil.getCurrentUser();
        reservationService.deleteReservation(user.getId(), ticketTypeId);

        return ResponseEntity.ok("Reservation cancelled");
        }
}
