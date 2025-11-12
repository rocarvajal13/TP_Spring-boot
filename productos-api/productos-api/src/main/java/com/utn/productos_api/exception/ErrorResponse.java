package com.utn.productos_api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int codigodeestado;
    private String mensajeerror;
    private String rutadepeticion;
    public ErrorResponse(HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.codigodeestado = status.value();
        this.mensajeerror = mensajeerror;
        this.rutadepeticion = rutadepeticion;
    }
}
