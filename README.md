# Api-Productos
API REST completa para gestión de productos en un sistema de e-commerce básico. Implementa todos los conceptos de desarrollo de APIs REST profesionales con Spring Boot:

- ✅ Arquitectura en capas (Controller, Service, Repository, Entity)
- ✅ Persistencia con Spring Data JPA y H2
- ✅ DTOs con validaciones Bean Validation
- ✅ **Uso de Lombok y Java Records** para código limpio y moderno
- ✅ Manejo centralizado de excepciones
- ✅ Documentación interactiva con Swagger/OpenAPI
- ✅ Operaciones CRUD completas con todos los métodos HTTP

---

## ⚙️ Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| Java | 17+ | Lenguaje de programación |
| Spring Boot | 3.2.0 | Framework principal |
| Spring Data JPA | 3.2.0 | Persistencia de datos |
| H2 Database | - | Base de datos en memoria |
| Bean Validation | - | Validación de datos |
| Springdoc OpenAPI | 2.3.0 | Documentación Swagger |
| Maven | 3.x | Gestión de dependencias |
| Lombok | 1.18.30 | Reducción de boilerplate |

---

## 🏗️ Estructura del Proyecto

```
productos-api/
├── src/main/java/com/utn/productos/
│   ├── model/
│   │   ├── Categoria.java              # Enum de categorías
│   │   └── Producto.java               # Entidad JPA con Lombok
│   ├── dto/
│   │   ├── ProductoDTO.java            # DTO con Lombok para crear/actualizar
│   │   ├── ProductoResponseDTO.java    # Record para respuestas
│   │   └── ActualizarStockDTO.java     # DTO con Lombok para PATCH de stock
│   ├── repository/
│   │   └── ProductoRepository.java     # Interfaz JPA Repository
│   ├── service/
│   │   └── ProductoService.java        # Lógica de negocio
│   ├── controller/
│   │   └── ProductoController.java     # Endpoints REST
│   ├── exception/
│   │   ├── ProductoNotFoundException.java
│   │   ├── StockInsuficienteException.java
│   │   ├── ErrorResponse.java
│   │   └── GlobalExceptionHandler.java
│   └── ProductosApiApplication.java    # Clase principal
├── src/main/resources/
│   └── application.properties          # Configuración
└── pom.xml
```

---

## 🚀 Instrucciones de Instalación y Ejecución

### Prerrequisitos

- Java JDK 17 o superior
- Maven 3.x
- IDE recomendado: IntelliJ IDEA, Eclipse o VS Code

### 1. Clonar el Repositorio

```bash
git clone <URL-DEL-REPOSITORIO>
cd productos-api
```

### 2. Compilar el Proyecto

```bash
mvn clean install
```

### 3. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

O ejecutar el JAR:
```bash
java -jar target/productos-api-1.0.0.jar
```

La aplicación iniciará en `http://localhost:8080`

---

## 🌐 Endpoints de la API

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/productos` | Listar todos los productos |
| GET | `/api/productos/{id}` | Obtener producto por ID |
| GET | `/api/productos/categoria/{categoria}` | Filtrar por categoría |
| POST | `/api/productos` | Crear nuevo producto |
| PUT | `/api/productos/{id}` | Actualizar producto completo |
| PATCH | `/api/productos/{id}/stock` | Actualizar solo el stock |
| DELETE | `/api/productos/{id}` | Eliminar producto |

---

## 📝 Ejemplos de Uso

### Crear un Producto (POST)

```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Laptop HP",
    "descripcion": "Laptop HP 15.6 pulgadas, 8GB RAM, 256GB SSD",
    "precio": 45000.00,
    "stock": 10,
    "categoria": "ELECTRONICA"
  }'
```

### Obtener Todos los Productos (GET)

```bash
curl http://localhost:8080/api/productos
```

### Actualizar Stock (PATCH)

```bash
curl -X PATCH http://localhost:8080/api/productos/1/stock \
  -H "Content-Type: application/json" \
  -d '{"stock": 15}'
