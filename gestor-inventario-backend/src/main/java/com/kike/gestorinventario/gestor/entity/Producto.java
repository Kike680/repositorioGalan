package com.kike.gestorinventario.gestor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    //Quizas haya que quitarlo
    private BigDecimal precio;
    private Integer cantidad;

    @Lob
    @Column(columnDefinition="longtext")
    private String imagenUrl;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; // Referencia al usuario propietario
}
