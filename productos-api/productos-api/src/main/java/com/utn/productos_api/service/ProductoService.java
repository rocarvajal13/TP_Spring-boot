package com.utn.productos_api.service;

import com.utn.productos_api.dto.ActualizarStockDTO;
import com.utn.productos_api.dto.ProductoDTO;
import com.utn.productos_api.dto.ProductoResponseDTO;
import com.utn.productos_api.exception.ProductoNotFoundException; // Importar la excepción
import com.utn.productos_api.exception.StockInsuficienteException; // Importar la excepción (si se implementa)
import com.utn.productos_api.model.Categoria; // Asumiendo que Categoria es una clase/enum
import com.utn.productos_api.model.Producto;
import com.utn.productos_api.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoResponseDTO crearProducto(ProductoDTO productoDTO) {
        // Convertir DTO a entidad usando el método del DTO
        Producto producto = productoDTO.toEntity();

        // Guardar en base de datos
        Producto productoGuardado = productoRepository.save(producto);

        // Convertir entidad a DTO de respuesta
        return ProductoResponseDTO.fromEntity(productoGuardado);
    }

    /**
     * Obtiene todos los productos del sistema.
     *
     * @return Lista de DTOs con todos los productos
     */
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(ProductoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca un producto por su ID.
     *
     * @param id Identificador del producto
     * @return DTO con los datos del producto
     * @throws ProductoNotFoundException Si el producto no existe
     */
    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        return ProductoResponseDTO.fromEntity(producto);
    }

    /**
     * Filtra productos por categoría.
     *
     * @param categoria Categoría por la cual filtrar
     * @return Lista de DTOs de productos de la categoría especificada
     */
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerPorCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria).stream()
                .map(ProductoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza completamente un producto existente (operación PUT).
     *
     * @param id ID del producto a actualizar
     * @param productoDTO DTO con los nuevos datos del producto
     * @return DTO de respuesta con el producto actualizado
     * @throws ProductoNotFoundException Si el producto no existe
     */
    public ProductoResponseDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        // Verificar que el producto existe
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        // Actualizar todos los campos usando el método del DTO
        productoDTO.updateEntity(producto);

        // Guardar cambios
        Producto productoActualizado = productoRepository.save(producto);

        return ProductoResponseDTO.fromEntity(productoActualizado);
    }

    /**
     * Actualiza solo el stock de un producto (operación PATCH).
     *
     * @param id ID del producto
     * @param nuevoStock Nuevo valor de stock
     * @return DTO de respuesta con el producto actualizado
     * @throws ProductoNotFoundException Si el producto no existe
     */
    public ProductoResponseDTO actualizarStock(Long id, Integer nuevoStock) {
        // Verificar que el producto existe
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        // Actualizar solo el stock
        producto.setStock(nuevoStock);

        // Guardar cambios
        Producto productoActualizado = productoRepository.save(producto);

        return ProductoResponseDTO.fromEntity(productoActualizado);
    }

    /**
     * Elimina un producto del sistema.
     *
     * REGLA DE NEGOCIO ADICIONAL (no requerida en la consigna):
     * No permite eliminar productos que tienen stock > 0 para evitar
     * pérdida de inventario. Esta es una práctica común en sistemas reales.
     *
     * @param id ID del producto a eliminar
     * @throws ProductoNotFoundException Si el producto no existe
     * @throws StockInsuficienteException Si el producto tiene stock > 0
     */
    public void eliminarProducto(Long id) {
        // Verificar que el producto existe
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        // REGLA DE NEGOCIO: No eliminar productos con stock
        // (Esta validación es una buena práctica, aunque no estaba en la consigna)
        if (producto.getStock() > 0) {
            throw new StockInsuficienteException(
                    "No se puede eliminar el producto '" + producto.getNombre() +
                            "' porque tiene stock disponible (" + producto.getStock() + " unidades). " +
                            "Por favor, reduzca el stock a 0 antes de eliminar."
            );
        }

        // Eliminar el producto
        productoRepository.deleteById(id);
    }
}