package um.edu.uy.entities;

import java.time.LocalDateTime;

public class Calificacion {
    private String idUsuario;
    private String idPelicula;
    private double puntaje;
    private LocalDateTime fecha; // ver que formato

    public Calificacion(String idUsuario, String idPelicula, double puntaje, LocalDateTime fecha) {
        this.idUsuario = idUsuario;
        this.idPelicula = idPelicula;
        this.puntaje = puntaje;
        this.fecha = fecha;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getIdPelicula() {
        return idPelicula;
    }

    public double getPuntaje() {
        return puntaje;
    }

    // chequeo por mes?

    public int getMes() {
        return fecha.getMonthValue();
    }

    public String getMesNombre(){
        return fecha.getMonth().name();
    }
}
