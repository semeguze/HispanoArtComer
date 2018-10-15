package co.edu.javeriana.sebastianmesa.hispanoartcomer.Cafeteria;

public class AlimentosCafeteria {

    private String name;
    private String surname;

    public AlimentosCafeteria(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        AlimentosCafeteria itemCompare = (AlimentosCafeteria) obj;
        if(itemCompare.getName().equals(this.getName()))
            return true;

        return false;
    }
}