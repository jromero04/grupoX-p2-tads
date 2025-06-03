package um.edu.uy.entities;

public class Calificacion {
    private String idPelicula;
    private double puntaje;
    private String fecha; // puede ser en formato "YYYY-MM-DD"

    public Calificacion(String idPelicula, double puntaje, String fecha) {
        this.idPelicula = idPelicula;
        this.puntaje = puntaje;
        this.fecha = fecha;
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
