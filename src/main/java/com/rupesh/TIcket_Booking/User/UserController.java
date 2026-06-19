package com.rupesh.TIcket_Booking.User;

import com.rupesh.TIcket_Booking.RefreshToken.RefreshTokenDTO;
import com.rupesh.TIcket_Booking.RefreshToken.RefreshTokenResponseDTO;
import com.rupesh.TIcket_Booking.RefreshToken.RefreshTokenService;
import com.rupesh.TIcket_Booking.Security.SecurityUtil;
import com.rupesh.TIcket_Booking.User.DTO.LoginRequestDTO;
import com.rupesh.TIcket_Booking.User.DTO.LoginResponseDTO;
import com.rupesh.TIcket_Booking.User.DTO.RegisterRequestDTO;
import com.rupesh.TIcket_Booking.User.DTO.RegisterResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.Login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> login(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(userService.Register(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestBody RefreshTokenDTO request) {

        return ResponseEntity.ok(userService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenDTO request) {

        userService.logout(request);
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/me")
    public String me() {
        return SecurityUtil.getCurrentUserEmail();
    }
}
