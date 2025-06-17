package um.edu.uy;

//metodo que vacie heap con peliculas luego de calcular todo, fijate si esta en tad

//cambiar nombre de consulta2

import um.edu.uy.entities.Pelicula;




public class Consulta2 implements Comparable<Consulta2>{
    private String idPelicula;
    private String tituloPelicula;
    private double calificacionPromedio;

    public Consulta2(String IdPelicula, String tituloPelicula, double calificacionMedia){
        this.idPelicula = idPelicula;
        this.tituloPelicula = tituloPelicula;
        this.calificacionPromedio = calificacionMedia;
    }

    public String getIdPelicula() {return idPelicula;}

    public String getTituloPelicula(){return tituloPelicula;}

    public double getCalificacionPromedio(){return calificacionPromedio;}

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public void setTituloPelicula(String tituloPelicula) {
        this.tituloPelicula = tituloPelicula;
    }

    public void setCalificacionPromedio(double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    @Override
    public int compareTo(Consulta2 otra) {
        return Double.compare(this.getCalificacionPromedio(), otra.getCalificacionPromedio());
    }
}
