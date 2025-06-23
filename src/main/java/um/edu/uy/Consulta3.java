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

public void ingresosSaga (MyHash<String, Coleccion> colecciones) {
    Node<String, Coleccion>[] arreglo = colecciones.getArray();
    ArrayHeap<Consulta3> ingresosSagaAuxiliar = new ArrayHeap<>(arreglo.length, true); //use length arreglo como capacidad del heap para que no sea fijo y dependa de la cant de datos
    for (Node<String, Coleccion> nodo : arreglo) {
        if (nodo != null) {
            Coleccion c = nodo.getValue();
            Consulta3 consultaColeccion = new Consulta3(c.getIdColeccion(), c.getTituloColeccion(), c.getIngresosTotales());
            ingresosSagaAuxiliar.insert(consultaColeccion);
        }
    }

    //hice este heap nuevo porque queria que solo mantenga el top5 (no encontre otra forma de borrar las que no quiero sin borrar el top
    ArrayHeap<Consulta3> ingresosTop = new ArrayHeap<>(arreglo.length, true); //use length arreglo como capacidad del heap para que no sea fijo y dependa de la cant de datos

    for (int i = 0; i < 5 && ingresosSagaAuxiliar.size() > 0; i++) {
        Consulta3 c = ingresosSagaAuxiliar.delete();
        ingresosTop.insert(c);
        //este c cambiar el nombre (esta mal?)
    }
    //en este punto tengo el heap ingresostop con las 5 colecciones top

    //ahora quiero recorrer este heap para hallar el idpeliculas

    for (int i = 1; i <= ingresosTop.size(); i++) {
        Consulta3 c = ingresosTop.get(i);     //cree un metodo get en heap
        String idColeccion = c.getIdColeccion();

        Coleccion coleccion = null;          //sin esta linea no me reconoce el coleccion mas abajo

        try {
            coleccion = colecciones.search(idColeccion);
        } catch (InvalidHashKey e) {
            System.out.println("ID de colección no encontrado: " + idColeccion);  //este print no se si esta bien
        }
        if (coleccion != null){

            MyList<String> idsPeliculas = new MyLinkedList<>(); // lista vacía para meter los id

            MyList<Pelicula> peliculas = coleccion.getPeliculas();
            for (int iteracion = 0; iteracion < peliculas.size(); i++) {         //cambiar nombre iteracion
                Pelicula p = peliculas.get(iteracion);
                idsPeliculas.add(p.getIdPelicula());
            }

        c.setIdPeliculas(idsPeliculas);
        c.setCantidadPeliculas(idsPeliculas.size());


        }

    }

}}