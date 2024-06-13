package com.kike.gestorinventario.gestor.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<Long> productosIds;
}
