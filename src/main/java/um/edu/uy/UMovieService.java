package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.hash.Node;
import um.edu.uy.tads.heap.ArrayHeap;

import java.util.ArrayList;
import java.util.List;

public class UMovieService {
    private MyHash<String, Pelicula> peliculas = new Hash<>();
    private MyHash<String, Usuario> usuarios = new Hash<>();
    private MyHash<String, Participante> participantes = new Hash<>();
    private MyHash<String, Coleccion> colecciones = new Hash<>();

    private MyList<Calificacion> calificaciones = new MyLinkedList<>();


    public MyHash<String, Pelicula> getPeliculas() {
        return peliculas;
    }

    public MyHash<String, Coleccion> getColecciones() {
        return colecciones;
    }

    public MyHash<String, Participante> getParticipantes() {
        return participantes;
    }

    public MyHash<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public MyList<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void agregarColeccion(Coleccion c) {
        try {
            colecciones.add(c.getIdColeccion(), c);
        } catch (Exception e) {
            System.out.println("Error al agregar coleccion: " + e.getMessage());
        }
    }

    public Coleccion buscarColeccion(String idColeccion) {
        try {
            return colecciones.search(idColeccion);
        } catch (InvalidHashKey e) {
            return null; // si no existe esa clave en el hash
        }
    }

    public void agregarPelicula(Pelicula p) {
        peliculas.add(p.getIdPelicula(), p);
    }

    public MyList<Pelicula> getPeliculasComoLista() {
        return peliculas.getValues();
    }

    public MyList<Coleccion> getColeccionesComoLista() {
        return colecciones.getValues();
    }

    //dos nuevos
    public Participante obtenerParticipante(String nombre, String rol) throws InvalidHashKey {
        String clave = nombre + "-" + rol;
        if (!participantes.contains(clave)) {
            Participante nuevo = new Participante(nombre, rol);
            participantes.add(clave, nuevo);
        }
        return participantes.search(clave);
    }

    public Pelicula searchOrNull(String id) {
        try {
            return peliculas.search(id);
        } catch (InvalidHashKey e) {
            return null;
        }
    }





    public void topPeliculasPorIdioma() {
        String[] idiomas = {"en", "fr", "it", "es", "pt"};

        for (String idioma : idiomas) {
            ArrayHeap<Pelicula> heap = new ArrayHeap<>(true); // MaxHeap

            Node<String, Pelicula>[] arreglo = peliculas.getArray();
            for (Node<String, Pelicula> nodo : arreglo) {
                if (nodo != null) {
                    Pelicula p = nodo.getValue();
                    if (p.getIdiomaOriginal().equals(idioma)) {
                        // Verificar si la película tiene calificaciones
                        if (p.getCantidadDeCalificaciones() > 0) {
                            heap.insert(p);
                        }
                    }
                }
            }


            System.out.println("Top 5 para idioma: " + idioma);
            for (int i = 0; i < 5 && heap.size() > 0; i++) {
                Pelicula p = heap.delete();
                System.out.println(p.getIdPelicula() + ", " + p.getTituloPelicula() + ", " + p.getCantidadDeCalificaciones() + ", " + p.getIdiomaOriginal());
            }
            System.out.println();
        }


    }
/*
    private static void ordenarPorCalificaciones(MyList<Pelicula> lista) {
        ArrayHeap<Pelicula> heap = new ArrayHeap<>(lista.size() + 1, true); // true = MaxHeap

        for (int i = 0; i < lista.size(); i++) {
            heap.insert(lista.get(i));
        }

        // Limpiamos lista original
        while (lista.size() > 0) {
            lista.remove(0);
        }

        // Recuperamos elementos en orden descendente
        while (heap.size() > 0) {
            lista.add(heap.delete());
        }
    }
    */


