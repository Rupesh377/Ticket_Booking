package com.rupesh.TIcket_Booking.Event;

import com.rupesh.TIcket_Booking.Event.DTO.CreateEventRequestDTO;
import com.rupesh.TIcket_Booking.Event.DTO.CreateTicketTypeRequestDTO;
import com.rupesh.TIcket_Booking.Event.DTO.EventResponseDTO;
import com.rupesh.TIcket_Booking.Exception.AccessDeniedException;
import com.rupesh.TIcket_Booking.Exception.ResourceNotFoundException;
import com.rupesh.TIcket_Booking.Security.SecurityUtil;
import com.rupesh.TIcket_Booking.User.Role;
import com.rupesh.TIcket_Booking.User.User;
import com.rupesh.TIcket_Booking.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Long createEvent(CreateEventRequestDTO request) {

        String email = SecurityUtil.getCurrentUserEmail();

        User organizer = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (organizer.getRole() != Role.ORGANIZER) {
            throw new AccessDeniedException("Only organizers can create events");
        }

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .venue(request.getVenue())
                .city(request.getCity())
                .eventDate(request.getEventDate())
                .published(false)
                .organizer(organizer)
                .build();

        for(CreateTicketTypeRequestDTO dto : request.getTicketTypes()) {

            TicketType ticketType = TicketType.builder()
                    .name(dto.getName())
                    .price(dto.getPrice())
                    .totalSeats(dto.getTotalSeats())
                    .availableSeats(dto.getTotalSeats())
                    .event(event)
                    .build();

            event.getTicketTypes().add(ticketType);
        }
        return eventRepository.save(event).getId();
    }


    public List<EventResponseDTO> getMyEvents() {

        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return eventRepository.findByOrganizer(user).stream().map(this::mapToDTO).toList();
    }



    public EventResponseDTO getEvent(Long eventId) {

        Event event = eventRepository.findById(eventId)
                        .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return mapToDTO(event);
    }


    public List<EventResponseDTO> getPublishedEvents() {
        return eventRepository.findByPublishedTrue().stream().map(this::mapToDTO).toList();
    }


    public void publishEvent(Long eventId) {

        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event not found"));

        if (!event.getOrganizer().getId().equals(user.getId())) {
            throw new AccessDeniedException("Not event owner");
        }

        event.setPublished(true);
        eventRepository.save(event);
    }

    private EventResponseDTO mapToDTO(Event event) {

        return EventResponseDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .venue(event.getVenue())
                .city(event.getCity())
                .eventDate(event.getEventDate())
                .published(event.isPublished())
                .organizerEmail(event.getOrganizer().getEmail())
                .build();
    }
}
