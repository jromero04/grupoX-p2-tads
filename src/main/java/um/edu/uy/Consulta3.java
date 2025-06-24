package um.edu.uy;

import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.hash.Node;
import um.edu.uy.tads.heap.ArrayHeap;

//esta clase solo tiene idcoleccion e ingresos totales porque primero obtengo el top 5 y despues recorro la lista peliculas

public class Consulta3 implements Comparable<Consulta3>{
    private String idColeccion;
    private String tituloColeccion;
    private int cantidadPeliculas;
    private MyList<String> idPeliculas;
    private double ingresosTotales;


    public Consulta3(String idColeccion, String tituloColeccion, double ingresosTotales) {
        this.idColeccion = idColeccion;
        this.tituloColeccion = tituloColeccion;
        this.cantidadPeliculas = 0;
        this.idPeliculas = new MyLinkedList<>();
        this.ingresosTotales = ingresosTotales;                         //ver si calcularlo aca o en carga csv
    }


    public String getIdColeccion(){return idColeccion;}

    public String getTituloColeccion(){return tituloColeccion;}

    public int getCantidadPeliculas(){return cantidadPeliculas;}

    public MyList<String> getIdPeliculas(){return idPeliculas;}

    public double getIngresosTotales(){return ingresosTotales;}




    public void setCantidadPeliculas(int cantidadPeliculas) {
        this.cantidadPeliculas = cantidadPeliculas;
    }

    public void setIdPeliculas(MyList<String> idPeliculas) {
        this.idPeliculas = idPeliculas;
    }

    public void setIngresosTotales(double ingresosTotales) {
        this.ingresosTotales = ingresosTotales;
    }



    public int compareTo(Consulta3 otra) {
        return Double.compare(this.getIngresosTotales(), otra.getIngresosTotales());
}

//fijate si hace falta mas setter y getters y si cambio nombre "otra" en compare

//primero ordenar el heap original de coleccion y despues sacar los primeros 5



}}