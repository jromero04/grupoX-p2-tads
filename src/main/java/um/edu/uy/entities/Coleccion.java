package um.edu.uy.entities;

import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;

public class Coleccion implements Comparable<Coleccion>{
    private String idColeccion;
    private String tituloColeccion;
    private MyList<Pelicula> peliculas;
    private double ingresosTotales;

    // ver como calculo los ingresos totales luego.

    public Coleccion(String idColeccion, String tituloColeccion) {
        this.idColeccion = idColeccion;
        this.tituloColeccion = tituloColeccion;
        this.peliculas = new MyLinkedList<>();
        this.ingresosTotales = 0;
    }

    public String getIdColeccion() {
        return idColeccion;
    }

    public void setIdColeccion(String idColeccion) {
        this.idColeccion = idColeccion;
    }

    public String getTituloColeccion() {
        return tituloColeccion;
    }

    public void setTituloColeccion(String tituloColeccion) {
        this.tituloColeccion = tituloColeccion;
    }

    public MyList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(MyList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public double getIngresosTotales() {
        return ingresosTotales;
    }

    public void setIngresosTotales(double ingresosTotales) {
        this.ingresosTotales = ingresosTotales;
    }

    public void agregarPelicula(Pelicula nuevaPelicula){
        boolean yaRegistrada = false;
        for (int i = 0; i<peliculas.size(); i++){
            if (peliculas.get(i).getIdPelicula().equals(nuevaPelicula.getIdPelicula())){
                yaRegistrada = true;
                break;
            }
        }
        if (!yaRegistrada){
            this.peliculas.add(nuevaPelicula);
        }
    }

    // si quisieramos ordenar las colecciones por ingresos
    // paraa el top 5
    @Override
    public int compareTo(Coleccion otra) {
        return Double.compare(otra.ingresosTotales, this.ingresosTotales); // orden descendente
    }


    public double calcularIngresos() {
        double total = 0;
        for (int i =0; i< peliculas.size(); i++) {
            Pelicula pelicula = peliculas.get(i);
            total += pelicula.getIngresos();
        }
        return total;
    }



    // recordar manejar en UMovieService que no haya colecciones con mismo id
}
