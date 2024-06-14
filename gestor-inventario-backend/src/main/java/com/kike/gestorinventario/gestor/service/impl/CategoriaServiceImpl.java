package com.kike.gestorinventario.gestor.service.impl;

import com.kike.gestorinventario.gestor.entity.Categoria;
import com.kike.gestorinventario.gestor.repository.CategoriaRepository;
import com.kike.gestorinventario.gestor.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;


    @Override
    public List<Categoria> buscarTodasCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> buscarCatePorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void borrarCatPorId(Long id) {
        categoriaRepository.deleteById(id);

    }

    @Override
    public Categoria buscarCatPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }
}
