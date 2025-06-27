package um.edu.uy.Consultas;

import um.edu.uy.UMovieService;
import um.edu.uy.entities.Calificacion;
import um.edu.uy.entities.Participante;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;

public class ActorConMasCalificacionesPorMes {
    private UMovieService servicio = new UMovieService();

    public ActorConMasCalificacionesPorMes(UMovieService servicio){
        this.servicio = servicio;
    }

    /*public void calcularActorPorMes() throws InvalidHashKey {
        MyHash<Integer, MyHash<String, Integer>> conteoPorMes = new Hash<>();
        MyHash<String, Integer> peliculasPorActor = new Hash<>();

        MyList<Calificacion> calificaciones = servicio.getCalificaciones();

        // Precomputar calificaciones por mes y actor
        for (int i = 0; i < calificaciones.size(); i++) {
            Calificacion c = calificaciones.get(i);
            Pelicula p = c.getPelicula();
            int mes = c.getFecha().getMonthValue();

            // Inicializar el hash para el mes si no existe
            if (!conteoPorMes.contains(mes)) {
                conteoPorMes.add(mes, new Hash<>());
            }

            MyHash<String, Integer> actoresMes = conteoPorMes.search(mes);

            // Contar calificaciones por actor
            MyList<Participante> elenco = p.getElenco();
            for (int j = 0; j < elenco.size(); j++) {
                Participante actor = elenco.get(j);
                String nombreActor = actor.getNombreParticipante();

                int calificacionesActor = actoresMes.contains(nombreActor)
                        ? actoresMes.search(nombreActor)
                        : 0;
                actoresMes.add(nombreActor, calificacionesActor + 1);

                // Contar películas vistas por actor
                int peliculasActor = peliculasPorActor.contains(nombreActor)
                        ? peliculasPorActor.search(nombreActor)
                        : 0;
                peliculasPorActor.add(nombreActor, peliculasActor + 1);
            }
        }

        // Mostrar resultados
        for (int mes = 1; mes <= 12; mes++) {
            if (!conteoPorMes.contains(mes)) continue;

            MyHash<String, Integer> actoresMes = conteoPorMes.search(mes);
            MyList<String> nombresActores = actoresMes.keys();

            String actorTop = null;
            int maxCalificaciones = 0;

            // Encontrar el actor con más calificaciones
            for (int i = 0; i < nombresActores.size(); i++) {
                String nombreActor = nombresActores.get(i);
                int calificacionesActor = actoresMes.search(nombreActor);

                if (calificacionesActor > maxCalificaciones) {
                    maxCalificaciones = calificacionesActor;
                    actorTop = nombreActor;
                }
            }

            if (actorTop != null) {
                int peliculasVistas = peliculasPorActor.search(actorTop);

                System.out.println("Mes: " + mes);
                System.out.println("Actor: " + actorTop);
                System.out.println("Películas vistas: " + peliculasVistas);
                System.out.println("Total calificaciones: " + maxCalificaciones);
                System.out.println();
            }
        }
    }*/

    public void calcularActorPorMesOptimizado() throws InvalidHashKey {
        MyHash<Integer, MyHash<String, Integer>> conteoPorMes = new Hash<>();
        MyHash<String, Integer> peliculasPorActor = new Hash<>();

        MyList<Calificacion> calificaciones = servicio.getCalificaciones();

        // Precomputar calificaciones por mes y actor
        for (int i = 0; i < calificaciones.size(); i++) {
            Calificacion c = calificaciones.get(i);
            Pelicula p = c.getPelicula();
            int mes = c.getFecha().getMonthValue();

            // Inicializar el hash para el mes si no existe
            MyHash<String, Integer> actoresMes = conteoPorMes.contains(mes)
                    ? conteoPorMes.search(mes)
                    : new Hash<>();
            conteoPorMes.add(mes, actoresMes);

            // Contar calificaciones por actor
            MyList<Participante> elenco = p.getElenco();
            for (int j = 0; j < elenco.size(); j++) {
                Participante actor = elenco.get(j);
                String nombreActor = actor.getNombreParticipante();

                actoresMes.add(nombreActor, actoresMes.contains(nombreActor)
                        ? actoresMes.search(nombreActor) + 1
                        : 1);

                peliculasPorActor.add(nombreActor, peliculasPorActor.contains(nombreActor)
                        ? peliculasPorActor.search(nombreActor) + 1
                        : 1);
            }
        }

        // Mostrar resultados
        for (int mes = 1; mes <= 12; mes++) {
            if (!conteoPorMes.contains(mes)) continue;

            MyHash<String, Integer> actoresMes = conteoPorMes.search(mes);
            MyList<String> nombresActores = actoresMes.keys();

            String actorTop = null;
            int maxCalificaciones = 0;

            // Encontrar el actor con más calificaciones
            for (int i = 0; i < nombresActores.size(); i++) {
                String nombreActor = nombresActores.get(i);
                int calificacionesActor = actoresMes.search(nombreActor);

                if (calificacionesActor > maxCalificaciones) {
                    maxCalificaciones = calificacionesActor;
                    actorTop = nombreActor;
                }
            }

            if (actorTop != null) {
                int peliculasVistas = peliculasPorActor.search(actorTop);

                System.out.println("Mes: " + mes);
                System.out.println("Actor: " + actorTop);
                System.out.println("Películas vistas: " + peliculasVistas);
                System.out.println("Total calificaciones: " + maxCalificaciones);
                System.out.println();
            }
        }
    }
}
