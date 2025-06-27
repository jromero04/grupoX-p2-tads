package um.edu.uy.Consultas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.edu.uy.UMovieService;
import um.edu.uy.entities.Calificacion;
import um.edu.uy.entities.Participante;
import um.edu.uy.entities.Pelicula;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class Top10DirectoresConMejorCalificacionTest {

    private UMovieService servicio;
    private Top10DirectoresConMejorCalificacion consulta;

    @BeforeEach
    void setUp() {
        servicio = new UMovieService();
        consulta = new Top10DirectoresConMejorCalificacion(servicio);

        // Crear directores
        Participante d1 = new Participante("Director Uno", "Director");
        Participante d2 = new Participante("Director Dos", "Director");

        // Agregar 2 películas por director con muchas calificaciones
        for (int i = 1; i <= 2; i++) {
            Pelicula peli = new Pelicula("D1P" + i, "Película D1 " + i, "en", 0);
            for (int j = 0; j < 70; j++) {
                peli.agregarCalificacion(new Calificacion(null, peli, 4.0, LocalDateTime.now()));
            }
            d1.agregarPelicula(peli);
        }

        for (int i = 1; i <= 2; i++) {
            Pelicula peli = new Pelicula("D2P" + i, "Película D2 " + i, "en", 0);
            for (int j = 0; j < 70; j++) {
                peli.agregarCalificacion(new Calificacion(null, peli, 3.0, LocalDateTime.now()));
            }
            d2.agregarPelicula(peli);
        }

        servicio.getParticipantes().add("Director Uno-Director", d1);
        servicio.getParticipantes().add("Director Dos-Director", d2);
    }

    @Test
    void testConsultaDirectoresConMediana() {
        // Capturar salida del sout de mi ejecutar()
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            consulta.ejecutar();
        } catch (Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }

        String output = outContent.toString();
        String[] lineas = output.split("\n");

        // Verificamos que haya resultados impresos (mínimo 2 líneas esperadas)
        assertTrue(lineas.length >= 2);

        // Verificamos contenido
        assertTrue(output.contains("Director Uno"));
        assertTrue(output.contains("Director Dos"));
        System.out.println("OUTPUT:\n" + output);
        assertTrue(output.contains("4,00"));
        assertTrue(output.contains("3,00"));
    }
}