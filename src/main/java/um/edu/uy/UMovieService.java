package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.hash.Node;

public class UMovieService {
    private MyHash<String, Pelicula> peliculas;
    private MyHash<String, Usuario> usuarios;
    private MyHash<String, Participante> participantes;
    private MyHash<String, Coleccion> colecciones;

    private MyList<Calificacion> calificaciones;


    public void mejorCalificacion(MyList<Calificacion> calificaciones){

    }

    public void mejorCalificacion(MyHash<String, Pelicula> peliculas) {
        Node<String, Pelicula>[] arreglo = peliculas.getArray();
        MyList<MyLinkedList> pelicu
        for (Node<String, Pelicula> nodo : arreglo) {
            if (nodo != null) {
                Pelicula p = nodo.getValue();
                Double totalPuntaje = 0.0;
                for (int index = 0; index < p.getCalificaciones().size(); index++) {
                    Double puntaje = p.getCalificaciones().get(index).getPuntaje();
                    totalPuntaje += puntaje;
                }
                double cantidadCalificaciones = p.getCantidadDeCalificaciones();
                double clasificacionMedia = totalPuntaje/cantidadCalificaciones;

                System.out.println(p.getTitulo_pelicula());
            }


        }
    }
}

