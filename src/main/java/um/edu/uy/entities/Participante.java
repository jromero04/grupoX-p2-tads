package um.edu.uy.entities;

import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;

import java.util.Objects;

public class Participante {
    private String nombreParticipante;
    private String rol;
    private MyHash<String, Pelicula> peliculas;

    public Participante(String nombreParticipante, String rol) {
        this.nombreParticipante = nombreParticipante;
        this.rol = rol;
        this.peliculas = new Hash<>();
    }

    public String getNombreParticipante() {
        return nombreParticipante;
    }

    public String getRol() {
        return rol;
    }

    public MyHash<String, Pelicula> getPeliculas() {
        return peliculas;
    }

    public void agregarPelicula(Pelicula pelicula) {
        if (!peliculas.contains(pelicula.getIdPelicula())) {
            peliculas.add(pelicula.getIdPelicula(),pelicula );
        }
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Participante that)) return false;
        return Objects.equals(nombreParticipante, that.nombreParticipante) && Objects.equals(rol, that.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreParticipante, rol);
    }

    @Override
    public String toString() {
        return nombreParticipante + " (" + rol + ")";
    }

}
