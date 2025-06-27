package um.edu.uy.Consultas;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Calificacion;
import um.edu.uy.entities.Participante;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.hash.Node;
import um.edu.uy.tads.heap.ArrayHeap;

import java.util.Arrays;

// chequeado en main

public class Top10DirectoresConMejorCalificacion {

    private UMovieService servicio;

    public Top10DirectoresConMejorCalificacion(UMovieService servicio) {
        this.servicio = servicio;
    }

    public void ejecutar() throws InvalidHashKey {
        ArrayHeap<Participante> heap = new ArrayHeap<>(false); // false = heap máximo

        long inicio = System.currentTimeMillis();

        Node<String, Participante>[] array = servicio.getParticipantes().getArray();
        for (Node<String, Participante> nodo : array) {
            if (nodo == null) continue;
            Participante director = nodo.getValue();

            if (!director.getRol().equals("Director")) continue;

            MyHash<String, Pelicula> pelisDelDirector = director.getPeliculas();
            if (pelisDelDirector.getSize() <= 1) continue;

            MyList<Double> todasLasNotas = new MyLinkedList<>();

            Node<String, Pelicula>[] nodosPeliculas = pelisDelDirector.getArray();
            for (Node<String, Pelicula> nodoP : nodosPeliculas) {
                if (nodoP == null) continue;
                Pelicula peli = nodoP.getValue();
                MyList<Calificacion> califs = peli.getCalificaciones();

                for (int k = 0; k < califs.size(); k++) {
                    todasLasNotas.add(califs.get(k).getPuntaje());
                }
            }
            /*System.out.println("Director: " + director.getNombreParticipante() +
                    " | Películas: " + pelisDelDirector.getSize() +
                    " | Calificaciones: " + todasLasNotas.size());*/


            if (todasLasNotas.size() <= 100) continue;

            double mediana = calcularMediana(todasLasNotas);
            director.setValorComparacion(mediana);
            director.setCantidadPeliculas(pelisDelDirector.getSize());
            heap.insert(director);

        }
        int contador = 0;
        while (!heap.isEmpty() && contador < 10) {
            Participante d = heap.delete();
            System.out.println(d.getNombreParticipante() + ", " + d.getCantidadPeliculas() + ", " + String.format("%.2f", d.getValorComparacion()));
            contador++;
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la consulta: " + (fin - inicio) + " ms");
    }

    private double calcularMediana(MyList<Double> lista) {
        int n = lista.size();
        double[] copia = new double[n];
        for (int i = 0; i < n; i++) {
            copia[i] = lista.get(i);
        }
        Arrays.sort(copia);
        if (n % 2 == 1) {
            return copia[n / 2];
        } else {
            return (copia[n / 2 - 1] + copia[n / 2]) / 2.0;
        }
    }
}
