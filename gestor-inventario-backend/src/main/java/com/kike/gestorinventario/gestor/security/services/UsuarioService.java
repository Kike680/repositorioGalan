package com.kike.gestorinventario.gestor.security.services;

import com.kike.gestorinventario.gestor.entity.Rol;
import com.kike.gestorinventario.gestor.entity.Usuario;
import com.kike.gestorinventario.gestor.security.repository.RolRepository;
import com.kike.gestorinventario.gestor.security.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolRepository rolRepository;

    public Usuario buscarPorNombre(String username) {
        return usuarioRepository.findByUsername(username);
    }
    @Transactional
    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setUsername(usuario.getUsername());
        usuario.setRoles(usuario.getRoles());
        usuario.setPassword(usuario.getPassword());
        usuario.setEmail(usuario.getEmail());
        usuario.setPhone(usuario.getPhone());


        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        //Una vez guardado el usuario, vamos a recoger los roles de ese Usuario
        //A este rol (El de abajo justo) le voy a agregar el usuario guardado
        for (Rol rol : usuario.getRoles()) {
            rol.getUsuarios().add(usuarioGuardado);
        }
        return  usuarioGuardado;
    }
}
