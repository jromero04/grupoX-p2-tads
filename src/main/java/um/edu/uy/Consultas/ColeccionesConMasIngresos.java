package um.edu.uy.Consultas;

import um.edu.uy.AuxiliaresConsulta.IngresosColecciones;
import um.edu.uy.UMovieService;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.hash.Node;
import um.edu.uy.tads.heap.ArrayHeap;

public class ColeccionesConMasIngresos {

    private UMovieService servicio;

    public ColeccionesConMasIngresos(UMovieService servicio){
        this.servicio = servicio;
    }

    public void ingresosSaga (MyHash<String, Coleccion> colecciones) {
        Node<String, Coleccion>[] arregloColecciones = colecciones.getArray();
        ArrayHeap<IngresosColecciones> heapIngresosSaga = new ArrayHeap<>(1000, true);

        for (Node<String, Coleccion> nodoColeccion : arregloColecciones) {
            if (nodoColeccion != null) {
                Coleccion coleccionEnArreglo = nodoColeccion.getValue();
                IngresosColecciones ingresoColeccion = new IngresosColecciones(coleccionEnArreglo.getIdColeccion(), coleccionEnArreglo.getTituloColeccion(), coleccionEnArreglo.getIngresosTotales());
                heapIngresosSaga.insert(ingresoColeccion);
            }
        }

        ArrayHeap<IngresosColecciones> ingresosTop = new ArrayHeap<>(1000, true);

        for (int elementoHeap = 0; elementoHeap < 5 && heapIngresosSaga.size() > 0; elementoHeap++) {
            IngresosColecciones coleccionTop = heapIngresosSaga.delete();
            ingresosTop.insert(coleccionTop);
        }

        for (int elementoHeap = 1; elementoHeap <= ingresosTop.size(); elementoHeap++) {
            IngresosColecciones coleccionTop = ingresosTop.get(elementoHeap);
            String idColeccion = coleccionTop.getIdColeccion();

            Coleccion coleccion = null;

            try {
                coleccion = colecciones.search(idColeccion);
            } catch (InvalidHashKey e) {
            }
            if (coleccion != null) {

                MyList<String> idsPeliculas = new MyLinkedList<>();

                MyList<Pelicula> peliculas = coleccion.getPeliculas();
                for (int peliculaEnLista = 0; peliculaEnLista < peliculas.size(); peliculaEnLista++) {
                    Pelicula pelicula = peliculas.get(peliculaEnLista);
                    idsPeliculas.add(pelicula.getIdPelicula());
                }

                coleccionTop.setIdPeliculas(idsPeliculas);
                coleccionTop.setCantidadPeliculas(idsPeliculas.size());
            }

        }
        for (int coleccionIngreso = 1; coleccionIngreso <= ingresosTop.size(); coleccionIngreso++) {
            System.out.println("Id colección: " + ingresosTop.get(coleccionIngreso).getIdColeccion() + ", Título colección: " + ingresosTop.get(coleccionIngreso).getTituloColeccion() + ", Cantidad de películas: " + ingresosTop.get(coleccionIngreso).getCantidadPeliculas() + ", Ids películas: " + ingresosTop.get(coleccionIngreso).getIdPeliculas() + "Ingresos Generados: " + ingresosTop.get(coleccionIngreso).getIngresosTotales());
        }
    }


}
