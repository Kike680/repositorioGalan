package com.kike.gestorinventario.gestor.repository;

import com.kike.gestorinventario.gestor.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Encuentra categorías por nombre
    List<Categoria> findAll();

    Optional <Categoria>findCategoriaById(Long id);
    /*Categoria save(Categoria categoria);
    void delete(Categoria categoria);*/

        //Si luego introduzco un buscador si encuentra el nombre
    @Query("Select c from Categoria  c Where c.nombre like %:nombre%")
    Categoria findByNombre(@Param("nombre")String nombre);
}
