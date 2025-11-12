package com.utn.productos_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarStockDTO {
    @NotNull(message = "La actualizacion de stock no debe ser nula")
    @Min(value = 0)
    private Integer stock;
}
