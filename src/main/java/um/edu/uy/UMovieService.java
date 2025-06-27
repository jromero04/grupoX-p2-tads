package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;

public class UMovieService {
    private MyHash<String, Pelicula> peliculas = new Hash<>();
    private MyHash<String, Usuario> usuarios = new Hash<>();
    private MyHash<String, Participante> participantes = new Hash<>();
    private MyHash<String, Coleccion> colecciones = new Hash<>();

    private MyList<Calificacion> calificaciones = new MyLinkedList<>();


    public MyHash<String, Pelicula> getPeliculas() {
        return peliculas;
    }

    public MyHash<String, Coleccion> getColecciones() {
        return colecciones;
    }

    public MyHash<String, Participante> getParticipantes() {
        return participantes;
    }

    public MyHash<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public MyList<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void agregarColeccion(Coleccion c) {
        try {
            colecciones.add(c.getIdColeccion(), c);
        } catch (Exception e) {
            System.out.println("Error al agregar coleccion: " + e.getMessage());
        }
    }

    public Coleccion buscarColeccion(String idColeccion) {
        try {
            return colecciones.search(idColeccion);
        } catch (InvalidHashKey e) {
            return null;
        }
    }

    public void agregarPelicula(Pelicula p) {
        peliculas.add(p.getIdPelicula(), p);
    }

    public Participante obtenerParticipante(String nombre, String rol) throws InvalidHashKey {
        String clave = nombre + "-" + rol;
        if (!participantes.contains(clave)) {
            Participante nuevo = new Participante(nombre, rol);
            participantes.add(clave, nuevo);
        }
        return participantes.search(clave);
    }




}


