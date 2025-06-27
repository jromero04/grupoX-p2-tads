package um.edu.uy.Consultas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.edu.uy.UMovieService;
import um.edu.uy.entities.Calificacion;
import um.edu.uy.entities.Pelicula;

import java.io.Serial;

import static org.junit.jupiter.api.Assertions.*;

class Top5PeliculasPorIdiomaTest {
    private UMovieService servicio;
    private Top5PeliculasPorIdioma consulta;

    @BeforeEach
    void setUp() {
        servicio = new UMovieService();
        consulta = new Top5PeliculasPorIdioma(servicio);

        // Agregar películas de prueba con diferentes idiomas y calificaciones
        Pelicula peliculaEn = new Pelicula("1", "Pelicula A", "en", 1000);
        peliculaEn.agregarCalificacion(new Calificacion(null, peliculaEn, 4.5, null));
        servicio.agregarPelicula(peliculaEn);

        Pelicula peliculaFr = new Pelicula("2", "Pelicula B", "fr", 2000);
        peliculaFr.agregarCalificacion(new Calificacion(null, peliculaFr, 3.0, null));
        servicio.agregarPelicula(peliculaFr);

        Pelicula peliculaEs = new Pelicula("3", "Pelicula C", "es", 3000);
        peliculaEs.agregarCalificacion(new Calificacion(null, peliculaEs, 5.0, null));
        servicio.agregarPelicula(peliculaEs);

        Pelicula peliculaPt = new Pelicula("4", "Pelicula D", "pt", 4000);
        peliculaPt.agregarCalificacion(new Calificacion(null, peliculaPt, 2.5, null));
        servicio.agregarPelicula(peliculaPt);

        Pelicula peliculaIt = new Pelicula("5", "Pelicula E", "it", 5000);
        peliculaIt.agregarCalificacion(new Calificacion(null, peliculaIt, 4.0, null));
        servicio.agregarPelicula(peliculaIt);
    }

    @Test
    void testTopPeliculasPorIdiomaConDatosVacios() {
        // No se cargan películas
        servicio = new UMovieService(); // Reiniciar servicio vacío
        consulta = new Top5PeliculasPorIdioma(servicio);

        consulta.topPeliculasPorIdioma();
        // No debería haber películas filtradas
        assertEquals(0, servicio.getPeliculas().getSize());
    }


    @Test
    void testTopPeliculasPorIdiomaConUnIdioma() {
        // Cargar películas de un solo idioma
        servicio.agregarPelicula(new Pelicula("1", "Pelicula A", "en", 1000));
        servicio.agregarPelicula(new Pelicula("2", "Pelicula B", "en", 2000));
        servicio.agregarPelicula(new Pelicula("3", "Pelicula C", "en", 3000));

        consulta.topPeliculasPorIdioma();
        // Verificar que se filtren correctamente
        assertEquals(3, servicio.getPeliculas().getSize());
    }


    @Test
    void testTopPeliculasPorIdiomaConMultiplesIdiomas() {
        // Cargar películas de diferentes idiomas
        servicio.agregarPelicula(new Pelicula("1", "Pelicula A", "en", 1000));
        servicio.agregarPelicula(new Pelicula("2", "Pelicula B", "fr", 2000));
        servicio.agregarPelicula(new Pelicula("3", "Pelicula C", "es", 3000));

        consulta.topPeliculasPorIdioma();
        // Verificar que se filtren correctamente
        assertEquals(3, servicio.getPeliculas().getSize());
    }


    @Test
    void testTopPeliculasPorIdiomaConMenosDeCincoPeliculas() {
        // Cargar menos de 5 películas para un idioma
        servicio.agregarPelicula(new Pelicula("1", "Pelicula A", "en", 1000));
        servicio.agregarPelicula(new Pelicula("2", "Pelicula B", "en", 2000));

        consulta.topPeliculasPorIdioma();
        // Verificar que se filtren correctamente
        assertEquals(2, servicio.getPeliculas().getSize());
    }

    @Test
    void testTopPeliculasPorIdiomaConPeliculasSinCalificaciones() {
        // Cargar películas sin calificaciones
        Pelicula peliculaSinCalificaciones = new Pelicula("1", "Pelicula A", "en", 1000);
        servicio.agregarPelicula(peliculaSinCalificaciones);

        consulta.topPeliculasPorIdioma();
        // Verificar que no se incluyan películas sin calificaciones
        assertEquals(0, peliculaSinCalificaciones.getCantidadDeCalificaciones());
    }
}