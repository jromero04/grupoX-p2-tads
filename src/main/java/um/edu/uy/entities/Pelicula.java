package um.edu.uy.entities;

import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.heap.ArrayHeap;

import java.util.Arrays;

public class Pelicula implements Comparable<Pelicula>{
    private String id_pelicula;
    private String titulo_pelicula;
    private String idiomaOriginal;
    private double ingresos;
    private MyList<String> generos = new MyLinkedList<>();
    private MyHash<String,Participante> directores = new Hash<>(5);
    private Coleccion coleccion;
    private MyHash<String,Participante> elenco = new Hash<>(100);
    private MyList<Calificacion> calificaciones = new MyLinkedList<>();

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

    public MyHash<String, Participante> getDirectores() {
        return directores;
    }

    public void setDirectores(MyHash<String, Participante> directores) {
        this.directores = directores;
    }

    public void agregarDirector(Participante nuevoDirector) {
        String clave = nuevoDirector.getNombre_participante() + "-" + nuevoDirector.getRol();
        if (!directores.contains(clave)) {
            directores.add(clave, nuevoDirector);
        }
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
        String clave = p.getNombre_participante() + "-" + p.getRol();
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

    public double getPromedioCalificaciones(){
        if (calificaciones.size() == 0) return 0;
        double suma = 0;
        for (int i = 0; i< calificaciones.size(); i++){
            suma += calificaciones.get(i).getPuntaje();
        }
        return suma / calificaciones.size();
    }

    public double getMedianaCalificaciones(){
        int n = calificaciones.size();
        if (n==0) return 0;


        // Extraer los puntajes de las calificaciones
        double[] puntajes = new double[n];
        for (int i = 0; i < n; i++) {
            puntajes[i] = calificaciones.get(i).getPuntaje();
        }

        // Ordenar los puntajes
        Arrays.sort(puntajes);

        // Calcular la mediana
        if (n % 2 == 0) {
            return (puntajes[n / 2 - 1] + puntajes[n / 2]) / 2.0;
        } else {
            return puntajes[n / 2];
        }
    }
    @Override
    public String toString() {
        return id_pelicula + ", " + titulo_pelicula + ", " + idiomaOriginal;
    }

    @Override
    public int compareTo(Pelicula otra) {
        return Integer.compare(this.getCantidadDeCalificaciones(), otra.getCantidadDeCalificaciones());
    }
}
