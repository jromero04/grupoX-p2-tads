package um.edu.uy.entities;

import java.time.LocalDateTime;

public class Calificacion {
    private Usuario usuario;
    private Pelicula pelicula;
    private double puntaje;
    private LocalDateTime fecha; // ver que formato

    public Calificacion(Usuario usuario, Pelicula pelicula, double puntaje, LocalDateTime fecha) {
        this.usuario = usuario;
        this.pelicula = pelicula;
        this.puntaje = puntaje;
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public double getPuntaje() {
        return puntaje;
    }


    public int getMes() {
        return fecha.getMonthValue();
    }

}
