package co.edu.javeriana.sebastianmesa.hispanoartcomer.Peso;

public class RegistroPesos {

    private String fecha;
    private Long peso;

    public RegistroPesos(String fecha, Long peso) {
        this.fecha = fecha;
        this.peso = peso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }
}