```

### Filtrar por Categoría (GET)

```bash
curl http://localhost:8080/api/productos/categoria/ELECTRONICA
```

---

## 📚 Documentación Swagger

La API incluye documentación interactiva completa con Swagger UI.

**Acceso**: `http://localhost:8080/swagger-ui/index.html`

Desde Swagger UI puedes:
- Ver todos los endpoints documentados
- Probar cada endpoint directamente desde el navegador
- Ver esquemas de DTOs y respuestas
- Revisar códigos de estado HTTP posibles

---

## 🗄️ Consola H2

La base de datos H2 incluye una consola web para ver y consultar datos.

**Acceso**: `http://localhost:8080/h2-console`

**Configuración de conexión**:
- JDBC URL: `jdbc:h2:mem:productosdb`
- User Name: `sa`
- Password: (dejar vacío)

### Consultas SQL de Ejemplo

```sql
-- Ver todos los productos
SELECT * FROM productos;

-- Contar productos por categoría
SELECT categoria, COUNT(*) FROM productos GROUP BY categoria;

-- Productos con stock bajo
SELECT * FROM productos WHERE stock < 5;
```

---

## 🎯 Funcionalidades Implementadas

### Modelo de Datos
- ✅ Entidad `Producto` con anotaciones JPA y Lombok (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@ToString`)
- ✅ Enum `Categoria` con 5 categorías predefinidas
- ✅ Generación automática de IDs con `@GeneratedValue`

### DTOs y Validación
- ✅ `ProductoDTO`: clase con Lombok y validaciones completas (nombre, precio, stock, categoría)
- ✅ `ProductoResponseDTO`: **record de Java** para respuestas inmutables (mejor práctica)
- ✅ `ActualizarStockDTO`: clase con Lombok para operaciones PATCH
- ✅ Mapeo manual con métodos estáticos (sin librerías externas)

### Capa de Persistencia
- ✅ `ProductoRepository` extendiendo `JpaRepository`
- ✅ Query method personalizado: `findByCategoria()`
- ✅ Operaciones CRUD automáticas de Spring Data JPA

### Lógica de Negocio
- ✅ Servicio con `@Transactional` y `@RequiredArgsConstructor` (inyección por constructor con Lombok)
- ✅ Conversión entre DTOs y entidades
- ✅ **Regla de negocio**: no permite eliminar productos con stock > 0

### API REST
- ✅ Controller con `@RequiredArgsConstructor` y todos los métodos HTTP
- ✅ Uso correcto de códigos de estado HTTP
- ✅ `ResponseEntity` para control de respuestas
- ✅ Validación con `@Valid` en endpoints POST/PUT/PATCH

### Manejo de Errores
- ✅ Excepciones personalizadas
- ✅ `@ControllerAdvice` para manejo centralizado
- ✅ Respuestas de error estructuradas con `ErrorResponse`
- ✅ Manejo específico de errores de validación

### Documentación
- ✅ Swagger/OpenAPI con anotaciones completas
- ✅ `@Tag`, `@Operation`, `@ApiResponse` en todos los endpoints
- ✅ Descripciones y ejemplos en parámetros

---

## 🔧 Reglas de Negocio

### Eliminación de Productos
**Importante**: No se permite eliminar productos que tengan stock disponible.

Si intentas eliminar un producto con `stock > 0`, recibirás un error 409 (Conflict):

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 409,
  "error": "Conflicto con regla de negocio",
  "mensaje": "No se puede eliminar el producto 'Laptop HP' porque tiene stock disponible (10 unidades). Por favor, reduzca el stock a 0 antes de eliminar.",
  "path": "/api/productos/1"
}
```

**Solución**: Reducir el stock a 0 usando PATCH antes de eliminar:
```bash
# 1. Actualizar stock a 0
curl -X PATCH http://localhost:8080/api/productos/1/stock \
  -H "Content-Type: application/json" \
  -d '{"stock": 0}'

# 2. Eliminar producto
curl -X DELETE http://localhost:8080/api/productos/1
```

---

## 📸 Capturas de Pantalla

### Swagger UI - Lista de Endpoints

![img.png](img.png)

### Prueba POST - Crear Producto

![img_1.png](img_1.png)

### Prueba GET - Listar Productos

![img_2.png](img_2.png)

### Error 404 - Producto No Encontrado

![img_3.png](img_3.png)

### Error 400 - Validación Fallida

![img_4.png](img_4.png)

### Consola H2 - Tabla Productos

![img_5.png](img_5.png)

---

## 🧠 Conceptos de Spring Boot Aplicados

### 1. Spring Data JPA
Repositorio que extiende `JpaRepository` proporciona CRUD automático:
```java
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(Categoria categoria);
}
```

### 2. Bean Validation
Validaciones declarativas en DTOs:
```java
@NotBlank(message = "El nombre es obligatorio")
@Size(min = 3, max = 100)
private String nombre;
```

### 3. Records para DTOs de Respuesta
Java 17 records para DTOs inmutables:
```java
// ProductoResponseDTO es un record
public record ProductoResponseDTO(
    Long id,
    String nombre,
    String descripcion,
    Double precio,
    Integer stock,
    Categoria categoria
) {
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
```

### 4. Lombok para Reducción de Boilerplate
```java
// Entidad Producto
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Producto { /* ... */ }

// DTOs mutables
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO { /* ... */ }

// Servicios y Controllers
@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repository;
    // Constructor generado automáticamente
}
```

### 5. Manejo Centralizado de Excepciones
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarProductoNoEncontrado(...) {
        // Manejo consistente
    }
}
```

### 6. Transaccionalidad
```java
@Service
@Transactional
public class ProductoService {
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerTodos() {
        // Solo lectura
    }
}
```

---

## 🔥 Mejoras con Lombok y Records

Este proyecto utiliza las mejores prácticas modernas de Java y Spring Boot:

### ¿Por qué usar Lombok?

**Antes (sin Lombok):**
```java
public class Producto {
    private Long id;
    private String nombre;

