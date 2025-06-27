package um.edu.uy.entities;

import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;

import java.util.Objects;

public class Participante implements Comparable<Participante> {
    private String nombreParticipante;
    private String rol;
    private MyHash<String, Pelicula> peliculas;
    private double valorComparacion;
    private int cantidadPeliculas;

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

    public MyHash<String, Pelicula> getPeliculas() {
        return peliculas;
    }

    public void agregarPelicula(Pelicula pelicula) {
        if (!peliculas.contains(pelicula.getIdPelicula())) {
            peliculas.add(pelicula.getIdPelicula(),pelicula );
        }
    }

    public double getValorComparacion() {
        return valorComparacion;
    }

    public void setValorComparacion(double valorComparacion) {
        this.valorComparacion = valorComparacion;
    }

    public int getCantidadPeliculas() {
        return cantidadPeliculas;
    }

    public void setCantidadPeliculas(int cantidadPeliculas) {
        this.cantidadPeliculas = cantidadPeliculas;
    }

    @Override
    public int compareTo(Participante otro) {
        return Double.compare(this.valorComparacion, otro.valorComparacion);
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
