package um.edu.uy.Consultas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.edu.uy.UMovieService;
import um.edu.uy.entities.Calificacion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.MyHash;

import java.io.Serial;

import static org.junit.jupiter.api.Assertions.*;

class Top5PeliculasPorIdiomaTest {
    private UMovieService servicio;
    private Top5PeliculasPorIdioma consulta;

    @BeforeEach
    void setUp() {
        servicio = new UMovieService();
        consulta = new Top5PeliculasPorIdioma(servicio);
    }

    @Test
    void testTopPeliculasPorIdiomaConDatosVacios() throws InvalidHashKey {
        MyHash<String, MyList<Pelicula>> resultado = consulta.obtenerTopPeliculasPorIdioma();
        for (String idioma : new String[]{"en", "fr", "it", "es", "pt"}) {
            assertTrue(resultado.search(idioma).isEmpty());
        }
    }

    @Test
    void testTopPeliculasPorIdiomaConUnIdioma() throws InvalidHashKey {
        Pelicula p1 = new Pelicula("1", "Pelicula A", "en", 1000);
        p1.agregarCalificacion(new Calificacion(null, p1, 4.5, null));
        servicio.agregarPelicula(p1);

        Pelicula p2 = new Pelicula("2", "Pelicula B", "en", 1500);
        p2.agregarCalificacion(new Calificacion(null, p2, 3.5, null));
        p2.agregarCalificacion(new Calificacion(null, p2, 4, null));
        servicio.agregarPelicula(p2);

        MyHash<String, MyList<Pelicula>> resultado = consulta.obtenerTopPeliculasPorIdioma();
        MyList<Pelicula> en = resultado.search("en");

        assertEquals(2, en.size());
        assertEquals("2", en.get(0).getIdPelicula()); // tiene más calificaciones
        assertEquals("1", en.get(1).getIdPelicula());
    }

    @Test
    void testTopPeliculasPorIdiomaConMultiplesIdiomas() throws InvalidHashKey {
        Pelicula p1 = new Pelicula("1", "Pelicula A", "en", 1000);
        p1.agregarCalificacion(new Calificacion(null, p1, 4.0, null));
        servicio.agregarPelicula(p1);

        Pelicula p2 = new Pelicula("2", "Pelicula B", "fr", 2000);
        p2.agregarCalificacion(new Calificacion(null, p2, 3.0, null));
        servicio.agregarPelicula(p2);

        Pelicula p3 = new Pelicula("3", "Pelicula C", "es", 3000);
        p3.agregarCalificacion(new Calificacion(null, p3, 5.0, null));
        servicio.agregarPelicula(p3);

        MyHash<String, MyList<Pelicula>> resultado = consulta.obtenerTopPeliculasPorIdioma();

        assertEquals(1, resultado.search("en").size());
        assertEquals(1, resultado.search("fr").size());
        assertEquals(1, resultado.search("es").size());
        assertTrue(resultado.search("it").isEmpty());
        assertTrue(resultado.search("pt").isEmpty());
    }

    @Test
    void testTopPeliculasPorIdiomaConMenosDeCincoPeliculas() throws InvalidHashKey {
        for (int i = 1; i <= 3; i++) {
            Pelicula p = new Pelicula(String.valueOf(i), "Pelicula " + i, "it", 1000 * i);
            p.agregarCalificacion(new Calificacion(null, p, 3 + i, null));

            // darle más calificaciones a la película con ID 3 para que gane el top
            if (i == 3) {
                p.agregarCalificacion(new Calificacion(null, p, 5.0, null));
                p.agregarCalificacion(new Calificacion(null, p, 4.0, null));
            }
            servicio.agregarPelicula(p);
        }

        MyHash<String, MyList<Pelicula>> resultado = consulta.obtenerTopPeliculasPorIdioma();
        MyList<Pelicula> it = resultado.search("it");

        assertEquals(3, it.size());
        assertEquals("3", it.get(0).getIdPelicula()); // mayor ingreso
    }

    @Test
    void testTopPeliculasPorIdiomaConPeliculasSinCalificaciones() throws InvalidHashKey {
        Pelicula sinCalif = new Pelicula("10", "Sin Calificación", "es", 1000);
        servicio.agregarPelicula(sinCalif);

        MyHash<String, MyList<Pelicula>> resultado = consulta.obtenerTopPeliculasPorIdioma();
        assertTrue(resultado.search("es").isEmpty());
    }
}