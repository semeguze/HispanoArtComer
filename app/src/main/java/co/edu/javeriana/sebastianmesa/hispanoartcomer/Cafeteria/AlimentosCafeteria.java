package co.edu.javeriana.sebastianmesa.hispanoartcomer.Cafeteria;

public class AlimentosCafeteria {

    private String nombreAlimento;
    private String precioAlimento;

    public AlimentosCafeteria(String nombreAlimento, String precioAlimento) {
        this.nombreAlimento = nombreAlimento;
        this.precioAlimento = precioAlimento;
    }

    public String getNombreAlimento() {
        return nombreAlimento;
    }

    public void setNombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public String getPrecioAlimento() {
        return precioAlimento;
    }

    public void setPrecioAlimento(String precioAlimento) {
        this.precioAlimento = precioAlimento;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        AlimentosCafeteria itemCompare = (AlimentosCafeteria) obj;
        if(itemCompare.getNombreAlimento().equals(this.getNombreAlimento()))
            return true;

        return false;
    }
}