package com.rupesh.TIcket_Booking.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO {

    private LocalDateTime timestamp;

    private Integer status;

    private String message;
}
