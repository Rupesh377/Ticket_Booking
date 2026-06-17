package com.rupesh.TIcket_Booking.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.Login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> login(@Valid @RequestBody RegisterDTO request) {
        return ResponseEntity.ok(userService.Register(request));
    }
}
