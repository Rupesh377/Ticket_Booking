package com.rupesh.TIcket_Booking.User.DTO;

import com.rupesh.TIcket_Booking.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private String password;

    private Role role;
}
