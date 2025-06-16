package um.edu.uy;

import um.edu.uy.entities.Pelicula;
import um.edu.uy.UMovieService;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;

public class Main {
    public static void main(String[] args) {
        UMovieService servicio = new UMovieService();
        CargaCSV lector = new CargaCSV();

        // 1. Cargar películas primero
        MyHash<String, Boolean> vistas = new Hash<>(10000); // inicializa vacío
        long inicioCarga = System.nanoTime();
        lector.cargarPeliculas(servicio, vistas); // carga todas las películas
        long finCarga = System.nanoTime();
        System.out.println("Tiempo de carga de películas: " + (finCarga - inicioCarga) / 1_000_000 + " ms");

        // 2. Luego cargar calificaciones (y marca las vistas)
        long inicioCalificaciones = System.nanoTime();
        vistas = lector.cargarCalificaciones(servicio);
        long finCargaCal = System.nanoTime();
        System.out.println("Tiempo de carga de calificaciones: " + (finCargaCal- inicioCalificaciones) / 1_000_000 + " ms");

        long inicioCreditos = System.nanoTime();
        lector.cargarCreditos(servicio, vistas);
        long finCreditos = System.nanoTime();
        System.out.println("Tiempo de carga de créditos: " + (finCreditos - inicioCreditos) / 1_000_000 + " ms");

        long inicioConsulta = System.nanoTime();
        servicio.topPeliculasPorIdioma();
        long finConsulta = System.nanoTime();
        System.out.println("Tiempo ejecución top idioma: " + (finConsulta - inicioConsulta) / 1_000_000 + " ms");

    }
}