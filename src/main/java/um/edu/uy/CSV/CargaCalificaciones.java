package um.edu.uy.CSV;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Calificacion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.Usuario;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CargaCalificaciones {

    public MyHash<String, Boolean> cargarCalificaciones(UMovieService servicio) {
        String ruta = "ratings_1mm.csv";
        int cargadas = 0;
        int noEncontrdas = 0;


        MyHash<String, Boolean> peliculasConCalificacion = new Hash<>(); // clave = idPelicula

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String cabecera = br.readLine();

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length < 4) continue;

                String idUsuario = partes[0].trim();
                String idPelicula = partes[1].trim();
                String ratingStr = partes[2].trim();
                String timeStr = partes[3].trim();

                if (idUsuario.isEmpty() || idPelicula.isEmpty() || ratingStr.isEmpty() || timeStr.isEmpty()) continue;

                double puntaje;
                long timestamp;
                try {
                    puntaje = Double.parseDouble(ratingStr);
                    timestamp = Long.parseLong(timeStr);
                } catch (NumberFormatException e) {
                    System.out.println("Error al parsear puntaje o timestamp: " + e.getMessage());
                    continue;
                }

                //Convierto a LocalDateTime
                LocalDateTime fecha = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();


                // Marcar la película como vista
                peliculasConCalificacion.add(idPelicula, true);

                // Obtener o crear el usuario
                Usuario usuario;
                try {
                    usuario = servicio.getUsuarios().search(idUsuario);
                } catch (InvalidHashKey e) {
                    usuario = new Usuario(idUsuario);
                    servicio.getUsuarios().add(idUsuario, usuario);
                }

                //Obtener la pelicula
                Pelicula pelicula;
                try {
                    pelicula = servicio.getPeliculas().search(idPelicula);
                } catch (InvalidHashKey ex) {
                    //System.out.println("Pelicula no encontrada: " + idPelicula);
                    noEncontrdas++;
                    continue;
                }
                // Crear y asignar calificación
                Calificacion calificacion = new Calificacion(usuario, pelicula, puntaje, fecha);
                usuario.agregarCalificacion(calificacion);
                pelicula.agregarCalificacion(calificacion);
                servicio.getCalificaciones().add(calificacion);

                cargadas++;
            }

            System.out.println("Calificaciones cargadas: " + cargadas);
            System.out.println("Total de peliculas no encontradas: " + noEncontrdas);

        } catch (IOException e) {
            System.out.println("Error leyendo ratings_1mm.csv");
            e.printStackTrace();
        }

        return peliculasConCalificacion;
    }
}