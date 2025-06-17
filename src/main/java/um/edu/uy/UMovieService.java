package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
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
                System.out.println(p.getIdPelicula() + ", " + p.getTituloPelicula() + ", " + p.getCantidadDeCalificaciones() + ", " + p.getIdiomaOriginal());
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

                System.out.println(p.getTituloPelicula());
            }


        }
    }

    public void mejorCalificacion(MyHash<String, Pelicula> peliculas) {

        List<Consulta2> promedioAuxiliar = new ArrayList<>();   //fijate si usar heap para evitar el sorting

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

                    Consulta2 promedios = new Consulta2(p.getIdPelicula(), p.getTituloPelicula(), clasificacionMedia);
                }

                //falta hacer el sorting
            }


        }
    }

    public void top10Directores() throws InvalidHashKey {
        MyHash<String, MyLinkedList<Double>> calificacionesPorDirector = new Hash<>(1000);
        MyHash<String, Integer> peliculasPorDirector = new Hash<>(1000);

        Node<String, Pelicula>[] nodosPeliculas = peliculas.getArray();

        for (Node<String, Pelicula> nodo : nodosPeliculas) {
            if (nodo != null) {
                Pelicula peli = nodo.getValue();
                Participante director = peli.getDirectores().search();
                if (director == null) continue;

                String nombreDirector = director.getNombre_participante();

//                // DEBUG: mostrar datos de un director puntual
//                if (nombreDirector.equals("Nora Ephron")) {
//                    System.out.println("Película de Nora Ephron: " + peli.getTitulo_pelicula());
//                    System.out.println("   Calificaciones:");
//                    for (int i = 0; i < peli.getCalificaciones().size(); i++) {
//                        Calificacion c = peli.getCalificaciones().get(i);
//                        System.out.print(c.getPuntaje() + " ");
//                    }
//                    System.out.println("\n---");
//                }

                MyList<Calificacion> calificaciones = peli.getCalificaciones();
                if (calificaciones == null || calificaciones.size() == 0) continue;

                if (!calificacionesPorDirector.contains(nombreDirector)) {
                    calificacionesPorDirector.add(nombreDirector, new MyLinkedList<>());
                    peliculasPorDirector.add(nombreDirector, 0);
                }

                for (int i = 0; i < calificaciones.size(); i++) {
                    Calificacion calificacion = calificaciones.get(i);
                    calificacionesPorDirector.search(nombreDirector).add(calificacion.getPuntaje());
                }

                int cantPeliculas = peliculasPorDirector.search(nombreDirector);
                peliculasPorDirector.add(nombreDirector, cantPeliculas + 1);
            }
        }

        ArrayHeap<InfoDirector> heap = new ArrayHeap<>(1000, true); // heap máximo

        for (Node<String, MyLinkedList<Double>> nodo : calificacionesPorDirector.getArray()) {
            if (nodo != null) {
                String director = nodo.getKey();
                MyLinkedList<Double> califs = nodo.getValue();int totalCalificaciones = califs.size();
            int cantPeliculas = peliculasPorDirector.search(director);

            if (totalCalificaciones > 100 && cantPeliculas > 1) {
                double mediana = calcularMediana(califs);
                heap.insert(new InfoDirector(director, cantPeliculas, mediana));
            }
        }
        }

        int i = 0;
        while (heap.size() > 0 && i < 10) {
            InfoDirector d = heap.delete();
            System.out.println(d.getNombre() + "," + d.getCantidadPeliculas() + "," + d.getMediana());
            i++;
        }

    }

    private double calcularMediana(MyLinkedList<Double> lista) {
        int n = lista.size();
        double[] arr = new double[n];
        for (int i = 0; i < lista.size(); i++) {
            arr[i] = lista.get(i);
        }

        ordenarArray(arr);

        if (n % 2 == 0) {
            return (arr[n/2 - 1] + arr[n/2]) / 2.0;
        } else {
            return arr[n/2];
        }
    }

    private void ordenarArray(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    double temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

}

