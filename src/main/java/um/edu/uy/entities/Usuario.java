package um.edu.uy.entities;

import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;

public class Usuario {
    private String id_usuario;
    private MyList<Calificacion> calificaciones;

    public Usuario(String id_usuario) {
        this.id_usuario = id_usuario;
        this.calificaciones = new MyLinkedList<>();
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public MyList<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void agregarCalificacion(Calificacion calificacion) {
        this.calificaciones.add(calificacion);
    }

}
