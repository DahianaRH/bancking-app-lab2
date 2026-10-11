
package com.udea.lab12026p;

import com.udea.lab12026p.controller.DataController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaApplicationTests {

    private DataController dataController;

    @BeforeEach
    void setUp() {
        dataController = new DataController();
    }

    @Test
    void health() {
        assertEquals(
                "BANKING API HEALTH CHECK OK!",
                dataController.healthCheck()
        );
    }

    @Test
    void version() {
        Map<String, String> response = dataController.version();

        assertEquals("Aplicación Bancaria", response.get("application"));
        assertEquals("1.0.0", response.get("version"));
    }

    @Test
    void versionMustContainApplicationAndVersion() {
        Map<String, String> response = dataController.version();

        assertNotNull(response.get("application"));
        assertNotNull(response.get("version"));
        assertEquals(2, response.size());
    }
}
