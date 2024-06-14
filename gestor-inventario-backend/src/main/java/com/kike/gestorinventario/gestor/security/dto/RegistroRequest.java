package com.kike.gestorinventario.gestor.security.dto;

import com.kike.gestorinventario.gestor.enums.RolEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
@Data
@AllArgsConstructor
public class RegistroRequest {

    private String username;
    private String password;
    private String email;
    private String phone;
   /* private Set<RolEnum> roles;*/
}
