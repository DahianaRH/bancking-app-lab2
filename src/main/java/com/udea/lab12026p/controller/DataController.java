package com.udea.lab12026p.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DataController {

    private static final String VERSION = "1.0.0";

    /**
     * Comprueba que la API bancaria responde.
     */
    @GetMapping("/")
    public String healthCheck() {
        return "BANKING API HEALTH CHECK OK!";
    }

    /**
     * Consulta la versión de la aplicación.
     */
    @GetMapping("/version")
    public Map<String, String> version() {
        return Map.of(
                "application", "Aplicación Bancaria",
                "version", VERSION
        );
    }
}
