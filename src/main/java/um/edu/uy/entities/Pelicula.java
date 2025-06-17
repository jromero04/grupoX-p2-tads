package um.edu.uy.entities;

import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;

public class Pelicula implements Comparable<Pelicula>{
    private String idPelicula;
    private String tituloPelicula;
    private String idiomaOriginal;
    private double ingresos;
    private MyList<String> generos = new MyLinkedList<>();
    private Participante director;
    private Coleccion coleccion;
    private MyHash<String,Participante> elenco = new Hash<>(100);
    private MyList<Calificacion> calificaciones = new MyLinkedList<>();

    public Pelicula(String idPelicula, String tituloPelicula, String idiomaOriginal, double ingresos) {
        this.idPelicula = idPelicula;
        this.tituloPelicula = tituloPelicula;
        this.idiomaOriginal = idiomaOriginal;
        this.ingresos = ingresos;
    }

    public String getIdPelicula() {
        return idPelicula;
    }

    public String getTituloPelicula() {
        return tituloPelicula;
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

    public MyHash<String, Participante> getElenco() {
        return elenco;
    }

    public void agregarParticipante(Participante p) {
        String clave = p.getNombreParticipante() + "-" + p.getRol();
        if (!elenco.contains(clave)) {
            elenco.add(clave, p);
        }
    }

    public void agregarCalificacion(Calificacion c) {
        this.calificaciones.add(c);
    }

    public MyList<Calificacion> getCalificaciones() {
        return this.calificaciones;
    }

    public int getCantidadDeCalificaciones(){
        return this.calificaciones.size();
    }

    @Override
    public String toString() {
        return idPelicula + ", " + tituloPelicula + ", " + idiomaOriginal;
    }

    @Override
    public int compareTo(Pelicula otra) {
        return Integer.compare(this.getCantidadDeCalificaciones(), otra.getCantidadDeCalificaciones());
    }
}
