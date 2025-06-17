package um.edu.uy;

// no se si es valido almacenar resultados de calificacion media aca para despues hacer el sorting.
// igual vuelvo a calcular resultados cada vez que hago consulta

public class Consulta2 {
    private String idPelicula;
    private String tituloPelicula;
    private double calificacionMedia;

    public Consulta2(String IdPelicula, String tituloPelicula, double calificacionMedia){
        this.idPelicula = idPelicula;
        this.tituloPelicula = tituloPelicula;
        this.calificacionMedia = calificacionMedia;
    }

    public String getIdPelicula() {return idPelicula;}

    public String getTituloPelicula(){return tituloPelicula;}

    public double getCalificacionMedia(){return calificacionMedia;}

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public void setTituloPelicula(String tituloPelicula) {
        this.tituloPelicula = tituloPelicula;
    }

    public void setCalificacionMedia(double calificacionMedia) {
        this.calificacionMedia = calificacionMedia;
    }


}
