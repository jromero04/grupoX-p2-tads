package um.edu.uy.entities;

import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;

import java.util.Objects;

public class Participante {
    private String nombreParticipante;
    /* los roles se encuentran en CSV credits, lista enorme de cast
    lista de crew donde voy a encontrar la director y al final el id de la pelicula.
    */
    private String rol;
    private MyHash<Pelicula, Boolean> peliculas;

    // ver si no agregar fecha por mes del anio para consultas

    public Participante(String nombreParticipante, String rol) {
        this.nombreParticipante = nombreParticipante;
        this.rol = rol;
        this.peliculas = new Hash<>();
    }

    public String getNombreParticipante() {
        return nombreParticipante;
    }

    public void setNombreParticipante(String nombreParticipante) {
        this.nombreParticipante = nombreParticipante;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public MyHash<Pelicula, Boolean> getPeliculas() {
        return peliculas;
    }

    // evito que se agreguen mas de una vez el idPelicula
    // que pasa si un participante aparece como director y actor en una misma pelicula??
    public void agregarPelicula(Pelicula pelicula) {
        if (!peliculas.contains(pelicula)) {
            peliculas.add(pelicula, true);
        }
    }

    /* hay participantes que pueden ser actores y directores
    al crear la instancia los diferencio
    dos participantes son los mismos si comparten nombre y rol
    Evitamos que al contar los trabajos de X actor y X director se mezclen entre si
    */

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
