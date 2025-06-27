package um.edu.uy.CSV;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Participante;
import um.edu.uy.entities.Pelicula;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CargaCreditos {

    public void cargarCreditos(UMovieService servicio) {
        String ruta = "credits.csv";
        int[] noEncontradas = {0};

        long inicio = System.currentTimeMillis();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            List<String> lineas = br.lines().skip(1).toList();

            int procesadas = lineas.parallelStream()
                    .mapToInt(linea -> procesarLineaCredito(linea, servicio, noEncontradas))
                    .sum();

            long fin = System.currentTimeMillis();
            System.out.println("Créditos procesados: " + procesadas);
            System.out.println("Películas no encontradas: " + noEncontradas[0]);
            System.out.println("Tiempo de carga de créditos: " + (fin - inicio) + " ms");
        } catch (Exception e) {
            System.out.println("Error leyendo archivo credits: " + e.getMessage());
        }
    }

    private int procesarLineaCredito(String linea, UMovieService servicio, int[] noEncontradas) {
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

        procesarActores(castStr, pelicula, servicio);
        procesarDirectores(crewStr, pelicula, servicio);

        return 1;
    }

    private void procesarActores(String castStr, Pelicula pelicula, UMovieService servicio) {
        try {
            castStr = castStr.trim();
            if (castStr.startsWith("\"") && castStr.endsWith("\"")) {
                castStr = castStr.substring(1, castStr.length() - 1);
            }

            castStr = castStr.replace("None", "null");

            Pattern pattern = Pattern.compile("'name':\\s*'([^']+)'");
            Matcher matcher = pattern.matcher(castStr);

            while (matcher.find()) {
                String nombreActor = matcher.group(1).replace("\"", "").trim();
                if (nombreActor.isEmpty()) continue;

                Participante participante = servicio.obtenerParticipante(nombreActor, "Actor");
                participante.agregarPelicula(pelicula);
                pelicula.agregarActor(participante);
            }

        } catch (Exception e) {
            System.out.println("Error en actores para película: " + pelicula.getIdPelicula() + " - " + pelicula.getTituloPelicula());
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void procesarDirectores(String crewStr, Pelicula pelicula, UMovieService servicio) {
        try {
            crewStr = crewStr.trim();
            if (crewStr.startsWith("\"") && crewStr.endsWith("\"")) {
                crewStr = crewStr.substring(1, crewStr.length() - 1);
            }

            crewStr = crewStr.replace("None", "null");

            Pattern pattern = Pattern.compile("\\{[^}]*'job':\\s*'Director'[^}]*'name':\\s*'([^']+)'");
            Matcher matcher = pattern.matcher(crewStr);

            while (matcher.find()) {
                String nombreDirector = matcher.group(1).replace("\"", "").trim();
                if (nombreDirector.isEmpty()) continue;

                Participante participante = servicio.obtenerParticipante(nombreDirector, "Director");
                participante.agregarPelicula(pelicula);
                pelicula.agregarDirector(participante);
            }

        } catch (Exception e) {
            System.out.println("Error al procesar director para película: " + pelicula.getIdPelicula());
        }
    }

}
