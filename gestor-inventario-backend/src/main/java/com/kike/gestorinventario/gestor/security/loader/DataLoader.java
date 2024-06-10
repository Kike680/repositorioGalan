package com.kike.gestorinventario.gestor.security.loader;

//Apenas se ejecute la aplicacion tendremos roles guardados por defecto

import com.kike.gestorinventario.gestor.entity.Rol;
import com.kike.gestorinventario.gestor.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class DataLoader {

    @Autowired
    private RolRepository rolRepository;

    @Bean
    public CommandLineRunner initData(){
        return args -> {
            cargarRolesPorDefecto();
        };
    }

    private void cargarRolesPorDefecto(){

        if(!rolRepository.existsByNombre("ROLE_USER")){
            Rol userRole = new Rol();
            userRole.setNombre("ROLE_USER");
            rolRepository.save(userRole);

        }
        if(!rolRepository.existsByNombre("ROLE_ADMIN")){
            Rol adminRol = new Rol();
            adminRol.setNombre("ROLE_ADMIN");
            rolRepository.save(adminRol);

        }
    }

}
