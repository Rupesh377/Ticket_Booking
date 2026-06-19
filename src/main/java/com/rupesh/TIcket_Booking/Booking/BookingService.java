package com.rupesh.TIcket_Booking.Booking;

import com.rupesh.TIcket_Booking.Booking.DTO.BookingRequestDTO;
import com.rupesh.TIcket_Booking.Event.TicketType;
import com.rupesh.TIcket_Booking.Event.TicketTypeRepository;
import com.rupesh.TIcket_Booking.Exception.BadRequestException;
import com.rupesh.TIcket_Booking.Exception.ResourceNotFoundException;
import com.rupesh.TIcket_Booking.Security.SecurityUtil;
import com.rupesh.TIcket_Booking.User.User;
import com.rupesh.TIcket_Booking.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final UserRepository userRepository;

    public Long createBooking(BookingRequestDTO request) {

        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TicketType ticketType = ticketTypeRepository.findById(request.getTicketTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("Ticket type not found"));

        if (ticketType.getAvailableSeats() < request.getQuantity()) {

            throw new BadRequestException("Not enough seats available");
        }

        ticketType.setAvailableSeats(ticketType.getAvailableSeats() - request.getQuantity());

        Booking booking = Booking.builder()
                .user(user)
                .ticketType(ticketType)
                .quantity(request.getQuantity())
                .status(BookingStatus.PENDING)
                .totalAmount(ticketType.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))

                .createdAt(LocalDateTime.now()).build();
        return bookingRepository.save(booking).getId();
    }
}