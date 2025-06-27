package um.edu.uy.Consultas;

//metodo que vacie heap con peliculas luego de calcular todo, fijate si esta en tad

//cambiar nombre de consulta2


public class Top10PeliculasMejorCalificacion implements Comparable<Top10PeliculasMejorCalificacion>{
    private String idPelicula;
    private String tituloPelicula;
    private double calificacionPromedio;

    public Top10PeliculasMejorCalificacion(String IdPelicula, String tituloPelicula, double calificacionMedia){
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
    public int compareTo(Top10PeliculasMejorCalificacion otra) {
        return Double.compare(this.getCalificacionPromedio(), otra.getCalificacionPromedio());
    }
}
