package com.rupesh.TIcket_Booking.RefreshToken;

import com.rupesh.TIcket_Booking.Exception.BadRequestException;
import com.rupesh.TIcket_Booking.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh-token-expiration}")
    private long refreshTokenDurationMs;


    public RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now()
                        .plusSeconds(refreshTokenDurationMs / 1000))
                .revoked(false)
                .user(user)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }


    public RefreshToken verifyToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token).orElseThrow(() ->
                        new BadRequestException("Invalid refresh token"));

        if (refreshToken.isRevoked()) {
            throw new BadRequestException("Refresh token revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Refresh token expired");
        }

        return refreshToken;
    }


    public void revokeToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token).orElseThrow(() ->
                        new BadRequestException("Invalid refresh token"));

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }
}
