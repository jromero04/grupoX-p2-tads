package um.edu.uy;

import um.edu.uy.entities.Pelicula;
import um.edu.uy.UMovieService;
import um.edu.uy.tads.hash.MyHash;

public class Main {
    public static void main(String[] args) {
        UMovieService servicio = new UMovieService();
        CargaCSV lector = new CargaCSV();

        lector.cargarPeliculas(servicio);
        servicio.topPeliculasPorIdioma();
    }
}