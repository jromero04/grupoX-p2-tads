package um.edu.uy.CSV;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.MyHash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CargaPeliculas {

    // TENGO QUE COMPROBAR QUE SE ESTAN CARGANDO BIEN LOS INGRESOS
    // COMPROBAR QUE SE ESTAN HACIENDO LAS COLECCIONES SI COL ES []
    public void cargarPeliculas(UMovieService servicio, MyHash<String, Boolean> vistas) {
        String ruta = "movies_metadata.csv";
        int cargadas = 0;
        //ruta del archivo
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String cabecera = br.readLine(); // leer y descartar la cabecera

            String linea;
            int numeroLinea = 1;
            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                // Componemos línea completa si hay saltos dentro de campos
                while (!tieneCantidadParDeComillas(linea)) {
                    String siguiente = br.readLine();
                    if (siguiente == null) break;
                    linea += "\n" + siguiente;
                    numeroLinea++; //nuevo
                }

                // usamos split por coma, PERO con control de comas dentro de comillas
                String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                // si la linea no tiene suficientes columnas la ignoro
                if (partes.length < 19) {
                    System.out.println("Error inesperado en linea: " + linea);
                    continue;
                }; // asegurarse que hay suficientes columnas

                // extraigo los datos necesarios
                String id = partes[5].trim(); //el id esta en la col 5 check
                String titulo = partes[8].trim(); // el titulo en la 8 etc check
                String idioma = partes[7].trim(); // check
                String ingresosStr = partes[13].trim(); // check
                String generosStr = partes[3].trim(); // check
                String coleccionStr = partes[1].trim(); // check

                // Validacion: id no vacío y numérico
                if (id.isEmpty() || !id.matches("\\d+")) {
                    //System.out.println("ID invalido en linea: "+numeroLinea +": " + id);
                    System.out.println("ID invalido: " + id);
                    continue;
                }

                double ingresos = 0;
                try {
                    ingresos = Double.parseDouble(ingresosStr);
                } catch (NumberFormatException e) {
                    ingresos = 0;
                }

                if (servicio.getPeliculas().contains(id)){
                    continue;
                }

                // Creo la pelicula
                Pelicula p = new Pelicula(id, titulo, idioma, ingresos);

                // proceso de generos.
                generosStr = generosStr.replaceAll("^\"|\"$", ""); // quito las comillas dobles para que no generen problemas

                if (!generosStr.isEmpty() && generosStr.startsWith("[")){
                    String[] generosSeparados = generosStr.split("\\},");

                    Pattern generoPattern = Pattern.compile("\\{'id': \\d+, 'name': '([^']+)'\\}");
                    Matcher matcher = generoPattern.matcher(generosStr);
                    while (matcher.find()) {
                        String nombreGenero = matcher.group(1);
                        p.agregarGenero(nombreGenero);
                    }
//                    for (String g : generosSeparados){
//                        int name = g.indexOf("'name':");
//                        int start = g.indexOf("'", name + 8); // comilla de apertura del nombre
//                        int end = g.indexOf("'", start + 1);       // comilla de cierre
//                        if (start != -1 && end != -1) {
//                            String nombreGenero = g.substring(start + 1, end);
//                            p.agregarGenero(nombreGenero);
//                        }
//                    }
                    // Mensaje de depuración para géneros
                    //System.out.println("Géneros cargados para la película ID=" + id + ": " + p.getGeneros());
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

                        coleccion.agregarPelicula(p);
                        coleccion.setIngresosTotales(coleccion.getIngresosTotales() + ingresos); // acumular ingresos

                        p.setColeccion(coleccion); // vinculamos la colección a la película
                    }

                }

                servicio.getPeliculas().add(id, p);
                cargadas++;

                // Mensaje de depuración
                //System.out.println("Película cargada: ID=" + id + ", Título=" + titulo + ", Idioma=" + idioma + ", Ingresos=" + ingresos);
            }

            System.out.println("Peliculas cargadas: " + cargadas);
        } catch (IOException e) {
            System.out.println("Error leyendo movies_metadata.csv");
            e.printStackTrace();
        }
    }

    private boolean tieneCantidadParDeComillas(String linea) {
        long count = linea.chars().filter(c -> c == '"').count();
        return count % 2 == 0;
    }

}
