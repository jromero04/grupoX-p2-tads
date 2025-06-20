package um.edu.uy;

import um.edu.uy.CSV.CargaCSV;
import um.edu.uy.CSV.CargaCalificaciones;
import um.edu.uy.CSV.CargaCreditos;
import um.edu.uy.CSV.CargaPeliculas;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalidHashKey {
        /*UMovieService servicio = new UMovieService();
        CargaCSV lector = new CargaCSV();

        //Cargar películas
        MyHash<String, Boolean> vistas = new Hash<>(); // inicializa vacío
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

         */

        Scanner scanner = new Scanner(System.in);
        UMovieService servicio = new UMovieService();
        MyHash<String, Boolean> vistas = new Hash<>();
        boolean salir = false;

        while (!salir) {
            System.out.println("Menu interactivo:");
            System.out.println("1. Cargar peliculas");
            System.out.println("2. Cargar calificaciones");
            System.out.println("3. Cargar creditos");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.println("Cargando peliculas...");
                    long inicioPeliculas = System.nanoTime();
                    CargaPeliculas cargaPeliculas = new CargaPeliculas();
                    cargaPeliculas.cargarPeliculas(servicio, vistas);
                    long finPeliculas = System.nanoTime();
                    System.out.println("Tiempo de carga de peliculas: " + (finPeliculas - inicioPeliculas) / 1_000_000 + " ms");

                    // caso de prueba para ver si se estan asignando las cosas correctamente
//                    try {
//                        Pelicula pelicula = servicio.getPeliculas().search("11862");
//                        if (pelicula != null) {
//                            System.out.println("Informacion de la pelicula:");
//                            System.out.println("ID: " + pelicula.getIdPelicula());
//                            System.out.println("Título: " + pelicula.getTituloPelicula());
//                            System.out.println("Idioma: " + pelicula.getIdiomaOriginal());
//                            System.out.println("Ingresos: " + pelicula.getIngresos());
//                            System.out.println("Géneros: " + pelicula.getGeneros());
//                            if (pelicula.getColeccion() != null) {
//                                System.out.println("Colección: " + pelicula.getColeccion().getTituloColeccion());
//                                System.out.println("Ingresos totales de la coleccion: " + pelicula.getColeccion().getIngresosTotales());
//                            } else {
//                                System.out.println("Colección: Ninguna");
//                            }
//                        } else {
//                            System.out.println("La película con ID 11862 no se encuentra en el servicio.");
//                        }
//                    } catch (InvalidHashKey e) {
//                        System.out.println("Error al buscar la película: " + e.getMessage());
//                    }

                    break;
                case 2:
                    System.out.println("Cargando calificaciones...");
                    long inicioCalificaciones = System.nanoTime();
                    CargaCalificaciones cargaCalificaciones = new CargaCalificaciones();
                    vistas = cargaCalificaciones.cargarCalificaciones(servicio);
                    long finCalificaciones = System.nanoTime();
                    System.out.println("Tiempo de carga de calificaciones: " + (finCalificaciones - inicioCalificaciones) / 1_000_000 + " ms");
                    break;
                case 3:
                    System.out.println("Cargando créditos...");
                    long inicioCreditos = System.nanoTime();
                    CargaCreditos cargaCreditos = new CargaCreditos();
                    cargaCreditos.cargarCreditosOptimizadoOP(servicio, vistas);
                    long finCreditos = System.nanoTime();
                    System.out.println("Tiempo de carga de créditos: " + (finCreditos - inicioCreditos) / 1_000_000 + " ms");
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        scanner.close();
    }
}