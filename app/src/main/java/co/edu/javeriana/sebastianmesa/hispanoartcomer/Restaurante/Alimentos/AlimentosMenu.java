package co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante.Alimentos;


import org.threeten.bp.LocalDate;

import java.util.ArrayList;

/**
 * Created by Sebas on 18/05/16.
 */
public class AlimentosMenu {
    private String tipo;
    private int thumbnail;
    private LocalDate fecha;
    private ArrayList<Aditivos> aditivos;

    private String platoPrincipal;
    private String platoSegundario;
    private String platoPostre;
    private String bebidaAlmuerzo;

    private String plato;
    private String bebida;



    public AlimentosMenu() {
    }

    public AlimentosMenu(String tipo, String platoPrincipal, String platoSegundario, String platoPostre, String bebidaAlmuerzo, int thumbnail, LocalDate fecha, ArrayList<Aditivos> aditivos) {
        this.tipo = tipo;
        this.platoPrincipal = platoPrincipal;
        this.platoSegundario = platoSegundario;
        this.platoPostre = platoPostre;
        this.bebidaAlmuerzo = bebidaAlmuerzo;
        this.thumbnail = thumbnail;
        this.fecha = fecha;
        this.aditivos = aditivos;
    }

    public AlimentosMenu(String tipo, String plato, String bebida, int thumbnail, LocalDate fecha, ArrayList<Aditivos> aditivos) {
        this.tipo = tipo;
        this.thumbnail = thumbnail;
        this.fecha = fecha;
        this.aditivos = aditivos;
        this.plato = plato;
        this.bebida = bebida;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPlatoPrincipal() {
        return platoPrincipal;
    }

    public void setPlatoPrincipal(String platoPrincipal) {
        this.platoPrincipal = platoPrincipal;
    }

    public String getPlatoSegundario() {
        return platoSegundario;
    }

    public void setPlatoSegundario(String platoSegundario) {
        this.platoSegundario = platoSegundario;
    }

    public String getPlatoPostre() {
        return platoPostre;
    }

    public void setPlatoPostre(String platoPostre) {
        this.platoPostre = platoPostre;
    }

    public String getBebidaAlmuerzo() {
        return bebidaAlmuerzo;
    }

    public void setBebidaAlmuerzo(String bebidaAlmuerzo) {
        this.bebidaAlmuerzo = bebidaAlmuerzo;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Aditivos> getAditivos() {
        return aditivos;
    }

    public void setAditivos(ArrayList<Aditivos> aditivos) {
        this.aditivos = aditivos;
    }

    public String getPlato() {
        return plato;
    }

    public void setPlato(String plato) {
        this.plato = plato;
    }

    public String getBebida() {
        return bebida;
    }

    public void setBebida(String bebida) {
        this.bebida = bebida;
    }
}