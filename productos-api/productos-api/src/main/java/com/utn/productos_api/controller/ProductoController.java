package com.utn.productos_api.controller;

import com.utn.productos_api.dto.ActualizarStockDTO;
import com.utn.productos_api.dto.ProductoDTO;
import com.utn.productos_api.dto.ProductoResponseDTO;
import com.utn.productos_api.model.Categoria;
import com.utn.productos_api.service.ProductoService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name="Prodcutos", description = "Endpoints para la gestion de productos")
public class ProductoController {

    private final ProductoService productoService;

    @Operation(summary = "Listar todos los productos", description = "Obtiene una lista completa de todos los productos disponibles en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        List<ProductoResponseDTO> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    /**
     * GET /api/productos/{id}
     * Obtiene un producto específico por su ID.
     *
     * @param id Identificador del producto (capturado de la URL)
     * @return Producto encontrado con código 200 OK, o 404 NOT FOUND si no existe
     */
    @Operation(summary = "Obtener producto por ID", description = "Busca y retorna un producto específico utilizando su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(
            @Parameter(description = "ID único del producto", required = true, example = "1")
            @PathVariable Long id) {
        ProductoResponseDTO producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    /**
     * GET /api/productos/categoria/{categoria}
     * Filtra productos por categoría.
     *
     * @param categoria Categoría a filtrar (ELECTRONICA, ROPA, etc.)
     * @return Lista de productos de la categoría con código 200 OK
     */
    @Operation(summary = "Filtrar productos por categoría", description = "Obtiene todos los productos que pertenecen a una categoría específica")
    @ApiResponse(responseCode = "200", description = "Lista de productos filtrada exitosamente")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> filtrarPorCategoria(
            @Parameter(description = "Categoría del producto (ELECTRONICA, ROPA, ALIMENTOS, HOGAR, DEPORTES)", required = true)
            @PathVariable Categoria categoria) {
        List<ProductoResponseDTO> productos = productoService.obtenerPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }

    /**
     * POST /api/productos
     * Crea un nuevo producto.
     *
     * @param productoDTO Datos del producto a crear (validados con @Valid)
     * @return Producto creado con código 201 CREATED
     */
    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto en el sistema con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error de validación")
    })
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del nuevo producto", required = true)
            @Valid @RequestBody ProductoDTO productoDTO) {
        ProductoResponseDTO productoCreado = productoService.crearProducto(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    /**
     * PUT /api/productos/{id}
     * Actualiza completamente un producto existente.
     * Todos los campos del producto son reemplazados.
     *
     * @param id ID del producto a actualizar
     * @param productoDTO Nuevos datos del producto (validados)
     * @return Producto actualizado con código 200 OK, o 404 si no existe
     */
    @Operation(summary = "Actualizar producto completo", description = "Actualiza todos los campos de un producto existente (operación PUT)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @Parameter(description = "ID del producto a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del producto", required = true)
            @Valid @RequestBody ProductoDTO productoDTO) {
        ProductoResponseDTO productoActualizado = productoService.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    /**
     * PATCH /api/productos/{id}/stock
     * Actualiza parcialmente un producto (solo el stock).
     * PATCH se usa cuando solo actualizamos campos específicos.
     *
     * @param id ID del producto
     * @param stockDTO Nuevo valor de stock (validado)
     * @return Producto con stock actualizado con código 200 OK
     */
    @Operation(summary = "Actualizar stock del producto", description = "Actualiza únicamente el stock de un producto (operación PATCH)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Valor de stock inválido")
    })
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDTO> actualizarStock(
            @Parameter(description = "ID del producto", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevo valor de stock", required = true)
            @Valid @RequestBody ActualizarStockDTO stockDTO) {
        ProductoResponseDTO productoActualizado =
                productoService.actualizarStock(id, stockDTO.getStock());
        return ResponseEntity.ok(productoActualizado);
    }

    /**
     * DELETE /api/productos/{id}
     * Elimina un producto del sistema.
     *
     * NOTA: La validación de stock > 0 se maneja en el servicio como regla de negocio.
     *
     * @param id ID del producto a eliminar
     * @return Código 204 NO CONTENT si se eliminó correctamente,
     *         404 si no existe, o 409 si tiene stock
     */
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema (solo si tiene stock = 0)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "409", description = "No se puede eliminar: el producto tiene stock disponible")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del producto a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

}


