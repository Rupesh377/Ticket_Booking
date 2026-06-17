package com.rupesh.TIcket_Booking.RefreshToken;

import com.rupesh.TIcket_Booking.User.User;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.spi.Tokens;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
