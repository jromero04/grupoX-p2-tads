package um.edu.uy.CSV;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.List.MyList;

import java.util.Scanner;

public class NuevoMain {
    public static void main(String[] args) {
        UMovieService servicio = new UMovieService();
        CargaPeliculas carga = new CargaPeliculas();
        CargaCalificaciones cargaCalificaciones = new CargaCalificaciones();
        CargaCreditos cargaCreditos = new CargaCreditos();

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("1. Carga completa de datos");
            System.out.println("2. Mostrar colecciones cargadas");
            System.out.println("3. Mostrar peliculas individuales");
            System.out.println("4. Salir");
            System.out.println("5. Cargar calificaciones");
            System.out.println("6. Ver calificaciones de una película por ID");
            System.out.println("7. Buscar pelicula ID");
            System.out.println("8. Cargar créditos con Jackson");
            System.out.print("Elegi una opcion: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    carga.cargarPeliculas(servicio);
                    cargaCreditos.cargarCreditos(servicio);
                    //cargaCalificaciones.cargarCalificaciones(servicio);
                    System.out.println("Sin cal");
                    break;

                case "2":
                    System.out.println("Colecciones:");
                    MyList<Coleccion> colecciones = servicio.getColeccionesComoLista(); // asumimos que tenés este método
                    for (int i = 0; i < colecciones.size(); i++) {
                        Coleccion c = colecciones.get(i);
                        System.out.println(" - ID: " + c.getIdColeccion());
                        System.out.println("   Nombre: " + c.getTituloColeccion());
                        System.out.println("   Películas: " + c.getPeliculas().size());
                        System.out.println("   Ingresos totales: $" + c.calcularIngresos());
                        System.out.println();
                    }
                    break;

                case "3":
                    System.out.println("Películas:");
                    MyList<Pelicula> peliculas = servicio.getPeliculasComoLista(); // asumimos que tenés este método
                    for (int i = 0; i < peliculas.size(); i++) {
                        Pelicula p = peliculas.get(i);
                        System.out.println(" - ID: " + p.getIdPelicula() + " | Título: " + p.getTituloPelicula());
                        System.out.println("   Idioma: " + p.getIdiomaOriginal());
                        System.out.println("   Ingresos: $" + p.getIngresos());
                        System.out.println("   Colección: " + (p.getColeccion() != null ? p.getColeccion().getTituloColeccion() : "ninguna"));
                        System.out.println("   Elenco: " + p.getElenco());
                        System.out.println();
                    }
                    break;

                case "4":
                    salir = true;
                    break;

                case "5":
                    break;

                case "7":
                    System.out.print("Ingresá el ID de la película que querés buscar en el CSV: ");
                    String idABuscar = scanner.nextLine();
                    carga.buscarPeliculaPorId(idABuscar);
                    break;

                case "8":
                    break;


                default:
                    System.out.println("Opción inválida");
            }
        }
    }
}
