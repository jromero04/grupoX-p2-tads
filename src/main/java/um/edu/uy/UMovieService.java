package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.hash.Node;
import um.edu.uy.tads.heap.ArrayHeap;

import java.util.ArrayList;
import java.util.List;

public class UMovieService {
    private MyHash<String, Pelicula> peliculas  = new Hash<>(100);
    private MyHash<String, Usuario> usuarios = new Hash<>(100);
    private MyHash<String, Participante> participantes = new Hash<>(100);
    private MyHash<String, Coleccion> colecciones = new Hash<>(100);

    private MyList<Calificacion> calificaciones = new MyLinkedList<>();


    public MyHash<String, Pelicula> getPeliculas() {
        return peliculas;
    }

    public MyHash<String, Coleccion> getColecciones() {
        return colecciones;
    }

    public MyHash<String, Participante> getParticipantes() {
        return participantes;
    }

    public MyHash<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public MyList<Calificacion> getCalificaciones() {
        return calificaciones;
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
                        // Verificar si la película tiene calificaciones
                        if (p.getCantidadDeCalificaciones() > 0) {
                            heap.insert(p);
                        }
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
/*
    private static void ordenarPorCalificaciones(MyList<Pelicula> lista) {
        ArrayHeap<Pelicula> heap = new ArrayHeap<>(lista.size() + 1, true); // true = MaxHeap

        for (int i = 0; i < lista.size(); i++) {
            heap.insert(lista.get(i));
        }

        // Limpiamos lista original
        while (lista.size() > 0) {
            lista.remove(0);
        }

        // Recuperamos elementos en orden descendente
        while (heap.size() > 0) {
            lista.add(heap.delete());
        }
    }
    */


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

    public void LLmejorCalificacion(MyHash<String, Pelicula> peliculas) {
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

    public void mejorCalificacion(MyHash<String, Pelicula> peliculas) {

        List<Consulta2> promedioAuxiliar = new ArrayList<>();

        Node<String, Pelicula>[] arreglo = peliculas.getArray();
        for (Node<String, Pelicula> nodo : arreglo) {
            if (nodo != null) {
                Pelicula p = nodo.getValue();
                Double totalPuntaje = 0.0;
                for (int index = 0; index < p.getCalificaciones().size(); index++) {
                    Double puntaje = p.getCalificaciones().get(index).getPuntaje();
                    totalPuntaje += puntaje;
                    double cantidadCalificaciones = p.getCantidadDeCalificaciones();
                    double clasificacionMedia = totalPuntaje/cantidadCalificaciones;

                    Consulta2 promedios = new Consulta2(p.getId_pelicula(), p.getTitulo_pelicula(), clasificacionMedia);
                }
            }


        }
    }
}

