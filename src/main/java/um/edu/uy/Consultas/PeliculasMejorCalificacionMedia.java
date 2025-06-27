package um.edu.uy.Consultas;

import um.edu.uy.AuxiliaresConsulta.ClasificacionesPeliculas;
import um.edu.uy.UMovieService;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.hash.Node;
import um.edu.uy.tads.heap.ArrayHeap;

public class PeliculasMejorCalificacionMedia {

    private UMovieService servicio;

    public PeliculasMejorCalificacionMedia(UMovieService servicio){
        this.servicio = servicio;
    }

    public void mejorCalificacion(MyHash<String, Pelicula> peliculas) {
        long inicio = System.currentTimeMillis(); // Inicio del tiempo de ejecución

        Node<String, Pelicula>[] arregloPeliculas = peliculas.getArray();
        ArrayHeap<ClasificacionesPeliculas> heapPeliculasClasificacion = new ArrayHeap<>(1000, true);
        for (Node<String, Pelicula> nodoPelicula : arregloPeliculas) {
            if (nodoPelicula != null) {
                if (nodoPelicula.getValue().getCantidadDeCalificaciones() > 100){
                    Pelicula pelicula = nodoPelicula.getValue();
                    ClasificacionesPeliculas clasificacionPelicula = new ClasificacionesPeliculas(pelicula.getIdPelicula(), pelicula.getTituloPelicula(), pelicula.getPromedioCalificaciones());
                    heapPeliculasClasificacion.insert(clasificacionPelicula);
                }
            }
        }
        for (int elementoHeap = 0; elementoHeap < 10 && heapPeliculasClasificacion.size() > 0; elementoHeap++) {
            ClasificacionesPeliculas peliculaTop = heapPeliculasClasificacion.delete();
            System.out.println(peliculaTop.getIdPelicula() + ", " + peliculaTop.getTituloPelicula() + ", " + peliculaTop.getCalificacionPromedio());
        }

        long fin = System.currentTimeMillis(); // Fin del tiempo de ejecución
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");

    }
}
