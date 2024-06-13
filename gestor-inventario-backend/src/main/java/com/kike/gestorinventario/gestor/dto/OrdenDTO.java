package com.kike.gestorinventario.gestor.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrdenDTO {
    private Long id;
    private LocalDate fecha;
    private BigDecimal total;
    private Long usuarioId;
    private List<OrdenDetalleDTO> detalles;
}
