package um.edu.uy;

import um.edu.uy.entities.Pelicula;
import um.edu.uy.UMovieService;
import um.edu.uy.tads.hash.MyHash;

public class Main {
    public static void main(String[] args) {
        UMovieService servicio = new UMovieService();
        CargaCSV lector = new CargaCSV();

        long inicioCarga = System.nanoTime();
        lector.cargarPeliculas(servicio);
        long finCarga = System.nanoTime();
        System.out.println("Tiempo de carga de películas: " + (finCarga - inicioCarga) / 1_000_000 + " ms");

        long inicioCreditos = System.nanoTime();
        lector.cargarCreditos(servicio);
        long finCreditos = System.nanoTime();
        System.out.println("Tiempo de carga de créditos: " + (finCreditos - inicioCreditos) / 1_000_000 + " ms");

        long inicioConsulta = System.nanoTime();
        servicio.topPeliculasPorIdioma();
        long finConsulta = System.nanoTime();
        System.out.println("Tiempo ejecución top idioma: " + (finConsulta - inicioConsulta) / 1_000_000 + " ms");
    }
}