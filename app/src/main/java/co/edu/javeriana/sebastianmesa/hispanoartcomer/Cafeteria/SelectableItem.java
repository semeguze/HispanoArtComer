package co.edu.javeriana.sebastianmesa.hispanoartcomer.Cafeteria;

//public class SelectableItem extends AlimentosCafeteria{
//    private boolean isSelected = false;
//
//
//    public SelectableItem(AlimentosCafeteria alimento,boolean isSelected) {
//        super(alimento.getNombreAlimento(),alimento.getPrecioAlimento());
//        this.isSelected = isSelected;
//    }
//
//
//    public boolean isSelected() {
//        return isSelected;
//    }
//
//    public void setSelected(boolean selected) {
//        isSelected = selected;
//    }
//}

public class SelectableItem extends Item{
    private boolean isSelected = false;


    public SelectableItem(Item item,boolean isSelected) {
        super(item.getName(),item.getSurname());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}