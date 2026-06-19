package com.rupesh.TIcket_Booking.Event.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateEventRequestDTO {

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String venue;

    @NotBlank
    private String city;

    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    private List<CreateTicketTypeRequestDTO> ticketTypes;
}
