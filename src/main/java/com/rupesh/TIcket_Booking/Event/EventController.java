package com.rupesh.TIcket_Booking.Event;

import com.rupesh.TIcket_Booking.Event.DTO.CreateEventRequestDTO;
import com.rupesh.TIcket_Booking.Event.DTO.EventResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Long> createEvent(@Valid @RequestBody CreateEventRequestDTO request) {
        return ResponseEntity.ok(eventService.createEvent(request));
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<EventResponseDTO>> getMyEvents() {
        return ResponseEntity.ok(eventService.getMyEvents());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEvent(eventId));
    }

    @GetMapping

    public ResponseEntity<List<EventResponseDTO>> getPublishedEvents() {
        return ResponseEntity.ok(eventService.getPublishedEvents());
    }


    @PostMapping("/{eventId}/publish")
    public ResponseEntity<String> publishEvent(@PathVariable Long eventId) {
        eventService.publishEvent(eventId);
        return ResponseEntity.ok("Published");

    }
}

