package com.kike.gestorinventario.gestor.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}