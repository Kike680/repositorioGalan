package com.kike.gestorinventario.gestor.security.repository;

import com.kike.gestorinventario.gestor.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
    boolean existsByNombre(String nombre);

}
