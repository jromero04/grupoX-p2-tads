package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.hash.Node;
import um.edu.uy.tads.heap.ArrayHeap;

public class UMovieService {
    private MyHash<String, Pelicula> peliculas;
    private MyHash<String, Usuario> usuarios;
    private MyHash<String, Participante> participantes;
    private MyHash<String, Coleccion> colecciones;

    private MyList<Calificacion> calificaciones;


    public UMovieService(){
        this.peliculas = new Hash<>(100);
        this.usuarios = new Hash<>(100);
        this.participantes = new Hash<>(100);
        this.colecciones = new Hash<>(100);
        this.calificaciones = new MyLinkedList<>();

    }

    public MyHash<String, Pelicula> getPeliculas() {
        return peliculas;
    }



    public void topPeliculasPorIdioma() {
        String [] idiomas = {"en", "fr", "it", "es", "pt"};

        for (String idioma : idiomas) {
            ArrayHeap<Pelicula> heap = new ArrayHeap<>(1000, true); // MaxHeap

            Node<String, Pelicula>[] arreglo = peliculas.getArray();
            for (Node<String, Pelicula> nodo : arreglo) {
                if (nodo != null) {
                    Pelicula p = nodo.getValue();
                    if (p.getIdiomaOriginal().equals(idioma)) {
                        heap.insert(p);
                    }
                }
            }


            System.out.println("Top 5 para idioma: " + idioma);
            for (int i = 0; i < 5 && heap.size() > 0; i++) {
                Pelicula p = heap.delete();
                System.out.println(p.getId_pelicula() + ", " + p.getTitulo_pelicula() + ", " + p.getCantidadDeCalificaciones() + ", " + p.getIdiomaOriginal());
            }
            System.out.println();
        }


    }

    /*private static void ordenarPorCalificaciones(MyList<Pelicula> lista) {
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
    }*/


    public void mejorCalificacion(MyList<Calificacion> calificaciones){

    }

    public void mejorCalificacion(MyHash<String, Pelicula> peliculas) {
        Node<String, Pelicula>[] arreglo = peliculas.getArray();
        MyList<MyLinkedList> pelicu;
        for (Node<String, Pelicula> nodo : arreglo) {
            if (nodo != null) {
                Pelicula p = nodo.getValue();
                Double totalPuntaje = 0.0;
                for (int index = 0; index < p.getCalificaciones().size(); index++) {
                    Double puntaje = p.getCalificaciones().get(index).getPuntaje();
                    totalPuntaje += puntaje;
                }
                double cantidadCalificaciones = p.getCantidadDeCalificaciones();
                double clasificacionMedia = totalPuntaje/cantidadCalificaciones;

                System.out.println(p.getTitulo_pelicula());
            }


        }
    }
}

