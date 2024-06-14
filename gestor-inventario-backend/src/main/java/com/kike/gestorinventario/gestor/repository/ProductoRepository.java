package com.kike.gestorinventario.gestor.repository;

import com.kike.gestorinventario.gestor.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Encuentra productos por nombre

    @Query("Select p from Producto  p Where p.nombre like %:nombre%")
    List<Producto> findByNombre(String nombre);

    // Encuentra productos por categoría
    List<Producto> findByCategoriaId(Long categoriaId);

    // Encuentra productos cuyo precio sea mayor a un valor específico
    List<Producto> findByPrecioGreaterThanEqual(BigDecimal precio);

    // Encuentra productos cuyo stock sea menor a un valor específico
    List<Producto> findByCantidadLessThanEqual(Integer cantidad);
}