    public void mejorCalificacion(MyHash<String, Pelicula> peliculas) {
        Node<String, Pelicula>[] arreglo = peliculas.getArray();
        ArrayHeap<Consulta2> promedioAuxiliar = new ArrayHeap<>(arreglo.length, true); //use length arreglo como capacidad del heap para que no sea fijo y dependa de la cant de datos
        for (Node<String, Pelicula> nodo : arreglo) {
            if (nodo != null) {
                Pelicula p = nodo.getValue();
                Consulta2 promedio = new Consulta2(p.getIdPelicula(), p.getTituloPelicula(), p.getPromedioCalificaciones());
                promedioAuxiliar.insert(promedio);
            }
        }

        for (int i = 0; i < 10 && promedioAuxiliar.size() > 0; i++) {
            Consulta2 p = promedioAuxiliar.delete();
            System.out.println(p.getIdPelicula() + ", " + p.getTituloPelicula() + ", " + p.getCalificacionPromedio());
        }
        System.out.println();
    }


    public void ingresosSaga (MyHash<String, Coleccion> colecciones) {
        Node<String, Coleccion>[] arreglo = colecciones.getArray();
        ArrayHeap<Consulta3> ingresosSagaAuxiliar = new ArrayHeap<>(arreglo.length, true); //use length arreglo como capacidad del heap para que no sea fijo y dependa de la cant de datos
        for (Node<String, Coleccion> nodo : arreglo) {
            if (nodo != null) {
                Coleccion c = nodo.getValue();
                Consulta3 consultaColeccion = new Consulta3(c.getIdColeccion(), c.getTituloColeccion(), c.getIngresosTotales());
                ingresosSagaAuxiliar.insert(consultaColeccion);
            }
        }

        //hice este heap nuevo porque queria que solo mantenga el top5 (no encontre otra forma de borrar las que no quiero sin borrar el top
        ArrayHeap<Consulta3> ingresosTop = new ArrayHeap<>(arreglo.length, true); //use length arreglo como capacidad del heap para que no sea fijo y dependa de la cant de datos

        for (int i = 0; i < 5 && ingresosSagaAuxiliar.size() > 0; i++) {
            Consulta3 c = ingresosSagaAuxiliar.delete();
            ingresosTop.insert(c);
            //este c cambiar el nombre (esta mal?)
        }
        //en este punto tengo el heap ingresostop con las 5 colecciones top

        //ahora quiero recorrer este heap para hallar el idpeliculas

        for (int i = 1; i <= ingresosTop.size(); i++) {
            Consulta3 c = ingresosTop.get(i);     //cree un metodo get en heap
            String idColeccion = c.getIdColeccion();

            Coleccion coleccion = null;          //sin esta linea no me reconoce el coleccion mas abajo

            try {
                coleccion = colecciones.search(idColeccion);
            } catch (InvalidHashKey e) {
                System.out.println("ID de colección no encontrado: " + idColeccion);  //este print no se si esta bien
            }
            if (coleccion != null) {

                MyList<String> idsPeliculas = new MyLinkedList<>(); // lista vacía para meter los id

                MyList<Pelicula> peliculas = coleccion.getPeliculas();
                for (int iteracion = 0; iteracion < peliculas.size(); i++) {         //cambiar nombre iteracion
                    Pelicula p = peliculas.get(iteracion);
                    idsPeliculas.add(p.getIdPelicula());
                }

                c.setIdPeliculas(idsPeliculas);
                c.setCantidadPeliculas(idsPeliculas.size());


            }

        }
    }













