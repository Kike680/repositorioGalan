package com.kike.gestorinventario.gestor.security.services;


//Se va a encagar de verificar y cargar el Token con su cabezara clave secreta etc.

import com.kike.gestorinventario.gestor.security.dto.UsuarioDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.expiration}")
    private long expiration;

    private Key secretKey;

    //Aqui creamos primero un tamaño
    @PostConstruct
    public void init() {
        //Vamos a crear un array de bytes
        byte[] apiSecretBytes = new byte[64];
        //Te va  agenerar los bytes de ese arreglo de forma aleatoria
        new SecureRandom().nextBytes(apiSecretBytes);
        //Le vamos a asignar el algoritmo para esa clave secreta de bytes aleatorios creado con la linea anterior
        secretKey = Keys.hmacShaKeyFor(apiSecretBytes);

    }

    public String generateToken(UsuarioDTO usuarioDTO) {

        Map<String, Object> claims = new HashMap<>();
        return  createToken(claims, usuarioDTO);

    }

    private String createToken(Map<String, Object> claims,UsuarioDTO usuarioDTO) {

        //Aqui estamos creando un tocker //Acordarme por si da fallos por la injeccion
        return Jwts.builder()
                .claims(claims)
                .claim("user", usuarioDTO)
                .subject(usuarioDTO.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getEncoded()))
                .compact();

    }

        //Esto seria para extraer los datos
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        //Primero tenemos que verificar quienes somos para eso le vamos a pasar nuestro token
        //Estamos obteniendo los Claims que seria el payload Datos a enviar firmados por jwt
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getEncoded()))
                .build()
                .parseSignedClaims(token).getPayload();
    }

    //Para obtener el sujeto Usuario(username), que estaria en el subject
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);

    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
 }
