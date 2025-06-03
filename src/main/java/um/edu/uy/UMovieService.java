package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.MyHash;

public class UMovieService {
    private MyHash<String, Pelicula> peliculas;
    private MyHash<String, Usuario> usuarios;
    private MyHash<String, Participante> participantes;
    private MyHash<String, Coleccion> colecciones;

    private MyList<Calificacion> calificaciones;
}
