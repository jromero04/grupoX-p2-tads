package um.edu.uy.AuxiliaresConsulta;

public class TopUsuarioGenero implements Comparable<TopUsuarioGenero> {
    private String genero;
    private int cantidadEvaluaciones;
    private int cantidadEvaluacionesUsuarioTop;
    private String idUsuario;

    public TopUsuarioGenero(String genero, int cantidadEvaluaciones) {
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



    public int compareTo(TopUsuarioGenero usuarioCantidadEvaluaciones) {
        return Double.compare(this.getCantidadEvaluaciones(), usuarioCantidadEvaluaciones.getCantidadEvaluaciones());
    }

}
