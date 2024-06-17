package com.kike.gestorinventario.gestor.repository;

import com.kike.gestorinventario.gestor.entity.Categoria;
import com.kike.gestorinventario.gestor.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    //Si luego introduzco un buscador si encuentra el nombre
    @Query("Select c from Categoria  c Where c.nombre like %:nombre%")
    Categoria findByNombre(@Param("nombre")String nombre);

    /*@Query("SELECT p FROM Categoria p WHERE p.usuario.id = :idUsuario")
    List<Producto> findByIdUsuario(long idUsuario);*/
}
