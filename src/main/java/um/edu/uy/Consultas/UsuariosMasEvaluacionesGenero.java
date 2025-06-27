package um.edu.uy.Consultas;

import um.edu.uy.AuxiliaresConsulta.TopUsuarioGenero;
import um.edu.uy.UMovieService;
import um.edu.uy.entities.Calificacion;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.Node;
import um.edu.uy.tads.heap.ArrayHeap;

public class UsuariosMasEvaluacionesGenero {

    private UMovieService servicio;

    public UsuariosMasEvaluacionesGenero(UMovieService servicio){
        this.servicio = servicio;
    }


    public void topUsuariosGeneros(MyList<Calificacion> calificaciones ){
        long inicio = System.currentTimeMillis(); // Inicio del tiempo de ejecución

        Hash<String, Integer> visualizacionesGeneros = new Hash<String, Integer>();

        for(int elementoCalificaciones = 0; elementoCalificaciones < calificaciones.size(); elementoCalificaciones++){
            MyList<String> generos = calificaciones.get(elementoCalificaciones).getPelicula().getGeneros();
            for(int elementoGeneros = 0; elementoGeneros < generos.size(); elementoGeneros++){

                Integer cantidadVistas = null;

                try {
                    cantidadVistas = visualizacionesGeneros.search(generos.get(elementoGeneros));

                } catch (InvalidHashKey e) {

                }

                if (cantidadVistas != null) {
                    try {
                        visualizacionesGeneros.replace(generos.get(elementoGeneros), cantidadVistas + 1);
                    } catch (InvalidHashKey e) {

                    }
                }
                else{
                    visualizacionesGeneros.add(generos.get(elementoGeneros), 1);
                }

            }

        }

        Node<String, Integer>[] arregloVisualizacionesGenero = visualizacionesGeneros.getArray();
        ArrayHeap<TopUsuarioGenero> calificacionesAuxiliar = new ArrayHeap<>(1000, false);
        for (Node<String, Integer> nodoCalificacionesAuxiliar : arregloVisualizacionesGenero) {
            if (nodoCalificacionesAuxiliar != null) {
                TopUsuarioGenero consulta6 = new TopUsuarioGenero(nodoCalificacionesAuxiliar.getKey(), nodoCalificacionesAuxiliar.getValue());
                calificacionesAuxiliar.insert(consulta6);
            }
        }

        for (int elementoCalificacionesAuxiliar = 0; elementoCalificacionesAuxiliar < calificacionesAuxiliar.size() - 10; elementoCalificacionesAuxiliar++) {
            calificacionesAuxiliar.delete();
        }

        for (int elementoCalificacionesAuxiliar = 1; elementoCalificacionesAuxiliar <= calificacionesAuxiliar.size(); elementoCalificacionesAuxiliar++) {
            TopUsuarioGenero visualizacionesUsuarioGenero = calificacionesAuxiliar.get(elementoCalificacionesAuxiliar);

            Hash<String, Integer> usuariosGeneros = new Hash<String, Integer>();

            String claveGenero = visualizacionesUsuarioGenero.getGenero();
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

            visualizacionesUsuarioGenero.setIdUsuario(usuarioTop);
            visualizacionesUsuarioGenero.setCantidadEvaluacionesUsuarioTop(cantidadTop);
        }

        for (int generoTopUsuario = 1; generoTopUsuario <= calificacionesAuxiliar.size(); generoTopUsuario++){
            System.out.println("Id usuario: " + calificacionesAuxiliar.get(generoTopUsuario).getIdusuario() + ", Género" + calificacionesAuxiliar.get(generoTopUsuario).getGenero() + ", Cantidad de evaluaciones " + calificacionesAuxiliar.get(generoTopUsuario).getCantidadEvaluacionesUsuarioTop());
        }

        long fin = System.currentTimeMillis(); // Fin del tiempo de ejecución
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");

    }
}
