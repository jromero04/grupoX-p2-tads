package um.edu.uy;

import um.edu.uy.entities.Pelicula;

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

                // Validacion: id no vacío y numérico
                if (id.isEmpty() || !id.matches("\\d+")) continue;

                double ingresos = 0;
                if (!ingresosStr.isEmpty() && ingresosStr.matches("\\d+")) {
                    ingresos = Double.parseDouble(ingresosStr);
                }

                Pelicula p = new Pelicula(id, titulo, idioma, ingresos);
                servicio.getPeliculas().add(id, p);
                cargadas++;
            }

            System.out.println("Peliculas cargadas: " + cargadas);
        } catch (IOException e) {
            System.out.println("Error leyendo movies_metadata.csv");
            e.printStackTrace();
        }
    }

}
