package com.kike.gestorinventario.gestor.repository;

import com.kike.gestorinventario.gestor.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Encuentra categorías por nombre
    Categoria findByNombre(String nombre);
}
