package um.edu.uy.entities;

public class Pelicula {
    private String id_pelicula;
    private String titulo_pelicula;
    private String idiomaOriginal;
    private int ingresos;
    //definir generos, coleccion, lista de participantes(elenco), director


    public Pelicula(String id_pelicula, String titulo_pelicula, String idiomaOriginal, int ingresos) {
        this.id_pelicula = id_pelicula;
        this.titulo_pelicula = titulo_pelicula;
        this.idiomaOriginal = idiomaOriginal;
        this.ingresos = ingresos;
    }

    public String getId_pelicula() {
        return id_pelicula;
    }

    public void setId_pelicula(String id_pelicula) {
        this.id_pelicula = id_pelicula;
    }

    public String getTitulo_pelicula() {
        return titulo_pelicula;
    }

    public void setTitulo_pelicula(String titulo_pelicula) {
        this.titulo_pelicula = titulo_pelicula;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public int getIngresos() {
        return ingresos;
    }

    public void setIngresos(int ingresos) {
        this.ingresos = ingresos;
    }

    // agregar un compare por id despues
}
