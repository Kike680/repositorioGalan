package com.kike.gestorinventario.gestor.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
@ToString
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String phone;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name= "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    //Los roles vamos a trabajar con Enums
    @Enumerated(EnumType.STRING)
    private Set<Rol> roles = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    private List<Orden> ordenes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
                Objects.equals(username, usuario.username) &&
                Objects.equals(password, usuario.password) &&
                Objects.equals(email, usuario.email) &&
                Objects.equals(phone, usuario.phone) &&
                Objects.equals(roles, usuario.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, phone, roles);
    }
}
