package com.kike.gestorinventario.gestor.service;

import com.kike.gestorinventario.gestor.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> buscarTodasCategorias();
    Categoria buscarCatePorId(Long id);
    Categoria save(Categoria categoria);
    void borrarCatPorId(Long id);
    Categoria buscarCatPorNombre(String nombre);


}
