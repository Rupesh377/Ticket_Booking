package com.rupesh.TIcket_Booking.Event.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateTicketTypeRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer totalSeats;
}
