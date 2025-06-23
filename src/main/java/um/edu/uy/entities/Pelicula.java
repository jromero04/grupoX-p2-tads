package um.edu.uy.entities;

import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Hash;
import um.edu.uy.tads.hash.MyHash;
import um.edu.uy.tads.heap.ArrayHeap;

import java.util.Arrays;

public class Pelicula implements Comparable<Pelicula>{
    private String idPelicula;
    private String tituloPelicula;
    private String idiomaOriginal;
    private double ingresos;

    private MyList<String> generos = new MyLinkedList<>();
    private MyHash<String,Participante> directores = new Hash<>();
    private MyList<Participante> elenco = new MyLinkedList<>();
    private MyList<Calificacion> calificaciones = new MyLinkedList<>();
    private Coleccion coleccion;

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

    public MyHash<String, Participante> getDirectores() {
        return directores;
    }

    public void setDirectores(MyHash<String, Participante> directores) {
        this.directores = directores;
    }

    public void agregarDirector(Participante nuevoDirector) {
        String clave = nuevoDirector.getNombreParticipante() + "-" + nuevoDirector.getRol();
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

    public MyList<Participante> getElenco() {
        return elenco;
    }

    public void agregarActor(Participante actor) {
        // Evitar duplicados
        for (int i = 0; i < elenco.size(); i++) {
            if (elenco.get(i).equals(actor)) {
                return;
            }
        }
        elenco.add(actor);
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
        return idPelicula + ", " + tituloPelicula + ", " + idiomaOriginal;
    }

    @Override
    public int compareTo(Pelicula otra) {
        return Integer.compare(this.getCantidadDeCalificaciones(), otra.getCantidadDeCalificaciones());
    }
}
