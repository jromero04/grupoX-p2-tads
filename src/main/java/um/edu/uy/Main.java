package um.edu.uy;

import um.edu.uy.entities.Pelicula;
import um.edu.uy.UMovieService;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;

public class Main {
    public static void main(String[] args) throws InvalidHashKey {
        UMovieService servicio = new UMovieService();
        CargaCSV lector = new CargaCSV();

        //Cargar películas
        MyHash<String, Boolean> vistas = new Hash<>(10000); // inicializa vacío
        long inicioCarga = System.nanoTime();
        lector.cargarPeliculas(servicio, vistas); // carga todas las películas
        long finCarga = System.nanoTime();
        System.out.println("Tiempo de carga de peliculas: " + (finCarga - inicioCarga) / 1_000_000 + " ms");

        //Luego cargar calificaciones y marca las vistas
        long inicioCalificaciones = System.nanoTime();
        vistas = lector.cargarCalificaciones(servicio);
        long finCargaCal = System.nanoTime();
        System.out.println("Tiempo de carga de calificaciones: " + (finCargaCal- inicioCalificaciones) / 1_000_000 + " ms");

        //Cargo los creditos con el metodo op
        long inicioCreditos = System.nanoTime();
        lector.cargarDirectoresYActoresOptimizado(servicio, vistas);
        long finCreditos = System.nanoTime();
        System.out.println("Tiempo de carga de creditos op: " + (finCreditos - inicioCreditos) / 1_000_000 + " ms");

        //Consulta
        long inicioConsulta = System.nanoTime();
        servicio.topPeliculasPorIdioma();
        long finConsulta = System.nanoTime();
        System.out.println("Tiempo ejecucion top idioma: " + (finConsulta - inicioConsulta) / 1_000_000 + " ms");

        //Consulta
        long inicioConsulta4 = System.nanoTime();
        servicio.top10Directores();
        long finConsulta4 = System.nanoTime();
        System.out.println("Tiempo ejecucion top direc: " + (finConsulta - inicioConsulta) / 1_000_000 + " ms");


    }
}