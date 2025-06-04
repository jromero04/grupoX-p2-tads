package um.edu.uy.entities;

public class Calificacion {
    private String idUsuario;
    private String idPelicula;
    private double puntaje;
    private String fecha; // ver que formato

    public Calificacion(String idUsuario, String idPelicula, double puntaje, String fecha) {
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

    public String getFecha() {
        return fecha;
    }

    // chequeo por mes?
}
