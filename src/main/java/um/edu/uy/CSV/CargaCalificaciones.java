package um.edu.uy.CSV;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Calificacion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.Usuario;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CargaCalificaciones {

    public void cargarCalificaciones(UMovieService servicio) {
        String ruta = "ratings_1mm.csv";
        int cargadas = 0;
        int ignoradas = 0;
        int otrasExcepciones = 0;


        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String cabecera = br.readLine(); // ignorar la cabecera

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length < 4) {
                    continue;
                }

                try {
                    String idUsuario = partes[0].trim();
                    String idPelicula = partes[1].trim();
                    String ratingStr = partes[2].trim();
                    String timestampStr = partes[3].trim();


                    // Validar existencia de película
                    Pelicula pelicula;
                    try {
                        pelicula = servicio.getPeliculas().search(idPelicula);
                    } catch (InvalidHashKey e) {
                        continue;
                    }

                    // Obtener o crear usuario
                    Usuario usuario;
                    try {
                        usuario = servicio.getUsuarios().search(idUsuario);
                    } catch (InvalidHashKey e) {
                        usuario = new Usuario(idUsuario);
                        servicio.getUsuarios().add(idUsuario, usuario);
                    }

                    // Parseo de datos
                    double puntaje;
                    long segundos;
                    try {
                        puntaje = Double.parseDouble(ratingStr);
                        segundos = Long.parseLong(timestampStr);
                    } catch (Exception e) {
                        continue;
                    }

                    LocalDateTime fecha = LocalDateTime.ofInstant(Instant.ofEpochSecond(segundos), ZoneId.systemDefault());

                    // Crear y guardar calificación
                    Calificacion c = new Calificacion(usuario, pelicula, puntaje, fecha);
                    servicio.getCalificaciones().add(c);
                    pelicula.agregarCalificacion(c);
                    cargadas++;

                } catch (Exception e) {
                    otrasExcepciones++;
                    ignoradas++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

    }


}