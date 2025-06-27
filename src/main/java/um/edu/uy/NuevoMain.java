package um.edu.uy;

import um.edu.uy.CSV.CargaCalificaciones;
import um.edu.uy.CSV.CargaCreditos;
import um.edu.uy.CSV.CargaPeliculas;
import um.edu.uy.Consultas.ActorConMasCalificacionesPorMes;
import um.edu.uy.Consultas.Top10DirectoresConMejorCalificacion;
import um.edu.uy.Consultas.Top5PeliculasPorIdioma;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;

import java.util.Scanner;

public class NuevoMain {
    public static void main(String[] args) throws InvalidHashKey {
        UMovieService servicio = new UMovieService();
        CargaPeliculas carga = new CargaPeliculas();
        CargaCalificaciones cargaCalificaciones = new CargaCalificaciones();
        CargaCreditos cargaCreditos = new CargaCreditos();

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir){
        System.out.println("\n=== Menu principal ===");
        System.out.println("1. Carga de datos");
        System.out.println("2. Ejecutar consultas");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opcion: ");
        String opcion = scanner.nextLine();

        switch (opcion) {
            case "1":
                long inicio = System.currentTimeMillis();

                carga.cargarPeliculas(servicio);
                cargaCreditos.cargarCreditos(servicio);
                cargaCalificaciones.cargarCalificaciones(servicio);

                long fin = System.currentTimeMillis();
                long tiempo = fin - inicio;
                System.out.println("Carga de datos exitosa, tiempo de ejecucion de la carga: " + tiempo + " ms");
                break;

            case "2":
                mostrarSubmenuConsultas(scanner, servicio);
                break;

            case "3":
                salir = true;
                break;

            default:
                System.out.println("Opción inválida.");
        }
        }
    }

    private static void mostrarSubmenuConsultas(Scanner scanner, UMovieService servicio) throws InvalidHashKey {
        Top5PeliculasPorIdioma consulta1 = new Top5PeliculasPorIdioma(servicio);
        ActorConMasCalificacionesPorMes consulta5 = new ActorConMasCalificacionesPorMes(servicio);
        Top10DirectoresConMejorCalificacion consulta4 = new Top10DirectoresConMejorCalificacion(servicio);

        boolean volver = false;

        while (!volver) {
            System.out.println("\n=== Menú de Consultas ===");
            System.out.println("1. Top 5 películas con más calificaciones por idioma.");
            System.out.println("2. Top 10 películas con mejor calificación media por parte de usuarios.");
            System.out.println("3. Top 5 colecciones que más ingresos generaron.");
            System.out.println("4. Top 10 directores con mejor calificación.");
            System.out.println("5. Actor con más calificaciones por mes.");
            System.out.println("6. Usuarios con más calificaciones por género.");
            System.out.println("7. Volver al menú principal");
            System.out.print("Seleccione una consulta: ");
            String opcionConsulta = scanner.nextLine();

            switch (opcionConsulta) {
                case "1":
                    long inicio = System.currentTimeMillis();
                    consulta1.topPeliculasPorIdioma();
                    long fin = System.currentTimeMillis();
                    long tiempo = fin - inicio;
                    System.out.println("Tiempo de ejecucion de la consulta: " + tiempo + " ms");
                    break;
                case "2":

                    //consulta1.topPeliculasPorIdioma();
                    break;
                case "3":
                    //consulta1.topPeliculasPorIdioma();
                    break;
                case "4":
                    consulta4.ejecutar();
                    break;
                case "5":
                    consulta5.calcularActorPorMes();
                    break;
                case "6":
                    //consulta1.topPeliculasPorIdioma();
                    break;
                case "7":
                    volver = true;
                    break;
                default:
                    System.out.println("Consulta aún no implementada o inválida.");
            }
        }
    }
}

