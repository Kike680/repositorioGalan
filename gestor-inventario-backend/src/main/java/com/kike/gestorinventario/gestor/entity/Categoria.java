package com.kike.gestorinventario.gestor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude="productos")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    //Me va a aservir para evitar referencias circulares para los bucles infinitos
    @JsonIgnoreProperties("categoria")
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;

}