package com.kike.gestorinventario.gestor.service;

import com.kike.gestorinventario.gestor.entity.Producto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> buscarTodosProductos();
    Optional<Producto> buscarProductoPorId(Long id);
    List<Producto> buscarProductoPorIdUsuario(Long id);
    Producto save(Producto producto);
    void updateCantidad(Long id, Integer cantidad);
    void borrarPorId(Long id);
    List<Producto> buscarProductPorNombre(String nombre);
    List<Producto> buscarProductPorCategoriaId(Long categoriaId);
    List<Producto> buscarProductPorPrecioMayorOigual(BigDecimal precio);
    List<Producto> buscarProductPorCantidadMenorQueOIgual(Integer cantidad);
}
