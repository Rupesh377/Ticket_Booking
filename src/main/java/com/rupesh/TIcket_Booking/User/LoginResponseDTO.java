package com.rupesh.TIcket_Booking.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private String jwt;

    private String refreshToken;

    private Role role;
}
