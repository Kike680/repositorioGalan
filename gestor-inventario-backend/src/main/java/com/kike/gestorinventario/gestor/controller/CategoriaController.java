package com.kike.gestorinventario.gestor.controller;

import com.kike.gestorinventario.gestor.dto.CategoriaDTO;
import com.kike.gestorinventario.gestor.dto.ProductoDTO;
import com.kike.gestorinventario.gestor.entity.Categoria;
import com.kike.gestorinventario.gestor.entity.Producto;
import com.kike.gestorinventario.gestor.service.CategoriaService;
import com.kike.gestorinventario.gestor.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin("*")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> categorias = categoriaService.buscarTodasCategorias();
        List<CategoriaDTO> categoriaDTOs = categorias.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriaDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarCatePorId(id);
        return ResponseEntity.ok().body(convertToDto(categoria));
    }

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = convertToEntity(categoriaDTO);
        return convertToDto(categoriaService.save(categoria));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoriaService.buscarCatePorId(id);
    }


    private CategoriaDTO convertToDto(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());
        categoriaDTO.setProductos(categoria.getProductos().stream().map(this::convertProductoToDto).collect(Collectors.toList()));
        return categoriaDTO;
    }

    private ProductoDTO convertProductoToDto(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setCantidad(producto.getCantidad());
        productoDTO.setImagenUrl(producto.getImagenUrl());
        /*productoDTO.setCategoria(convertToDto(producto.getCategoria()));*/
        productoDTO.setCategoria(new CategoriaDTO(producto.getCategoria().getId(), producto.getCategoria().getNombre(), null, null));
        return productoDTO;
    }

    private Categoria convertToEntity(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.getId());
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        // Asume que tienes un método para encontrar productos por ID
       /* categoria.setProductos(productoService.findAllById(categoriaDTO.getProductosIds()));*/
        return categoria;
    }


}