    public Producto() {}

    public Producto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return "Producto{id=" + id + ", nombre='" + nombre + "'}";
    }
}
```

**Después (con Lombok):**
```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Producto {
    private Long id;
    private String nombre;
}
```

**Reducción de código: ~70%**

### ¿Por qué usar Records para DTOs de respuesta?

**Ventajas:**
- ✅ **Inmutabilidad**: Los records son inmutables por defecto (thread-safe)
- ✅ **Concisión**: Menos código, más legible
- ✅ **Semántica clara**: Un record es claramente un "contenedor de datos"
- ✅ **Equals/HashCode automáticos**: Comparación basada en valores

```java
// Record (1 línea de declaración)
public record ProductoResponseDTO(Long id, String nombre, Double precio) {}

// Equivale a una clase tradicional de ~50 líneas
```

### ¿Por qué @RequiredArgsConstructor?

**Antes:**
```java
@Service
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }
}
```

**Después:**
```java
@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repository;
    // Constructor generado automáticamente
}
```

**Ventajas:**
- ✅ Menos código boilerplate
- ✅ Inyección de dependencias type-safe
- ✅ Campos `final` previenen reasignación

---




## 👤 Autor

**Nombre completo**: Benjamin Ligonie Wertmiller
**Legajo**: 50971
**Materia**: Desarrollo de Software
**Trabajo Práctico**: APIs REST con Spring Boot
**Año**: 2025

---

## 📚 Referencias

- [Documentación Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/reference/)
- [Bean Validation Specification](https://beanvalidation.org/)
- [Springdoc OpenAPI](https://springdoc.org/)
- [REST API Best Practices](https://restfulapi.net/)

---

## 📝 Notas Adicionales

- La base de datos H2 se destruye al detener la aplicación (modo `create-drop`)
- Para producción real, cambiar a base de datos persistente (PostgreSQL, MySQL)
- Los datos de ejemplo deben agregarse manualmente o mediante un archivo `data.sql`
- **Lombok requiere plugin en el IDE**: IntelliJ (integrado), Eclipse (instalar desde marketplace), VS Code (extensión "Lombok Annotations Support")
- Se usa **record** para DTOs de respuesta (inmutables) y **Lombok** para DTOs de entrada (mutables con validaciones)
- El mapeo DTO-Entity manual es educativo; en proyectos grandes considerar MapStruct

---
