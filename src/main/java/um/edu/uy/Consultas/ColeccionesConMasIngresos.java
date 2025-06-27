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

    public void ingresosSaga () {
        long inicio = System.currentTimeMillis();

        Node<String, Coleccion>[] arregloColecciones = servicio.getColecciones().getArray();
        ArrayHeap<IngresosColecciones> heapIngresosSaga = new ArrayHeap<>(1000, true);

        for (Node<String, Coleccion> nodoColeccion : arregloColecciones) {
            if (nodoColeccion != null) {
                Coleccion coleccionEnArreglo = nodoColeccion.getValue();
                IngresosColecciones ingresoColeccion = new IngresosColecciones(coleccionEnArreglo.getIdColeccion(), coleccionEnArreglo.getTituloColeccion(), coleccionEnArreglo.calcularIngresos());
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
                coleccion = servicio.getColecciones().search(idColeccion);
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
            System.out.println("Id colección: " + ingresosTop.get(coleccionIngreso).getIdColeccion() + ", Título colección: " + ingresosTop.get(coleccionIngreso).getTituloColeccion() + ", Cantidad de películas: " + ingresosTop.get(coleccionIngreso).getCantidadPeliculas() + ", Ids películas: " + ingresosTop.get(coleccionIngreso).getIdPeliculas() + ", Ingresos Generados: " + ingresosTop.get(coleccionIngreso).getIngresosTotales());
        }

        long fin = System.currentTimeMillis(); // Fin del tiempo de ejecución
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");

    }


}
