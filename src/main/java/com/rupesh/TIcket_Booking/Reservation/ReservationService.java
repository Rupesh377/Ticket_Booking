package com.rupesh.TIcket_Booking.Reservation;

import com.rupesh.TIcket_Booking.Event.TicketType;
import com.rupesh.TIcket_Booking.Event.TicketTypeRepository;
import com.rupesh.TIcket_Booking.Exception.BadRequestException;
import com.rupesh.TIcket_Booking.Exception.ResourceNotFoundException;
import com.rupesh.TIcket_Booking.Reservation.DTO.ReservationRequestDTO;
import com.rupesh.TIcket_Booking.Security.SecurityUtil;
import com.rupesh.TIcket_Booking.User.User;
import com.rupesh.TIcket_Booking.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final SecurityUtil securityUtil;

    public void reserveTicket(ReservationRequestDTO request) {

        User user = securityUtil.getCurrentUser();

        TicketType ticketType = ticketTypeRepository.findById(request.getTicketTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("Ticket type not found"));

        String counterKey = "ticket:" + ticketType.getId() + ":reserved";

        Integer reservedSeats = (Integer) redisTemplate.opsForValue().get(counterKey);

        if (reservedSeats == null) {
            reservedSeats = 0;
        }

        int remainingSeats = ticketType.getAvailableSeats() - reservedSeats;

        if (remainingSeats < request.getQuantity()) {
            throw new BadRequestException("Not enough seats available");
        }

        String reservationKey = "reservation:user:" + user.getId() + ":ticket:" + ticketType.getId();

        Reservation reservation = new Reservation(user.getId(), ticketType.getId(), request.getQuantity());

        redisTemplate.opsForValue().set(reservationKey, reservation, Duration.ofMinutes(10));
        redisTemplate.opsForValue().increment(counterKey, request.getQuantity());
    }

    public Reservation getMyReservation(Long userId, Long ticketTypeId) {

        String key = "reservation:user:" + userId + ":ticket:" + ticketTypeId;
        return (Reservation) redisTemplate.opsForValue().get(key);
    }

    public void deleteReservation(Long userId, Long ticketTypeId) {

        String key = "reservation:user:" + userId + ":ticket:" + ticketTypeId;
        redisTemplate.delete(key);
    }
}