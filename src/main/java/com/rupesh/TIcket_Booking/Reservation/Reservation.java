package com.rupesh.TIcket_Booking.Reservation;

import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@Data
public class Reservation {


    private Long userId;

    private Long ticketTypeId;

    private Integer quantity;
}

