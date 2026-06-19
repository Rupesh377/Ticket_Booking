package com.rupesh.TIcket_Booking.User.DTO;

import com.rupesh.TIcket_Booking.User.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private String jwt;

    private String refreshToken;

    private Role role;
}
