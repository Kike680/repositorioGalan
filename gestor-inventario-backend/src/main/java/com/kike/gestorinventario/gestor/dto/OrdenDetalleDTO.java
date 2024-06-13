package com.kike.gestorinventario.gestor.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrdenDetalleDTO {
    private Long id;
    private Integer cantidad;
    private BigDecimal precio;
    private Long productoId;
    private Long ordenId;
}
