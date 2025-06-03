package um.edu.uy.entities;

import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;

import java.util.Objects;

public class Participante {
    private String nombre_participante;
    /* los roles se encuentran en CSV credits, lista enorme de cast
    lista de crew donde voy a encontrar la director y al final el id de la pelicula.
    */
    private String rol;
    private MyList<String> peliculas;

    // ver si no agregar fecha por mes del anio para consultas

    public Participante(String nombre_participante, String rol) {
        this.nombre_participante = nombre_participante;
        this.rol = rol;
        this.peliculas = new MyLinkedList<>();
    }

    public String getNombre_participante() {
        return nombre_participante;
    }

    public void setNombre_participante(String nombre_participante) {
        this.nombre_participante = nombre_participante;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public MyList<String> getPeliculas() {
        return peliculas;
    }

    // evito que se agreguen mas de una vez el idPelicula
    // que pasa si un participante aparece como director y actor en una misma pelicula??
    public void agregarPelicula(String idPelicula){
        boolean yaRegistrada = false;
        for (int i = 0; i<peliculas.size(); i++){
            if (peliculas.get(i).equals(idPelicula)){
                yaRegistrada = true;
                break;
            }
        }
        if (!yaRegistrada){
            this.peliculas.add(idPelicula);
        }else {
            System.out.println("La pelicula con id: " + idPelicula + " ya esta registrada en las participaciones");
        }
    }

    /* hay participantes que pueden ser actores y directores
    al crear la instancia los diferencio
    dos participantes son los mismos si comparten nombre y rol
    Evitamos que al contar los trabajos de X actor y X director se mezclen entre si
    */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Participante that)) return false;
        return Objects.equals(nombre_participante, that.nombre_participante) && Objects.equals(rol, that.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre_participante, rol);
    }
}
