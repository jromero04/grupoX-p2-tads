package um.edu.uy.CSV;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import um.edu.uy.UMovieService;
import um.edu.uy.entities.Participante;
import um.edu.uy.entities.Pelicula;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class CargaCreditos {

    private static class CastEntry {
        public int id;
        public String name;
    }

    private static class CrewEntry {
        public String job;
        public String name;
    }

    public void cargarCreditos(UMovieService servicio) {
        String ruta = "credits.csv";
        ObjectMapper mapper = new ObjectMapper();
        int[] noEncontradas = {0};

        long inicio = System.currentTimeMillis();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            List<String> lineas = br.lines().skip(1).toList();

            int procesadas = lineas.parallelStream()
                    .mapToInt(linea -> procesarLineaCredito(linea, servicio, mapper, noEncontradas))
                    .sum();

            long fin = System.currentTimeMillis();
            System.out.println("Créditos procesados: " + procesadas);
            System.out.println("Películas no encontradas: " + noEncontradas[0]);
            System.out.println("Tiempo de carga de creditos: " + (fin - inicio) + " ms");
        } catch (Exception e) {
            System.out.println("Error leyendo archivo credits: " + e.getMessage());
        }
    }

    private int procesarLineaCredito(String linea, UMovieService servicio, ObjectMapper mapper, int[] noEncontradas) {
        String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (partes.length < 3) return 0;

        String castStr = partes[0].trim();
        String crewStr = partes[1].trim();
        String idPelicula = partes[2].trim();

        Pelicula pelicula;
        try {
            pelicula = servicio.getPeliculas().search(idPelicula);
        } catch (Exception e) {
            synchronized (noEncontradas) {
                noEncontradas[0]++;
            }
            return 0;
        }

        procesarActores(castStr, pelicula, servicio, mapper);
        procesarDirectores(crewStr, pelicula, servicio, mapper);

        return 1;
    }

    private void procesarActores(String castStr, Pelicula pelicula, UMovieService servicio, ObjectMapper mapper) {
        try {
            List<CastEntry> actores = mapper.readValue(castStr, new TypeReference<List<CastEntry>>() {});
            for (CastEntry actor : actores) {
                String nombreActor = actor.name;

                Participante participante = servicio.obtenerParticipante(nombreActor, "Actor");
                participante.agregarPelicula(pelicula);

                pelicula.agregarActor(participante);
            }
        } catch (Exception e) {
            // Ignorar errores
        }
    }

    private void procesarDirectores(String crewStr, Pelicula pelicula, UMovieService servicio, ObjectMapper mapper) {
        try {
            List<CrewEntry> equipo = mapper.readValue(crewStr, new TypeReference<List<CrewEntry>>() {});
            for (CrewEntry miembro : equipo) {
                if ("Director".equalsIgnoreCase(miembro.job)) {
                    Participante director = servicio.obtenerParticipante(miembro.name, "Director");
                    director.agregarPelicula(pelicula);
                    pelicula.agregarDirector(director);
                }
            }
        } catch (Exception e) {
            // Ignorar errores
        }
    }



}
