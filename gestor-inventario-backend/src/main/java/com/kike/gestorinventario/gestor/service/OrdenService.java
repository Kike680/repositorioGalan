package com.kike.gestorinventario.gestor.service;

import com.kike.gestorinventario.gestor.entity.Orden;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrdenService {
    List<Orden> buscarTodasOrden();
    Optional<Orden> buscarOrdenPorId(Long id);
    Orden save(Orden orden);
    void borrarOrdenPorId(Long id);
    List<Orden> buscarOrdenPorUsuarioId(Long usuarioId);
    List<Orden> buscarOrdenPorFecha(LocalDate fecha);
    List<Orden> buscarOrdenPorTotalMayorOIgual(BigDecimal total);
}
