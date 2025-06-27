package um.edu.uy.AuxiliaresConsulta;


public class ClasificacionesPeliculas implements Comparable<ClasificacionesPeliculas>{
    private String idPelicula;
    private String tituloPelicula;
    private double calificacionPromedio;

    public ClasificacionesPeliculas(String idPelicula, String tituloPelicula, double calificacionMedia){
        this.idPelicula = idPelicula;
        this.tituloPelicula = tituloPelicula;
        this.calificacionPromedio = calificacionMedia;
    }

    public String getIdPelicula() {return idPelicula;}

    public String getTituloPelicula(){return tituloPelicula;}

    public double getCalificacionPromedio(){return calificacionPromedio;}

    @Override
    public int compareTo(ClasificacionesPeliculas peliculaComparada) {
        return Double.compare(this.getCalificacionPromedio(), peliculaComparada.getCalificacionPromedio());
    }
}
