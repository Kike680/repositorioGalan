package com.kike.gestorinventario.gestor.controller;
import com.kike.gestorinventario.gestor.dto.CategoriaDTO;
import com.kike.gestorinventario.gestor.dto.ProductoDTO;
import com.kike.gestorinventario.gestor.entity.Categoria;
import com.kike.gestorinventario.gestor.entity.Producto;
import com.kike.gestorinventario.gestor.entity.Usuario;
import com.kike.gestorinventario.gestor.security.dto.UsuarioDTO;
import com.kike.gestorinventario.gestor.security.services.JwtUtil;
import com.kike.gestorinventario.gestor.security.services.UsuarioService;
import com.kike.gestorinventario.gestor.service.CategoriaService;
import com.kike.gestorinventario.gestor.service.ProductoService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin("*")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Optional<Producto> producto = productoService.buscarProductoPorId(id);

        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<ProductoDTO> findAll() {
        return productoService.buscarTodosProductos().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductoDTO>> findByIdUsuario(@PathVariable Long userId) {
        List<Producto> productos = productoService.buscarProductoPorIdUsuario(userId);

            List<ProductoDTO> productoDTOs = productos.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(productoDTOs);

    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProductoDTO productoString) throws IOException {
            System.out.println(productoString);
            Producto producto = convertToEntity(productoString);
            System.out.println(producto.toString());
            Categoria categoriaGuardar = categoriaService.buscarCatePorId(productoString.getCategoria().getId());
            System.out.println(categoriaGuardar);
            producto.setCategoria(categoriaGuardar);
            System.out.println(producto.toString());


                return ResponseEntity.ok().body(convertToDto(productoService.save(producto)));




    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productoService.borrarPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO, HttpServletRequest request) throws IOException {
        String token = request.getHeader("Authorization").substring(7);
        Claims claims = jwtUtil.extractAllClaims(token);
        Long userId = Long.parseLong(claims.getSubject());

        Optional<Producto> optionalProducto = productoService.buscarProductoPorId(id);
        if (optionalProducto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Producto producto = optionalProducto.get();

        if (!producto.getUsuario().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso para modificar este producto");
        }

        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCantidad(productoDTO.getCantidad());
        producto.setImagenUrl(productoDTO.getImagenUrl());

        Categoria categoria = categoriaService.buscarCatePorId(productoDTO.getCategoria().getId());
        producto.setCategoria(categoria);

        Producto updatedProducto = productoService.save(producto);

        return ResponseEntity.ok(convertToDto(updatedProducto));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorCategoria(@PathVariable Long categoriaId) {
        List<Producto> productos = productoService.buscarProductPorCategoriaId(categoriaId);
        List<ProductoDTO> productoDTOs = productos.stream()
                .map(this::convertToDto)
                .toList();
        return ResponseEntity.ok(productoDTOs);
    }


    private ProductoDTO convertToDto(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setCantidad(producto.getCantidad());
        productoDTO.setCategoria(convertToDtoCategoria(producto.getCategoria()));
        productoDTO.setImagenUrl(producto.getImagenUrl());
        productoDTO.setUsuario(convertToDtoUsuario(producto.getUsuario()));
        return productoDTO;
    }
    private UsuarioDTO convertToDtoUsuario(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setEmail(usuario.getEmail());
        return usuarioDTO;
    }
    public Usuario convertToEntityUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        return usuario;
    }

    private Categoria convertToEntityCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.getId());
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        // Asume que tienes un método para encontrar productos por ID
        /* categoria.setProductos(productoService.findAllById(categoriaDTO.getProductosIds()));*/
        return categoria;
    }
    private CategoriaDTO convertToDtoCategoria(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());
        //No es un array, borrarlo
        /*categoriaDTO.setProductos(categoria.getProductos().stream().map(this::convertToDto).collect(Collectors.toList()));*/
        return categoriaDTO;
    }

    private Producto convertToEntity(ProductoDTO productoDTO) throws IOException {
        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCantidad(productoDTO.getCantidad());
        //cambios nuevos
        producto.setImagenUrl(productoDTO.getImagenUrl());
        producto.setUsuario(convertToEntityUsuario(productoDTO.getUsuario()));
        // Asume que tienes un método para encontrar una categoría por ID
       /* producto.setCategoria(categoriaService.buscarCatePorId(productoDTO.getCategoriaId()).orElse(null));*/

        //nuevo

    /*    Usuario usuario = usuarioService.findById(productoDTO.getUsuarioId()).orElseThrow();
        Categoria categoria = categoriaService.buscarCatePorId(productoDTO.getCategoriaId()).orElseThrow();
        producto.setUsuario(usuario);
        producto.setCategoria(categoria);*/

        return producto;
    }

    Object getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }

    /*private ProductoDTO convertToDTOFromString(String productoString) {
        try {
            return objectMapper.readValue(productoString, ProductoDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir el JSON en ProductoDTO", e);
        }
    }*/
}
