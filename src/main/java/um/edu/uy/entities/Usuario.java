package um.edu.uy.entities;

import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;

public class Usuario {
    private String idUsuario;
    private MyList<Calificacion> calificaciones;

    public Usuario(String idUsuario) {
        this.idUsuario = idUsuario;
        this.calificaciones = new MyLinkedList<>();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public MyList<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void agregarCalificacion(Calificacion calificacion) {
        this.calificaciones.add(calificacion);
    }

}
