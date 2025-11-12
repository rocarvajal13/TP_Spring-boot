package com.utn.productos_api.dto;
import com.utn.productos_api.model.Categoria;
import com.utn.productos_api.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private Long id;

    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private Categoria categoria;

    public static ProductoResponseDTO fromEntity(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria()
        );
    }

}
