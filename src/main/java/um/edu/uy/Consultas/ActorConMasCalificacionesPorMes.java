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

    public void calcularActorPorMes() throws InvalidHashKey {
        MyHash<Integer, MyHash<String, Integer>> calificacionesPorMes = new Hash<>();
        MyHash<Integer, MyHash<String, MyHash<String, Boolean>>> peliculasPorMes = new Hash<>();

        MyList<Calificacion> calificaciones = servicio.getCalificaciones();

        long inicio = System.currentTimeMillis();

        for (int i = 0; i < calificaciones.size(); i++) {
            Calificacion c = calificaciones.get(i);
            int mes = c.getFecha().getMonthValue(); // Verifica que `getFecha` esté implementado
            Pelicula p = c.getPelicula(); // Verifica que `getPelicula` esté implementado
            String idPelicula = p.getIdPelicula();

            MyList<Participante> elenco = p.getElenco();

            for (int j = 0; j < elenco.size(); j++) {
                Participante actor = elenco.get(j);
                if (!actor.getRol().equals("Actor")) continue; // Verifica que `getRol` esté implementado

                String claveActor = actor.getNombreParticipante() + "-" + actor.getRol(); // Verifica que `getNombreParticipante` esté implementado

                synchronized (calificacionesPorMes) {
                    MyHash<String, Integer> actoresMes = calificacionesPorMes.contains(mes)
                            ? calificacionesPorMes.search(mes)
                            : new Hash<>();
                    actoresMes.add(claveActor, actoresMes.contains(claveActor)
                            ? actoresMes.search(claveActor) + 1
                            : 1);
                    calificacionesPorMes.add(mes, actoresMes);
                }

                synchronized (peliculasPorMes) {
                    MyHash<String, MyHash<String, Boolean>> actoresPeliculasMes = peliculasPorMes.contains(mes)
                            ? peliculasPorMes.search(mes)
                            : new Hash<>();
                    MyHash<String, Boolean> peliculasActor = actoresPeliculasMes.contains(claveActor)
                            ? actoresPeliculasMes.search(claveActor)
                            : new Hash<>();

                    peliculasActor.add(idPelicula, true); // se guarda sin repetir
                    actoresPeliculasMes.add(claveActor, peliculasActor);
                    peliculasPorMes.add(mes, actoresPeliculasMes);
                }
            }
        }

        // mostrar resultados
        for (int mes = 1; mes <= 12; mes++) {
            if (!calificacionesPorMes.contains(mes)) continue;

            MyHash<String, Integer> actoresMes = calificacionesPorMes.search(mes);
            MyList<String> actores = actoresMes.keys();

            String mejorActor = null;
            int maxCalificaciones = 0;

            for (int i = 0; i < actores.size(); i++) {
                String actor = actores.get(i);
                int califs = actoresMes.search(actor);
                if (califs > maxCalificaciones) {
                    maxCalificaciones = califs;
                    mejorActor = actor;
                }
            }

            if (mejorActor != null) {
                int peliculasVistas = 0;
                if (peliculasPorMes.contains(mes)) {
                    MyHash<String, MyHash<String, Boolean>> actoresPeliculas = peliculasPorMes.search(mes);
                    if (actoresPeliculas.contains(mejorActor)) {
                        peliculasVistas = actoresPeliculas.search(mejorActor).getSize();
                    }
                }

                System.out.println(mes + "," + mejorActor + "," + peliculasVistas + "," + maxCalificaciones);
            }
        }
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la consulta: " + (fin - inicio) + " ms");
    }

}


