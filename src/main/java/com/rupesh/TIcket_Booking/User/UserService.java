package com.rupesh.TIcket_Booking.User;

import com.rupesh.TIcket_Booking.Security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public  LoginResponseDTO Login(@Valid LoginRequestDTO request) {

        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("User not found with this email"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

            String jwt= jwtService.generateToken(request.getEmail());
            return new LoginResponseDTO(jwt , user.getRole());

    }


    public  RegisterResponseDTO Register(@Valid RegisterDTO request) {

        if(userRepository.existsByEmail(request.getEmail()))
                throw new RuntimeException("User already present with this gmail: "+request.getEmail());

        UserDTO user= UserDTO.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User user1=modelMapper.map(user , User.class);

        userRepository.save(user1);
        return new RegisterResponseDTO(user1.getName(), user1.getEmail());
    }
}
