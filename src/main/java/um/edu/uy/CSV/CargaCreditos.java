package um.edu.uy.CSV;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Participante;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.MyHash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CargaCreditos {

    /*public void cargarCreditos(UMovieService servicio, MyHash<String, Boolean> vistas) {
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
    }*/

        public void cargarCreditosOptimizado(UMovieService servicio, MyHash<String, Boolean> vistas) {
            String ruta = "credits.csv";
            int procesadas = 0;

            try (Stream<String> lineas = Files.lines(Paths.get(ruta))) {
                lineas.skip(1) // Saltar la cabecera
                      .parallel() // Procesar en paralelo
                      .map(linea -> procesarLinea(linea, servicio, vistas))
                      .reduce(0, Integer::sum); // Sumar las líneas procesadas

                System.out.println("Créditos procesados: " + procesadas);
            } catch (IOException e) {
                System.out.println("Error leyendo credits.csv");
                e.printStackTrace();
            }
        }

        private int procesarLinea(String linea, UMovieService servicio, MyHash<String, Boolean> vistas) {
            String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if (partes.length < 3) return 0;

            String castStr = partes[0].trim();
            String crewStr = partes[1].trim();
            String idPelicula = partes[2].trim();

            if (idPelicula.isEmpty() || !idPelicula.matches("\\d+") || !vistas.contains(idPelicula)) return 0;

            Pelicula pelicula;
            try {
                pelicula = servicio.getPeliculas().search(idPelicula);
            } catch (InvalidHashKey e) {
                return 0; // La película no existe
            }

            // Procesar directores
            if (crewStr.contains("'job': 'Director'")) {
                String[] crewMiembros = crewStr.split("\\},");
                for (String miembro : crewMiembros) {
                    if (miembro.contains("'job': 'Director'") && miembro.contains("'name':")) {
                        String nombreDirector = extraerNombre(miembro, "'name':");
                        if (nombreDirector != null) {
                            String claveDirector = nombreDirector + "-Director";
                            Participante director = obtenerOCrearParticipante(servicio, claveDirector, nombreDirector, "Director");
                            director.agregarPelicula(pelicula);
                            pelicula.agregarDirector(director);
                        }
                    }
                }
            }

            // Procesar actores
//            if (!castStr.isEmpty()) {
//                String[] actores = castStr.split("\\},");
//                for (String actor : actores) {
//                    if (actor.contains("'name':")) {
//                        String nombreActor = extraerNombre(actor, "'name':");
//                        if (nombreActor != null) {
//                            String claveActor = nombreActor + "-Actor";
//                            Participante actorObj = obtenerOCrearParticipante(servicio, claveActor, nombreActor, "Actor");
//                            actorObj.agregarPelicula(pelicula);
//                            pelicula.agregarParticipante(actorObj);
//                        }
//                    }
//                }
//            }

            return 1;
        }

        private String extraerNombre(String texto, String campo) {
            int nameIdx = texto.indexOf(campo);
            int start = texto.indexOf("'", nameIdx + campo.length());
            int end = texto.indexOf("'", start + 1);
            if (start != -1 && end != -1) {
                return texto.substring(start + 1, end);
            }
            return null;
        }

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


    public void cargarCreditosOptimizadoOP(UMovieService servicio, MyHash<String, Boolean> vistas) {
        String ruta = "credits.csv";
        int procesadas = 0;

        // Expresiones regulares para extraer datos
        Pattern directorPattern = Pattern.compile("\\{[^}]*'job': 'Director'[^}]*'name': '([^']+)'");
        Pattern actorPattern = Pattern.compile("'name': '([^']+)'");

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            br.readLine(); // Saltar la cabecera

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (partes.length < 3) continue;

                String castStr = partes[0].trim();
                String crewStr = partes[1].trim();
                String idPelicula = partes[2].trim();

                if (idPelicula.isEmpty() || !idPelicula.matches("\\d+") || !vistas.contains(idPelicula)) continue;

                Pelicula pelicula;
                try {
                    pelicula = servicio.getPeliculas().search(idPelicula);
                } catch (InvalidHashKey e) {
                    continue; // La película no existe
                }

                // Procesar directores
                Matcher directorMatcher = directorPattern.matcher(crewStr);
                while (directorMatcher.find()) {
                    String nombreDirector = directorMatcher.group(1);
                    String claveDirector = nombreDirector + "-Director";
                    Participante director = obtenerOCrearParticipante(servicio, claveDirector, nombreDirector, "Director");
                    director.agregarPelicula(pelicula);
                    pelicula.agregarDirector(director);
                }

                // Procesar actores
                Matcher actorMatcher = actorPattern.matcher(castStr);
                while (actorMatcher.find()) {
                    String nombreActor = actorMatcher.group(1);
                    pelicula.agregarNombreActor(nombreActor);
                }

                procesadas++;
            }

            System.out.println("Créditos procesados: " + procesadas);
        } catch (IOException e) {
            System.out.println("Error leyendo credits.csv");
            e.printStackTrace();
        }
    }

    private Participante obtenerOCrearParticipanteOP(UMovieService servicio, String clave, String nombre, String rol) {
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

