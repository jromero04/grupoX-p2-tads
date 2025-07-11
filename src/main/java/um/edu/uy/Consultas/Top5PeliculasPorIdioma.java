package um.edu.uy.Consultas;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.hash.Node;
import um.edu.uy.tads.heap.ArrayHeap;


public class Top5PeliculasPorIdioma {

    private UMovieService servicio;

    public Top5PeliculasPorIdioma(UMovieService servicio){
        this.servicio = servicio;
    }

    public void topPeliculasPorIdioma() {

        long inicio = System.currentTimeMillis();

        String[] idiomas = {"en", "fr", "it", "es", "pt"};

        for (String idioma : idiomas) {
            ArrayHeap<Pelicula> heap = construirHeapPorIdioma(idioma); // MaxHeap

            System.out.println("Top 5 para idioma: " + idioma);
            for (int i = 0; i < 5 && heap.size() > 0; i++) {
                Pelicula p = heap.delete();
                System.out.println(p.getIdPelicula() + ", " + p.getTituloPelicula() + ", " + p.getCantidadDeCalificaciones() + ", " + p.getIdiomaOriginal());
            }
            System.out.println();
        }

        long fin = System.currentTimeMillis(); // Fin del tiempo de ejecución
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
    }

    private ArrayHeap<Pelicula> construirHeapPorIdioma(String idioma) {
        ArrayHeap<Pelicula> heap = new ArrayHeap<>(true); // MaxHeap

        Node<String, Pelicula>[] arreglo = servicio.getPeliculas().getArray();
        for (Node<String, Pelicula> nodo : arreglo) {
            if (nodo != null) {
                Pelicula p = nodo.getValue();
                if (p.getIdiomaOriginal() != null && p.getIdiomaOriginal().equals(idioma) && p.getCantidadDeCalificaciones() > 0) {
                    heap.insert(p);
                }
            }
        }
        return heap;
    }

    public MyHash<String, MyList<Pelicula>> obtenerTopPeliculasPorIdioma() {
        MyHash<String, MyList<Pelicula>> resultado = new Hash<>();
        String[] idiomas = {"en", "fr", "it", "es", "pt"};

        for (String idioma : idiomas) {
            ArrayHeap<Pelicula> heap = construirHeapPorIdioma(idioma);
            MyList<Pelicula> top5 = new MyLinkedList<>();

            for (int i = 0; i < 5 && !heap.isEmpty(); i++) {
                top5.add(heap.delete());
            }

            resultado.add(idioma, top5);
        }

        return resultado;
    }

}
