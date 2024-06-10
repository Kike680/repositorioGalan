package com.kike.gestorinventario.gestor.security.repository;

import com.kike.gestorinventario.gestor.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    boolean existsByUsername(String username);
}
