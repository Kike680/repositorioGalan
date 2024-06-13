package com.kike.gestorinventario.gestor.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;
}