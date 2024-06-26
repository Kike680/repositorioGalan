package com.kike.gestorinventario.gestor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kike.gestorinventario.gestor.security.dto.UsuarioDTO;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer cantidad;
    private CategoriaDTO categoria;
    private String imagenUrl;
    private UsuarioDTO usuario;
}
