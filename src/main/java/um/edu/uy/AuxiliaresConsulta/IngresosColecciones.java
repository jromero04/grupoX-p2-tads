package um.edu.uy.AuxiliaresConsulta;

import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.hash.Node;
import um.edu.uy.tads.heap.ArrayHeap;

public class IngresosColecciones implements Comparable<IngresosColecciones>{
    private String idColeccion;
    private String tituloColeccion;
    private int cantidadPeliculas;
    private MyList<String> idPeliculas;
    private double ingresosTotales;


    public IngresosColecciones(String idColeccion, String tituloColeccion, double ingresosTotales) {
        this.idColeccion = idColeccion;
        this.tituloColeccion = tituloColeccion;
        this.cantidadPeliculas = 0;
        this.idPeliculas = new MyLinkedList<>();
        this.ingresosTotales = ingresosTotales;
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


    public int compareTo(IngresosColecciones ingresosComparados) {
        return Double.compare(this.getIngresosTotales(), ingresosComparados.getIngresosTotales());
    }

}