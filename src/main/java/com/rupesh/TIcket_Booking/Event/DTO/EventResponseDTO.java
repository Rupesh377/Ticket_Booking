package com.rupesh.TIcket_Booking.Event.DTO;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventResponseDTO {

    private Long id;

    private String title;

    private String description;

    private String venue;

    private String city;

    private LocalDateTime eventDate;

    private boolean published;

    private String organizerEmail;
}