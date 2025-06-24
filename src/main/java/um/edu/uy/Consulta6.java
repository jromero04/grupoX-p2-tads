package um.edu.uy;

import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;

public class Consulta6 implements Comparable<Consulta6> {
    private String genero;
    private int cantidadEvaluaciones;
    private int cantidadEvaluacionesUsuarioTop;
    private String idUsuario;

    public Consulta6(String genero,int cantidadEvaluaciones) {
        this.genero = genero;
        this.cantidadEvaluaciones = cantidadEvaluaciones;
    }

    public String getGenero(){return genero;}

    public int getCantidadEvaluaciones(){return cantidadEvaluaciones;}

    public int getCantidadEvaluacionesUsuarioTop(){return cantidadEvaluacionesUsuarioTop;}

    public String getIdusuario(){return idUsuario;}


    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setCantidadEvaluaciones(int cantidadEvaluaciones) {
        this.cantidadEvaluaciones = cantidadEvaluaciones;
    }

    public void setCantidadEvaluacionesUsuarioTop(int cantidadEvaluacionesUsuarioTop) {
        this.cantidadEvaluacionesUsuarioTop = cantidadEvaluacionesUsuarioTop;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }



    public int compareTo(Consulta6 otra) {
        return Double.compare(this.getCantidadEvaluaciones(), otra.getCantidadEvaluaciones());
    }

}
