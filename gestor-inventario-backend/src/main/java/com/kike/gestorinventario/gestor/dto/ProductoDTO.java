package com.kike.gestorinventario.gestor.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer cantidad;
    private Long categoriaId;
}
