package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CargaCSV {

    public MyHash<String, Boolean> cargarCalificaciones(UMovieService servicio) {
        String ruta = "ratings_1mm.csv";
        int cargadas = 0;

        MyHash<String, Boolean> peliculasConCalificacion = new Hash<>(10000); // clave = idPelicula

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String cabecera = br.readLine(); // descartar cabecera

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

                // Crear y asignar calificación
                Calificacion calificacion = new Calificacion(idUsuario, idPelicula, puntaje, fecha);
                usuario.agregarCalificacion(calificacion);

                servicio.getCalificaciones().add(calificacion); // una lista global

                //  asignar la calificación a la película
                try {
                    Pelicula pelicula = servicio.getPeliculas().search(idPelicula);
                    pelicula.agregarCalificacion(calificacion);
                } catch (InvalidHashKey e) {
                    //System.out.println("Película no encontrada: " + idPelicula);
                }

                // Mensaje de depuración
                //System.out.println("Calificación cargada: Usuario=" + idUsuario + ", Película=" + idPelicula + ", Puntaje=" + puntaje + ", Fecha=" + fecha);

                cargadas++;
            }

            System.out.println("Calificaciones cargadas: " + cargadas);
        } catch (IOException e) {
            System.out.println("Error leyendo ratings_1mm.csv");
            e.printStackTrace();
        }

        return peliculasConCalificacion;
    }

    public void cargarPeliculas(UMovieService servicio,  MyHash<String, Boolean> vistas) {
        String ruta = "movies_metadata.csv";
        int cargadas = 0;
        //ruta del archivo
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String cabecera = br.readLine(); // leer y descartar la cabecera

            String linea;
            while ((linea = br.readLine()) != null) {

                // usamos split por coma, PERO con control de comas dentro de comillas
                String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                // si la linea no tiene suficientes columnas la ignoro
                if (partes.length < 19) continue; // asegurarse que hay suficientes columnas

                // extraigo los datos necesarios
                String id = partes[5].trim(); //el id esta en la col 5
                String titulo = partes[8].trim(); // el titulo en la 8 etc
                String idioma = partes[7].trim();
                String ingresosStr = partes[13].trim();
                String generosStr = partes[3].trim();
                String coleccionStr = partes[1].trim();

                // Validacion: id no vacío y numérico
                if (id.isEmpty() || !id.matches("\\d+")) continue;

                // nuevo
                //if (!vistas.contains(id)) continue;

                double ingresos = 0;
                if (!ingresosStr.isEmpty() && ingresosStr.matches("\\d+")) {
                    ingresos = Double.parseDouble(ingresosStr);
                }
                // Creo la pelicula
                Pelicula p = new Pelicula(id, titulo, idioma, ingresos);

                // proceso de generos.
                if (!generosStr.isEmpty() && generosStr.startsWith("[")){
                    String[] generosSeparados = generosStr.split("\\},");

                    for (String g : generosSeparados){
                        int name = g.indexOf("'name':");
                        int start = g.indexOf("'", name + 8); // comilla de apertura del nombre
                        int end = g.indexOf("'", start + 1);       // comilla de cierre
                        if (start != -1 && end != -1) {
                            String nombreGenero = g.substring(start + 1, end);
                            p.agregarGenero(nombreGenero);
                        }
                    }
                }


                // proceso coleccion
                if (!coleccionStr.isEmpty() && coleccionStr.contains("'name':")) {
                    // Extraer id de la colección
                    int idIndex = coleccionStr.indexOf("'id':");
                    String idColeccion = null;
                    if (idIndex != -1) {
                        int idStart = coleccionStr.indexOf(" ", idIndex + 5);
                        int idEnd = coleccionStr.indexOf(",", idStart + 1);
                        if (idStart != -1 && idEnd != -1) {
                            idColeccion = coleccionStr.substring(idStart + 1, idEnd).trim();
                        }
                    }

                    // Extraer nombre
                    int nameIndex = coleccionStr.indexOf("'name':");
                    String nombreColeccion = null;
                    if (nameIndex != -1) {
                        int start = coleccionStr.indexOf("'", nameIndex + 8);
                        int end = coleccionStr.indexOf("'", start + 1);
                        if (start != -1 && end != -1) {
                            nombreColeccion = coleccionStr.substring(start + 1, end);
                        }
                    }

                    // Si pudimos obtener ambos datos
                    if (idColeccion != null && nombreColeccion != null) {
                        Coleccion coleccion;
                        try {
                            coleccion = servicio.getColecciones().search(idColeccion);
                        } catch (InvalidHashKey e) {
                            // si no existe la coleccion la creo y la agrego
                            coleccion = new Coleccion(idColeccion, nombreColeccion);
                            servicio.getColecciones().add(idColeccion, coleccion);
                        }

                        coleccion.agregarPelicula(id);
                        coleccion.setIngresosTotales(coleccion.getIngresosTotales() + ingresos); // acumular ingresos

                        p.setColeccion(coleccion); // vinculamos la colección a la película
                    }

                }

                servicio.getPeliculas().add(id, p);
                cargadas++;
            }

            System.out.println("Peliculas cargadas: " + cargadas);
        } catch (IOException e) {
            System.out.println("Error leyendo movies_metadata.csv");
            e.printStackTrace();
        }
    }

    public void cargarCreditos(UMovieService servicio, MyHash<String, Boolean> vistas) {
        String ruta = "credits.csv";
        int procesadas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String cabecera = br.readLine(); // descartar cabecera

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (partes.length < 3) continue;

                String castStr = partes[0].trim();
                String crewStr = partes[1].trim();
                String idPelicula = partes[2].trim();

                // valido id y filtro por peliculas vistas
                if (idPelicula.isEmpty() || !idPelicula.matches("\\d+")) continue;
                if (!vistas.contains(idPelicula)) continue;

                Pelicula pelicula;
                try {
                    pelicula = servicio.getPeliculas().search(idPelicula);
                } catch (InvalidHashKey e) {
                    continue; // la película no existe
                }


                // Procesar director
                if (crewStr.contains("'job': 'Director'")) {
                    String[] crewMiembros = crewStr.split("\\},");
                    for (String miembro : crewMiembros) {
                        if (miembro.contains("'job': 'Director'") && miembro.contains("'name':")) {
                            String nombreDirector = extraerNombre(miembro, "'name':");
                            if (nombreDirector != null) {
                                String claveDirector = nombreDirector + "-Director";
                                Participante director = obtenerOCrearParticipante(servicio, claveDirector, nombreDirector, "Director");
                                director.agregarPelicula(idPelicula);
                                pelicula.setDirector(director);
                                break; // Solo procesar un director
                            }
                        }
                    }
                }

                // Procesar actores
                if (!castStr.isEmpty()) {
                    String[] actores = castStr.split("\\},");
                    for (String actor : actores) {
                        if (actor.contains("'name':")) {
                            String nombreActor = extraerNombre(actor, "'name':");
                            if (nombreActor != null) {
                                String claveActor = nombreActor + "-Actor";
                                Participante actorObj = obtenerOCrearParticipante(servicio, claveActor, nombreActor, "Actor");
                                actorObj.agregarPelicula(idPelicula);
                                pelicula.agregarParticipante(actorObj);
                            }
                        }
                    }
                }


                procesadas++;
            }

            System.out.println("Créditos procesados: " + procesadas);
        } catch (IOException e) {
            System.out.println("Error leyendo credits.csv");
            e.printStackTrace();
        }
    }
    // Metodo auxiliar para extraer nombres
    private String extraerNombre(String texto, String campo) {
        int nameIdx = texto.indexOf(campo);
        int start = texto.indexOf("'", nameIdx + campo.length());
        int end = texto.indexOf("'", start + 1);
        if (start != -1 && end != -1) {
            return texto.substring(start + 1, end);
        }
        return null;
    }

    // Metodo auxiliar para obtener o crear un participante
    private Participante obtenerOCrearParticipante(UMovieService servicio, String clave, String nombre, String rol) {
        Participante participante;
        try {
            participante = servicio.getParticipantes().search(clave);
        } catch (InvalidHashKey e) {
            participante = new Participante(nombre, rol);
            servicio.getParticipantes().add(clave, participante);
        }
        return participante;
    }

}
