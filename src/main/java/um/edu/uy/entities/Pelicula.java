package um.edu.uy.entities;

import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;

public class Pelicula {
    private String id_pelicula;
    private String titulo_pelicula;
    private String idiomaOriginal;
    private double ingresos;
    private MyList<String> generos = new MyLinkedList<>();
    private Participante director;
    private Coleccion coleccion;
    private MyList<Participante> elenco = new MyLinkedList<>();
    private MyList<Calificacion> calificaciones = new MyLinkedList<>();
    // no se si no hace falta agregar una lista de calificaciones

    public Pelicula(String id_pelicula, String titulo_pelicula, String idiomaOriginal, double ingresos) {
        this.id_pelicula = id_pelicula;
        this.titulo_pelicula = titulo_pelicula;
        this.idiomaOriginal = idiomaOriginal;
        this.ingresos = ingresos;
    }

    public String getId_pelicula() {
        return id_pelicula;
    }

    public String getTitulo_pelicula() {
        return titulo_pelicula;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public MyList<String> getGeneros() {
        return generos;
    }

    public void agregarGenero(String genero) {
        this.generos.add(genero);
    }

    public Participante getDirector() {
        return director;
    }

    public void setDirector(Participante director) {
        this.director = director;
    }

    public Coleccion getColeccion() {
        return coleccion;
    }

    public void setColeccion(Coleccion coleccion) {
        this.coleccion = coleccion;
    }

    public MyList<Participante> getElenco() {
        return elenco;
    }

    public void agregarParticipante(Participante p) {
        this.elenco.add(p);
    }

    public void agregarCalificacion(Calificacion c) {
        this.calificaciones.add(c);
    }

    public MyList<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public int getCantidadDeCalificaciones(){
        return this.calificaciones.size();
    }

    @Override
    public String toString() {
        return id_pelicula + ", " + titulo_pelicula + ", " + idiomaOriginal;
    }
}
