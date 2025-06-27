package um.edu.uy.Consultas;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Calificacion;
import um.edu.uy.entities.Participante;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.heap.ArrayHeap;

import java.util.Arrays;

// falta mejorar el tiempo pero mas o menos

public class Top10DirectoresPorMediana {

    private UMovieService servicio;

    public Top10DirectoresPorMediana(UMovieService servicio) {
        this.servicio = servicio;
    }

    public void ejecutar() throws InvalidHashKey {
        MyHash<String, Participante> todosLosParticipantes = servicio.getParticipantes();
        ArrayHeap<Participante> heap = new ArrayHeap<>(false); // false = heap máximo

        long inicio = System.currentTimeMillis();

        MyList<String> claves = todosLosParticipantes.keys();
        for (int i = 0; i < claves.size(); i++) {
            String clave = claves.get(i);
            Participante director = todosLosParticipantes.search(clave);

            if (!director.getRol().equals("Director")) continue;

            MyHash<String, Pelicula> pelisDelDirector = director.getPeliculas();
            if (pelisDelDirector.getSize() <= 1) continue;

            MyList<String> clavesPeliculas = pelisDelDirector.keys();
            MyList<Double> todasLasNotas = new MyLinkedList<>();

            for (int j = 0; j < clavesPeliculas.size(); j++) {
                Pelicula peli = pelisDelDirector.search(clavesPeliculas.get(j));
                MyList<Calificacion> califs = peli.getCalificaciones();
                for (int k = 0; k < califs.size(); k++) {
                    todasLasNotas.add(califs.get(k).getPuntaje());
                }
            }

            if (todasLasNotas.size() <= 100) continue;

            double mediana = calcularMediana(todasLasNotas);
            director.setValorComparacion(mediana); // usar compareTo para ordenarlo
            director.setCantidadPeliculas(pelisDelDirector.getSize());
            heap.insert(director);
        }

        int contador = 0;
        while (!heap.isEmpty() && contador < 10) {
            Participante d = heap.delete();
            System.out.println(d.getNombreParticipante() + "," + d.getCantidadPeliculas() + "," + String.format("%.2f", d.getValorComparacion()));
            contador++;
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
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
