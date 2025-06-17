package um.edu.uy;

import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;

public class Consulta3 {
    private String idColeccion;
    private String tituloColeccion;
    private int cantidadPeliculas;
    private MyList<String> idPeliculas;
    private double ingresosTotales;

    // ver como calculo los ingresos totales luego.

    public Consulta3(String idColeccion, String tituloColeccion, int cantidadPeliculas) {
        this.idColeccion = idColeccion;
        this.tituloColeccion = tituloColeccion;
        this.cantidadPeliculas = cantidadPeliculas;
        this.idPeliculas = new MyLinkedList<>();
        this.ingresosTotales = 0;                         //ver si calcularlo aca o en carga csv
    }
}
