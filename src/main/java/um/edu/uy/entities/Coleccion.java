package um.edu.uy.entities;

import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;

public class Coleccion implements Comparable<Coleccion>{
    private String idColeccion;
    private String tituloColeccion;
    private MyList<String> idPelicula;
    private double ingresosTotales;

    // ver como calculo los ingresos totales luego.

    public Coleccion(String idColeccion, String tituloColeccion) {
        this.idColeccion = idColeccion;
        this.tituloColeccion = tituloColeccion;
        this.idPelicula = new MyLinkedList<>();
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

    public MyList<String> getIdPelicula() {
        return idPelicula;
    }

    public double getIngresosTotales() {
        return ingresosTotales;
    }

    public void setIngresosTotales(double ingresosTotales) {
        this.ingresosTotales = ingresosTotales;
    }

    public void agregarPelicula(String id){
        boolean yaRegistrada = false;
        for (int i = 0; i<idPelicula.size(); i++){
            if (idPelicula.get(i).equals(id)){
                yaRegistrada = true;
                break;
            }
        }
        if (!yaRegistrada){
            this.idPelicula.add(id);
        } else {
            System.out.println("La pelicula con id: " + id + "ya esta registrada en la coleccion (" + tituloColeccion +")");
        }
    }

    // si quisieramos ordenar las colecciones por ingresos
    // paraa el top 5
    @Override
    public int compareTo(Coleccion otra) {
        return Double.compare(otra.ingresosTotales, this.ingresosTotales); // orden descendente
    }

    // recordar manejar en UMovieService que no haya colecciones con mismo id
}
