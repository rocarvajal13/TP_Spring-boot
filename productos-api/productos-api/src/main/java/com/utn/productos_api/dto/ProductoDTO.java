package com.utn.productos_api.dto;
import com.utn.productos_api.model.Categoria;
import com.utn.productos_api.model.Producto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la creación o actualización completa de un producto.")
public class ProductoDTO {
    @Schema(description = "Nombre único del producto.")
    @NotBlank(message = "El nombre no puede estar vacio ni nulo")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Schema(description = "Descripción detallada del producto (opcional).")
    @Size(max = 500, message = "Limitado a 500 caracteres")
    private String descripcion;

    @Schema(description = "Precio unitario del producto. Debe ser positivo.")
    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio minimo de cada producto es de $0.01")
    private double precio;

    @Schema(description = "Cantidad disponible en stock. No puede ser negativo.")
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "No puede haber stock negativo")
    private int stock;

    @Schema(description = "Categoría a la que pertenece el producto.")
    @NotNull(message = "La categoria del producto no puede ser nula")
    private Categoria categoria;

    public Producto toEntity() {
        Producto producto = new Producto();
        producto.setNombre(this.nombre);
        producto.setDescripcion(this.descripcion);
        producto.setPrecio(this.precio);
        producto.setStock(this.stock);
        producto.setCategoria(this.categoria);
        return producto;
    }

    public void updateEntity(Producto producto) {
        producto.setNombre(this.nombre);
        producto.setDescripcion(this.descripcion);
        producto.setPrecio(this.precio);
        producto.setStock(this.stock);
        producto.setCategoria(this.categoria);
    }

}
