package com.kike.gestorinventario.gestor.security.filter;

import com.kike.gestorinventario.gestor.security.services.JwtUtil;
import com.kike.gestorinventario.gestor.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Nos va a servir para validar el token, que la cabecera este bien el payload...
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailService;

    //En la cabezera autorization tu vas a tener un token,
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        //Bearer token desde la B mayuscula hasyta el espacio hay 7, asi que solo me va a dar el token sin lo de Bearer
        //Filtro para acceder algun recurso, validando el token, para ver si es valido o no
        if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")){

            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);

        }
            //Si se extraigo el nombre de usuario y que aparte en el contexto actual de seguridad la autentificacion sea nula
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Cargar los detalles del usuario correspondiente al nombre de usuario
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            //Aqui validamos el token que acabamos de obtener de parte del cliente y le pasamos los detalles del usuario, si ese token es valido vamos a establecer la autentificaon
            if(jwtUtil.validateToken(jwt, userDetails)) {
                //Si el token es valido, establecer la autenticaion en el cntexto de seguridad
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //Que se repita la cadena de filtros 
        filterChain.doFilter(request, response);
    }
}
