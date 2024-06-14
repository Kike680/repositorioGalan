package com.kike.gestorinventario.gestor.security.controller;


import com.kike.gestorinventario.gestor.entity.Rol;
import com.kike.gestorinventario.gestor.entity.Usuario;
import com.kike.gestorinventario.gestor.enums.RolEnum;
import com.kike.gestorinventario.gestor.security.dto.AuthRequest;
import com.kike.gestorinventario.gestor.security.dto.AuthResponse;
import com.kike.gestorinventario.gestor.security.dto.RegistroRequest;
import com.kike.gestorinventario.gestor.security.dto.UsuarioDTO;
import com.kike.gestorinventario.gestor.security.repository.RolRepository;
import com.kike.gestorinventario.gestor.security.repository.UsuarioRepository;
import com.kike.gestorinventario.gestor.security.services.JwtUtil;
import com.kike.gestorinventario.gestor.security.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Para iniciar sesion hay que pasarle un RequestBody una Peticiion de Authentificaion
    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody AuthRequest authRequest){
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        Usuario usuario = usuarioRepository.findByUsername(authRequest.getUsername());
        UsuarioDTO userDTO = UsuarioDTO.userToDto(usuario);
        //Verificar si el usario existe y su contraseña correcta Si lo que obtiene del JSON es igual ala contraseña de userDEtails authRequest no esta encriptada y la  de userDetails si
        if(userDetails != null && passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())){
            String jwt = jwtUtil.generateToken(userDTO);
            //El cuerpo de la respuesta es un token, iniciar sesion, generar el token y enviarlo
            return ResponseEntity.ok(new AuthResponse(jwt));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrecto");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroRequest registroRequest){
        //Primero vamos a buscar a este usuario por nombre, si es diferente de nulo es que ya existe
        System.out.println(registroRequest);
        if(usuarioService.buscarPorNombre(registroRequest.getUsername()) != null){
            return ResponseEntity.badRequest().body("El nombre de usuario ya esta en uso");
        }
        //En caso de que exista
        Usuario usuario = new Usuario();
        usuario.setUsername(registroRequest.getUsername());
        usuario.setPassword(passwordEncoder.encode(registroRequest.getPassword()));
        //Esta dos lineas son las nuevas
        usuario.setEmail(registroRequest.getEmail());
        usuario.setPhone(registroRequest.getPhone());

        Set<Rol> roles = new HashSet<>();

        //Si estos roles no fueran validos (O estuviera vacia)
        //Si es questa lista no esta vacia, tenemos el rol y lo agregamos
        //Si tu en el JSON le pasas solo el nombre y el PAssword pero no un rol le vamos a dar por defecto el de USER
        if(roles.isEmpty()){
            Rol userRole = rolRepository.findByNombre(RolEnum.ROLE_USER.name());
            roles.add(userRole);
            usuario.setRoles(roles);
        }
        usuarioService.guardarUsuario(usuario);

        return ResponseEntity.ok().body("{\"message\": \"Usuario registrado exitosamente\"}");
    }

}
//Este for iria debajo del Set<Rol> roles
//Recorremos RolEnum, es recorrer los roles que le estamos pasando por el request a la hora registar, por cada rol que vayamos recorriendo, vamos a ir buscando por nombre si coinciden con los nombres del ENUM
//De lo que estamos obteniendo, si es que este rolObj existe en estos Enum entonces vamos a agregarlo
/*        if(registroRequest.getRoles() !=null){
            for(RolEnum rolEnum : registroRequest.getRoles()){
                Rol rolObj = rolRepository.findByNombre(rolEnum.name());
                if(rolObj != null){
                    roles.add(rolObj);

                }
            }
            usuario.setRoles(roles);
        }*/