package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.hash.Node;

public class UMovieService2 {
    private MyHash<String, Pelicula> peliculas;
    private MyHash<String, Usuario> usuarios;
    private MyHash<String, Participante> participantes;
    private MyHash<String, Coleccion> colecciones;

    private MyList<Calificacion> calificaciones;

    public void topPeliculasPorIdioma() {
        String [] idiomas = {"en", "fr", "it", "es", "pt"};

        for (String idioma : idiomas){
            MyList<Pelicula> pPorIdiomas = new MyLinkedList<>();

            // Recorremos todas las películas del hash
            Node<String, Pelicula>[] arreglo = peliculas.getArray();
            for (Node<String, Pelicula> nodo : arreglo) {
                if (nodo != null) {
                    Pelicula p = nodo.getValue();
                    System.out.println(p.getTituloPelicula());
                }
            }

        }
    }

    private static void ordenarPorCalificaciones(MyList<Pelicula> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                Pelicula p1 = lista.get(j);
                Pelicula p2 = lista.get(j + 1);
                if (p1.getCantidadDeCalificaciones() < p2.getCantidadDeCalificaciones()) {
                    // swap
                    lista.remove(j + 1);
                    lista.remove(j);
                    lista.add(j, p2);
                    lista.add(j + 1, p1);

                }
            }
        }
    }
}
