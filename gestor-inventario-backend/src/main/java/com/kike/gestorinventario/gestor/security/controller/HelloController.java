package com.kike.gestorinventario.gestor.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String index(){
        System.out.println("Hello desde el hello controler");
        return "Hello desde el hello controler";
    }
}
