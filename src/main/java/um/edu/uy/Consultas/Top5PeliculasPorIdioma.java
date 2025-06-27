package um.edu.uy.Consultas;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.hash.Node;
import um.edu.uy.tads.heap.ArrayHeap;

// funciona en main
public class Top5PeliculasPorIdioma {

    private UMovieService servicio;

    public Top5PeliculasPorIdioma(UMovieService servicio){
        this.servicio = servicio;
    }

    public void topPeliculasPorIdioma() {
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
    }

    private ArrayHeap<Pelicula> construirHeapPorIdioma(String idioma) {
        ArrayHeap<Pelicula> heap = new ArrayHeap<>(true); // MaxHeap

        Node<String, Pelicula>[] arreglo = servicio.getPeliculas().getArray();
        int peliculasFiltradas =0;
        for (Node<String, Pelicula> nodo : arreglo) {
            if (nodo != null) {
                Pelicula p = nodo.getValue();
                if (p.getIdiomaOriginal() != null && p.getIdiomaOriginal().equals(idioma) && p.getCantidadDeCalificaciones() > 0) {
                    heap.insert(p);
                    peliculasFiltradas++;
                }
            }
        }

        // Depuración: imprimir cantidad de películas filtradas
        System.out.println("Películas filtradas para idioma '" + idioma + "': " + peliculasFiltradas);

        return heap;
    }

}
