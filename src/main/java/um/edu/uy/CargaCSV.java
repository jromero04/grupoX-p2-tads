package um.edu.uy;

import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Participante;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CargaCSV {

    public void cargarPeliculas(UMovieService servicio) {
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

    public void cargarCreditos(UMovieService servicio) {
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

                if (idPelicula.isEmpty()) continue;

                Pelicula p;
                try {
                    p = servicio.getPeliculas().search(idPelicula);
                } catch (InvalidHashKey e) {
                    continue; // la película no existe
                }

                //Procesar director
                if (crewStr.contains("'job': 'Director'")) {
                    String[] crewMiembros = crewStr.split("\\},");
                    for (String miembro : crewMiembros) {
                        if (miembro.contains("'job': 'Director'") && miembro.contains("'name':")) {
                            int nameIdx = miembro.indexOf("'name':");
                            int start = miembro.indexOf("'", nameIdx + 8);
                            int end = miembro.indexOf("'", start + 1);
                            if (start != -1 && end != -1) {
                                String nombre = miembro.substring(start + 1, end);
                                String claveDirector = nombre + "-Director";
                                Participante director;
                                try {
                                    director = servicio.getParticipantes().search(claveDirector);
                                } catch (InvalidHashKey e) {
                                    director = new Participante(nombre, "Director");
                                    servicio.getParticipantes().add(claveDirector, director);
                                }
                                director.agregarPelicula(idPelicula);
                                p.setDirector(director);
                                break; // solo 1 director
                            }
                        }
                    }
                }

                // Procesar actores
                if (!castStr.isEmpty()) {
                    String[] actores = castStr.split("\\},");
                    for (String actor : actores) {
                        if (actor.contains("'name':")) {
                            int nameIdx = actor.indexOf("'name':");
                            int start = actor.indexOf("'", nameIdx + 8);
                            int end = actor.indexOf("'", start + 1);
                            if (start != -1 && end != -1) {
                                String nombreActor = actor.substring(start + 1, end);
                                String claveActor = nombreActor + "-Actor";
                                Participante actorObj;
                                try {
                                    actorObj = servicio.getParticipantes().search(claveActor);
                                } catch (InvalidHashKey e) {
                                    actorObj = new Participante(nombreActor, "Actor");
                                    servicio.getParticipantes().add(claveActor, actorObj);
                                }
                                actorObj.agregarPelicula(idPelicula);
                                p.agregarParticipante(actorObj);

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


}
