package com.rupesh.TIcket_Booking.Booking.DTO;

import lombok.Data;

@Data
public class BookingRequestDTO {
    private Long ticketTypeId;

    private Integer quantity;
}
