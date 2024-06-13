package com.kike.gestorinventario.gestor.repository;

import com.kike.gestorinventario.gestor.entity.OrdenDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenDetalleRepository extends JpaRepository<OrdenDetalle, Long> {
    // Encuentra detalles de órdenes por producto
    List<OrdenDetalle> findByProductoId(Long productoId);

    // Encuentra detalles de órdenes por orden
    List<OrdenDetalle> findByOrdenId(Long ordenId);
}
