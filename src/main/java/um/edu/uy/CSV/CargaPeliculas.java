package um.edu.uy.CSV;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Pelicula;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CargaPeliculas {
    public void cargarPeliculas(UMovieService servicio) {
        String ruta = "movies_metadata.csv";
        int cargadas = 0;
        int ignoradas = 0;
        long inicio = System.currentTimeMillis();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String cabecera = br.readLine(); // ignorar primera línea

            String linea;
            while ((linea = br.readLine()) != null) {
                // Si la línea no termina de cerrar las comillas, seguir leyendo
                while (linea.chars().filter(ch -> ch == '"').count() % 2 != 0) {
                    String siguiente = br.readLine();
                    if (siguiente == null) break;
                    linea += "\n" + siguiente;
                }

                // validacion comienzo con true o false
                if (!(linea.startsWith("TRUE") || linea.startsWith("FALSE"))) {
                    /*System.out.println("Línea descartada por no empezar con TRUE/FALSE:");
                    System.out.println(linea);*/
                    ignoradas++;
                    continue;
                }

                String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (partes.length < 19) { // asegurarse de que haya suficientes campos
                    ignoradas++;
                    System.out.println("Línea ignorada por tamaño insuficiente:");
                    System.out.println(linea);
                    continue;
                }

                try {
                    String idStr = partes[5].trim();
                    String titulo = partes[8].trim();
                    String idioma = partes[7].trim();
                    String ingresosStr = partes[13].trim();
                    String generosStr = partes[3].trim();
                    String coleccionStr = partes[1].trim();

                    if (idStr.isEmpty() || titulo.isEmpty() || idioma.isEmpty()) {
                        ignoradas++;
                        continue;
                    }

                    //int id = Integer.parseInt(idStr);
                    double ingresos = ingresosStr.isEmpty() ? 0 : Double.parseDouble(ingresosStr);

                    Pelicula pelicula = new Pelicula(idStr, titulo, idioma, ingresos);


                    // Cargar géneros
                    generosStr = generosStr.replaceAll("^\"|\"$", ""); // limpia comillas dobles
                    if (!generosStr.isEmpty() && generosStr.startsWith("[")) {
                        Pattern patternGenero = Pattern.compile("'name': '([^']+)'");
                        Matcher matcherGenero = patternGenero.matcher(generosStr);
                        while (matcherGenero.find()) {
                            String genero = matcherGenero.group(1);
                            pelicula.agregarGenero(genero);
                        }
                    } else {
                        System.out.println("No se pudo procesar géneros para: " + titulo + " -> " + generosStr);
                    }



                    // Cargar colección
                    if (!coleccionStr.isEmpty()) {
                        Pattern patternID = Pattern.compile("'id': (\\d+)");
                        Pattern patternNombreCol = Pattern.compile("'name': '([^']+)'");

                        Matcher matcherID = patternID.matcher(coleccionStr);
                        Matcher matcherCol = patternNombreCol.matcher(coleccionStr);

                        if (matcherID.find() && matcherCol.find()) {
                            String idColeccion = matcherID.group(1);
                            String nombreColeccion = matcherCol.group(1);

                            Coleccion coleccion = servicio.buscarColeccion(idColeccion);

                            if (coleccion == null) {
                                coleccion = new Coleccion(idColeccion, nombreColeccion);
                                servicio.agregarColeccion(coleccion);
                            }
                            coleccion.agregarPelicula(pelicula);
                            pelicula.setColeccion(coleccion);
                        }
                    } else {
                        // Si no tiene colección, se considera una saga por sí misma
                        String idColeccion = idStr;
                        String nombreColeccion = titulo;

                        Coleccion coleccion = new Coleccion(idColeccion, nombreColeccion);
                        coleccion.agregarPelicula(pelicula);
                        pelicula.setColeccion(coleccion);
                        servicio.agregarColeccion(coleccion);
                    }


                    servicio.agregarPelicula(pelicula);
                    cargadas++;
                } catch (Exception e) {
                    ignoradas++;

                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        long fin = System.currentTimeMillis();
        System.out.println("Peliculas cargadas: " + cargadas);
        System.out.println("Peliculas ignoradas: " + ignoradas);
        System.out.println("Tiempo de carga de peliculas: " + (fin - inicio) + " ms");
    }

    public void buscarPeliculaPorId(String idBuscado) {
        String ruta = "movies_metadata.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String cabecera = br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (partes.length > 5) {
                    String id = partes[5].trim();
                    if (id.equals(idBuscado)) {
                        System.out.println("Linea encontrada con ID " + idBuscado + ":");
                        System.out.println(linea);
                        return;
                    }
                }
            }
            System.out.println(" No se encontro la pelicula con ID: " + idBuscado);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }



}
