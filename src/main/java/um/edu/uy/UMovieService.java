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
import java.util.Arrays;
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
                if (nodo.getValue().getCantidadDeCalificaciones() > 100){
                    Pelicula p = nodo.getValue();
                    Consulta2 promedio = new Consulta2(p.getIdPelicula(), p.getTituloPelicula(), p.getPromedioCalificaciones());
                    promedioAuxiliar.insert(promedio);
                }

            }
        }

        for (int i = 0; i < 10 && promedioAuxiliar.size() > 0; i++) {
            Consulta2 p = promedioAuxiliar.delete();
            System.out.println(p.getIdPelicula() + ", " + p.getTituloPelicula() + ", " + p.getCalificacionPromedio());
        }

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

        //es mejor hacer minheap sin necesidad de crear un nuevo heap? el top 5 va a devolverse de menor a mayor

        ArrayHeap<Consulta3> ingresosTop = new ArrayHeap<>(1000, true); //use length arreglo como capacidad del heap para que no sea fijo y dependa de la cant de datos

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
                                                     //no es necesario hacer print si entra en el catch
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
        for (int coleccionIngreso = 1; coleccionIngreso <= ingresosTop.size(); coleccionIngreso++) {
            System.out.println("Id colección: " + ingresosTop.get(coleccionIngreso).getIdColeccion() + ", Título colección: " + ingresosTop.get(coleccionIngreso).getTituloColeccion() + ", Cantidad de películas: " + ingresosTop.get(coleccionIngreso).getCantidadPeliculas() + ", Ids películas: " + ingresosTop.get(coleccionIngreso).getIdPeliculas() + "Ingresos Generados: " + ingresosTop.get(coleccionIngreso).getIngresosTotales());
        }
    }




    public void topUsuariosGeneros(MyList<Calificacion> calificaciones ){
        Hash<String, Integer> visualizacionesGeneros = new Hash<String, Integer>(1000);

        for(int i=0; i < calificaciones.size(); i++){
            MyList<String> generos = calificaciones.get(i).getPelicula().getGeneros();
            for(int j=0; j < generos.size(); j++){

                Integer cantidadVistas = null;

                try {
                    cantidadVistas = visualizacionesGeneros.search(generos.get(j));

                } catch (InvalidHashKey e) {

                }

                if (cantidadVistas != null) {
                    try {
                        visualizacionesGeneros.replace(generos.get(j), cantidadVistas + 1);
                    } catch (InvalidHashKey e) {

                    }
                }
                else{
                    visualizacionesGeneros.add(generos.get(j), 1);
                }

            }

        }

        Node<String, Integer>[] arreglo = visualizacionesGeneros.getArray();
        ArrayHeap<Consulta6> calificacionesAuxiliar = new ArrayHeap<>(1000, false); //lo creo como min heap xq me ahorra tener que crear otro heap para eliminar los elementos que no quiero
        for (Node<String, Integer> nodo : arreglo) {
            if (nodo != null) {
                Consulta6 consulta6 = new Consulta6(nodo.getKey(), nodo.getValue());
                calificacionesAuxiliar.insert(consulta6);
            }
        }
        // aca tengo el heap ordenado por genero con mas calificaciones


        for (int i = 0; i < calificacionesAuxiliar.size() - 10; i++) {               //este for me deja el top10
            calificacionesAuxiliar.delete();
        }

        for (int i = 1; i <= calificacionesAuxiliar.size(); i++) {
            Consulta6 c = calificacionesAuxiliar.get(i);                    // este c es un objeto de tipo consulta 6 a los que le hago los set

            Hash<String, Integer> usuariosGeneros = new Hash<String, Integer>(1000);

            String claveGenero = c.getGenero();
            for (int calificacionesLista = 0; calificacionesLista < calificaciones.size(); calificacionesLista++){
                String usuario = calificaciones.get(calificacionesLista).getUsuario().getIdUsuario();
                if (calificaciones.get(calificacionesLista).getPelicula().getGeneros().getValue(claveGenero) != null){

                    Integer cantidadCalificacionesUsuario = null;

                    try{
                        cantidadCalificacionesUsuario = usuariosGeneros.search(usuario);
                    } catch (InvalidHashKey e) {
                    }
                    if (cantidadCalificacionesUsuario != null) {
                        try {
                            usuariosGeneros.replace(usuario, cantidadCalificacionesUsuario + 1);
                        } catch (InvalidHashKey e) {
                        }
                    }
                    else{
                        usuariosGeneros.add(usuario, 1);
                    }
                }

            }
            Node<String, Integer>[] arregloUsuarios = usuariosGeneros.getArray();
            String usuarioTop = null;
            Integer cantidadTop = 0;
            for (Node<String, Integer> nodoArregloUsuarios : arregloUsuarios){
                if (nodoArregloUsuarios.getValue() > cantidadTop){
                    usuarioTop = nodoArregloUsuarios.getKey();
                    cantidadTop = nodoArregloUsuarios.getValue();
                }
            }

            c.setIdUsuario(usuarioTop);
            c.setCantidadEvaluacionesUsuarioTop(cantidadTop);
        }

        for (int generoTopUsuario = 1; generoTopUsuario <= calificacionesAuxiliar.size(); generoTopUsuario++){
            System.out.println("Id usuario: " + calificacionesAuxiliar.get(generoTopUsuario).getIdusuario() + ", Género" + calificacionesAuxiliar.get(generoTopUsuario).getGenero() + ", Cantidad de evaluaciones " + calificacionesAuxiliar.get(generoTopUsuario).getCantidadEvaluacionesUsuarioTop());
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

            // Imprimir directores
            System.out.println("Directores:");
            Node<String, Participante>[] nodosDirectores = p.getDirectores().getArray();
            for (Node<String, Participante> nodo : nodosDirectores) {
                if (nodo != null) {
                    Participante director = nodo.getValue();
                    System.out.println("- " + director.getNombreParticipante() + " (" + director.getRol() + ")");
                }
            }

            // Imprimir elenco
            System.out.println("Elenco:");
            for (int i = 0; i < p.getElenco().size(); i++) {
                Participante actor = p.getElenco().get(i);
                System.out.println("- " + actor.getNombreParticipante() + " (" + actor.getRol() + ")");
            }
            Coleccion c = p.getColeccion();
            if (c != null) {
                System.out.println("Colección: " + c.getTituloColeccion());
                System.out.printf("Ingresos totales de la colección: %,.2f\n", c.calcularIngresos());
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

    public void imprimirPeliculasDeParticipante(String nombre, String rol) {
        String clave = nombre + "-" + rol;
        try {
            Participante p = this.participantes.search(clave);
            System.out.println("Participante: " + p.getNombreParticipante() + " (" + p.getRol() + ")");
            System.out.println("Películas en las que participó:");

            MyList<String> clavesPeliculas = p.getPeliculas().keys();
            for (int i = 0; i < clavesPeliculas.size(); i++) {
                String clavePelicula = clavesPeliculas.get(i);
                Pelicula peli = p.getPeliculas().search(clavePelicula);
                System.out.println("- " + peli.getTituloPelicula() + " (ID: " + peli.getIdPelicula() + ")");
            }
        } catch (Exception e) {
            System.out.println("No se encontró al participante: " + nombre + " con rol: " + rol);
        }
    }


}