    /*public void top10Directores() throws InvalidHashKey {
        MyHash<String, MyLinkedList<Double>> calificacionesPorDirector = new Hash<>();
        MyHash<String, Integer> peliculasPorDirector = new Hash<>();

        Node<String, Pelicula>[] nodosPeliculas = peliculas.getArray();

        for (Node<String, Pelicula> nodo : nodosPeliculas) {
            if (nodo != null) {
                Pelicula peli = nodo.getValue();
                Participante director = peli.getDirectores().search();
                if (director == null) continue;

                String nombreDirector = director.getNombreParticipante();

//                // DEBUG: mostrar datos de un director puntual
//                if (nombreDirector.equals("Nora Ephron")) {
//                    System.out.println("Película de Nora Ephron: " + peli.getTitulo_pelicula());
//                    System.out.println("   Calificaciones:");
//                    for (int i = 0; i < peli.getCalificaciones().size(); i++) {
//                        Calificacion c = peli.getCalificaciones().get(i);
//                        System.out.print(c.getPuntaje() + " ");
//                    }
//                    System.out.println("\n---");
//                }

                MyList<Calificacion> calificaciones = peli.getCalificaciones();
                if (calificaciones == null || calificaciones.size() == 0) continue;

                if (!calificacionesPorDirector.contains(nombreDirector)) {
                    calificacionesPorDirector.add(nombreDirector, new MyLinkedList<>());
                    peliculasPorDirector.add(nombreDirector, 0);
                }

                for (int i = 0; i < calificaciones.size(); i++) {
                    Calificacion calificacion = calificaciones.get(i);
                    calificacionesPorDirector.search(nombreDirector).add(calificacion.getPuntaje());
                }

                int cantPeliculas = peliculasPorDirector.search(nombreDirector);
                peliculasPorDirector.add(nombreDirector, cantPeliculas + 1);
            }
        }

        ArrayHeap<InfoDirector> heap = new ArrayHeap<>(true); // heap máximo

        for (Node<String, MyLinkedList<Double>> nodo : calificacionesPorDirector.getArray()) {
            if (nodo != null) {
                String director = nodo.getKey();
                MyLinkedList<Double> califs = nodo.getValue();int totalCalificaciones = califs.size();
            int cantPeliculas = peliculasPorDirector.search(director);

            if (totalCalificaciones > 100 && cantPeliculas > 1) {
                double mediana = calcularMediana(califs);
                heap.insert(new InfoDirector(director, cantPeliculas, mediana));
            }
        }
        }

        int i = 0;
        while (heap.size() > 0 && i < 10) {
            InfoDirector d = heap.delete();
            System.out.println(d.getNombre() + "," + d.getCantidadPeliculas() + "," + d.getMediana());
            i++;
        }

    }

    private double calcularMediana(MyLinkedList<Double> lista) {
        int n = lista.size();
        double[] arr = new double[n];
        for (int i = 0; i < lista.size(); i++) {
            arr[i] = lista.get(i);
        }

        ordenarArray(arr);

        if (n % 2 == 0) {
            return (arr[n/2 - 1] + arr[n/2]) / 2.0;
        } else {
            return arr[n/2];
        }
    }

    private void ordenarArray(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    double temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }*/

    public void imprimirInfoPelicula(String id) {
        try {
            Pelicula p = this.peliculas.search(id);
            System.out.println("ID: " + p.getIdPelicula());
            System.out.println("Título: " + p.getTituloPelicula());
            System.out.println("Idioma: " + p.getIdiomaOriginal());
            System.out.printf("Ingresos: %,.2f\n", p.getIngresos());

            System.out.print("Géneros: ");
            for (int i = 0; i < p.getGeneros().size(); i++) {
                System.out.print(p.getGeneros().get(i) + " ");
            }
            System.out.println();

            Coleccion c = p.getColeccion();
            if (c != null) {
                System.out.println("Colección: " + c.getTituloColeccion());
                System.out.printf("Ingresos totales de la colección: %,.2f\n", c.getIngresosTotales());
                System.out.println("Películas en la colección:");
                for (int i = 0; i < c.getPeliculas().size(); i++) {
                    Pelicula peli = c.getPeliculas().get(i);
                    System.out.println("- " + peli.getTituloPelicula() + " (ID: " + peli.getIdPelicula() + ")");
                }
            } else {
                System.out.println("Colección: Ninguna");
            }
        } catch (InvalidHashKey e) {
            System.out.println("No se encontró la película con ID: " + id);
        }
    }

    public void mostrarUnaPeliculaSinColeccionOriginal() {
        Node<String, Pelicula>[] nodosPeliculas = this.peliculas.getArray();
        for (Node<String, Pelicula> nodo : nodosPeliculas) {
            if (nodo != null) {
                Pelicula p = nodo.getValue();
                Coleccion c = p.getColeccion();
                if (c != null && c.getIdColeccion().equals(p.getIdPelicula())) {
                    System.out.println("Encontré una película sin colección original:");
                    imprimirInfoPelicula(p.getIdPelicula());
                    break;
                }
            }
        }
    }
}


