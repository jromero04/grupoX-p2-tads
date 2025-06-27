package um.edu.uy.Consultas;

import um.edu.uy.AuxiliaresConsulta.TopUsuarioGenero;
import um.edu.uy.UMovieService;
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


    public void topUsuariosGeneros(){
        long inicio = System.currentTimeMillis();

        Hash<String, Integer> visualizacionesGeneros = new Hash<String, Integer>();

        for(int elementoCalificaciones = 0; elementoCalificaciones < servicio.getCalificaciones().size(); elementoCalificaciones++){
            MyList<String> generos = servicio.getCalificaciones().get(elementoCalificaciones).getPelicula().getGeneros();
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
        ArrayHeap<TopUsuarioGenero> calificacionesAuxiliar = new ArrayHeap<>(1000, true);
        for (Node<String, Integer> nodoCalificacionesAuxiliar : arregloVisualizacionesGenero) {
            if (nodoCalificacionesAuxiliar != null) {
                TopUsuarioGenero usuarioPorGenero = new TopUsuarioGenero(nodoCalificacionesAuxiliar.getKey(), nodoCalificacionesAuxiliar.getValue());
                calificacionesAuxiliar.insert(usuarioPorGenero);
            }
        }


        ArrayHeap<TopUsuarioGenero> calificacionesAuxiliar2 = new ArrayHeap<>(1000, true);
        for (int elementoCalificacionesAuxiliar = 0; elementoCalificacionesAuxiliar < 10; elementoCalificacionesAuxiliar++) {
            TopUsuarioGenero generotop = calificacionesAuxiliar.delete();
            calificacionesAuxiliar2.insert(generotop);
        }




        for (int elementoCalificacionesAuxiliar = 1; elementoCalificacionesAuxiliar <= calificacionesAuxiliar2.size(); elementoCalificacionesAuxiliar++) {
            TopUsuarioGenero visualizacionesUsuarioGenero = calificacionesAuxiliar2.get(elementoCalificacionesAuxiliar);

            Hash<String, Integer> usuariosGeneros = new Hash<String, Integer>();

            String claveGenero = visualizacionesUsuarioGenero.getGenero();
            for (int calificacionesLista = 0; calificacionesLista < servicio.getCalificaciones().size(); calificacionesLista++){
                String usuario = servicio.getCalificaciones().get(calificacionesLista).getUsuario().getIdUsuario();
                if (servicio.getCalificaciones().get(calificacionesLista).getPelicula().getGeneros().getValue(claveGenero) != null){

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
                if (nodoArregloUsuarios != null && nodoArregloUsuarios.getValue() > cantidadTop){
                    usuarioTop = nodoArregloUsuarios.getKey();
                    cantidadTop = nodoArregloUsuarios.getValue();
                }
            }

            visualizacionesUsuarioGenero.setIdUsuario(usuarioTop);
            visualizacionesUsuarioGenero.setCantidadEvaluacionesUsuarioTop(cantidadTop);
        }

        for (int generoTopUsuario = 1; generoTopUsuario <= calificacionesAuxiliar2.size(); generoTopUsuario++){
            System.out.println(calificacionesAuxiliar2.get(generoTopUsuario).getIdusuario() + ", " + calificacionesAuxiliar2.get(generoTopUsuario).getGenero() + ", " + calificacionesAuxiliar2.get(generoTopUsuario).getCantidadEvaluacionesUsuarioTop());
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");

    }
}
