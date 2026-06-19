package com.rupesh.TIcket_Booking.User;

import com.rupesh.TIcket_Booking.Exception.BadRequestException;
import com.rupesh.TIcket_Booking.Exception.ResourceNotFoundException;
import com.rupesh.TIcket_Booking.RefreshToken.RefreshToken;
import com.rupesh.TIcket_Booking.RefreshToken.RefreshTokenDTO;
import com.rupesh.TIcket_Booking.RefreshToken.RefreshTokenResponseDTO;
import com.rupesh.TIcket_Booking.RefreshToken.RefreshTokenService;
import com.rupesh.TIcket_Booking.Security.JwtService;
import com.rupesh.TIcket_Booking.User.DTO.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public LoginResponseDTO Login(@Valid LoginRequestDTO request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (request.getEmail(), request.getPassword()));

        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("User not found with this email"));

//        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new BadRequestException("Invalid password");
//        }

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
            String jwt= jwtService.generateToken(user);
            return new LoginResponseDTO(jwt , refreshToken.getToken(), user.getRole());

    }


    public RegisterResponseDTO Register(@Valid RegisterRequestDTO request) {

        if(userRepository.existsByEmail(request.getEmail()))
                throw new BadRequestException("User already present with this gmail: "+request.getEmail());

        UserDTO user= UserDTO.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User user1=modelMapper.map(user , User.class);

        userRepository.save(user1);
        return new RegisterResponseDTO(user1.getName(), user1.getEmail());
    }

    public RefreshTokenResponseDTO refreshToken(RefreshTokenDTO request) {

        RefreshToken refreshToken = refreshTokenService.verifyToken(request.getRefreshToken());
        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(user);

        return new RefreshTokenResponseDTO(accessToken);
    }

    public void logout(RefreshTokenDTO request) {

        refreshTokenService.revokeToken(request.getRefreshToken());
    }
}
