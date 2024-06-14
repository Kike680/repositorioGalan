package com.kike.gestorinventario.gestor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kike.gestorinventario.gestor.SubirFotos.FileUpload;
import com.kike.gestorinventario.gestor.dto.ProductoDTO;
import com.kike.gestorinventario.gestor.entity.Categoria;
import com.kike.gestorinventario.gestor.entity.Producto;
import com.kike.gestorinventario.gestor.entity.Usuario;
import com.kike.gestorinventario.gestor.security.services.UsuarioService;
import com.kike.gestorinventario.gestor.service.CategoriaService;
import com.kike.gestorinventario.gestor.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<ProductoDTO> findAll() {
        return productoService.buscarTodosProductos().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> findById(@PathVariable Long id) {
        Optional<Producto> producto = productoService.buscarProductoPorId(id);
        return producto.map(value -> ResponseEntity.ok(convertToDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductoDTO save(@RequestBody ProductoDTO productoString) throws IOException {
        System.out.println("Producto: " + productoString);
        ProductoDTO productoDTO = productoString;
        Producto producto = convertToEntity(productoDTO);
        if(this.getAuthentication() instanceof Usuario user){
           producto.setUsuario(user);
        }

        return convertToDto(productoService.save(producto));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productoService.borrarPorId(id);
    }

    private ProductoDTO convertToDto(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setCantidad(producto.getCantidad());
        productoDTO.setCategoria(producto.getCategoria());
        productoDTO.setImagenUrl(producto.getImagenUrl());
        return productoDTO;
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
     /*   if (producto.getImagenUrl() != null && !producto.getImagenUrl().isEmpty()) {
            byte[]  imagenBytes= Base64.getDecoder().decode(producto.getImagenUrl());
            String imagenLink = saveImage(imagenBytes);
            producto.setImagenUrl(imagenLink);
        }*/
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
    //NUEVO
   /* private String saveImage(byte[] imagenBytes) throws IOException {
        String directoryPath = "path/to/your/image/directory"; // Reemplaza esto con tu ruta de directorio
        String fileName = UUID.randomUUID().toString() + ".png"; // Genera un nombre único para la imagen
        String filePath = directoryPath + File.separator + fileName;

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Crea el directorio si no existe
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imagenBytes);
        }

        return filePath; // Devuelve la ruta del archivo guardado
    }
*/
    /*private ProductoDTO convertToDTOFromString(String productoString) {
        try {
            return objectMapper.readValue(productoString, ProductoDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir el JSON en ProductoDTO", e);
        }
    }*/
}
