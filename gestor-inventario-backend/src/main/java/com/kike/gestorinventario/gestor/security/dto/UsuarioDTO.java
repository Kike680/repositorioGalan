package com.kike.gestorinventario.gestor.security.dto;

import com.kike.gestorinventario.gestor.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;


    public static UsuarioDTO userToDto(Usuario user){
        return UsuarioDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

}
