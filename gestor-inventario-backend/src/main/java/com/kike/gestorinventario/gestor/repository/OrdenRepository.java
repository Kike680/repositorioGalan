package com.kike.gestorinventario.gestor.repository;

import com.kike.gestorinventario.gestor.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {


}
