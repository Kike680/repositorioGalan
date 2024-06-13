package com.kike.gestorinventario.gestor.service.impl;

import com.kike.gestorinventario.gestor.entity.Producto;
import com.kike.gestorinventario.gestor.repository.ProductoRepository;
import com.kike.gestorinventario.gestor.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> buscarTodosProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> buscarProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void borrarPorId(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> buscarProductPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    @Override
    public List<Producto> buscarProductPorCategoriaId(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    @Override
    public List<Producto> buscarProductPorPrecioMayorOigual(BigDecimal precio) {
        return productoRepository.findByPrecioGreaterThanEqual(precio);
    }

    @Override
    public List<Producto> buscarProductPorCantidadMenorQueOIgual(Integer cantidad) {
        return productoRepository.findByCantidadLessThanEqual(cantidad);
    }
}
