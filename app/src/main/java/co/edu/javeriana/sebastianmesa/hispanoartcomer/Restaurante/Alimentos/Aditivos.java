package co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante.Alimentos;

import java.io.Serializable;

public class Aditivos implements Serializable {

    private String nombre;

    public Aditivos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
