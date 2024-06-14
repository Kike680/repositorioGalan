package com.kike.gestorinventario.gestor.dto;

import com.kike.gestorinventario.gestor.entity.Producto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(exclude ={"productos"})
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<ProductoDTO> productos;
}
