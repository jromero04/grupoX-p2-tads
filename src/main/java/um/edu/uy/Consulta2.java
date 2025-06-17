package um.edu.uy;

// no se si es valido almacenar resultados de calificacion media aca para despues hacer el sorting.
// igual vuelvo a calcular resultados cada vez que hago consulta

public class Consulta2 {
    private String id_pelicula;
    private String titulo_pelicula;
    private double calificacionMedia;

    public Consulta2(String Id_pelicula, String titulo_pelicula, double calificacionMedia){
        this.id_pelicula = id_pelicula;
        this.titulo_pelicula = titulo_pelicula;
        this.calificacionMedia = calificacionMedia;
    }

    public String getId_pelicula() {return id_pelicula;}

    public String getTitulo_pelicula(){return titulo_pelicula;}

    public double getCalificacionMedia(){return calificacionMedia;}

    public void setId_pelicula(String idPelicula) {
        this.id_pelicula = id_pelicula;
    }

    public void setTitulo_pelicula(String titulo_pelicula) {
        this.titulo_pelicula = titulo_pelicula;
    }

    public void setCalificacionMedia(double calificacionMedia) {
        this.calificacionMedia = calificacionMedia;
    }


}
