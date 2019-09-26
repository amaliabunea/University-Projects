package model;

public class ElementComanda {
    private Integer id;
    private Comanda comanda;
    private Medicament medicament;
    private Integer cantitate;

    public ElementComanda(){}
    public ElementComanda(Comanda comanda, Medicament medicament, Integer cantitate) {
        this.comanda = comanda;
        this.medicament = medicament;
        this.cantitate=cantitate;
    }

    public ElementComanda(Medicament medicament, Integer cantitate) {
        this.medicament=medicament;
        this.cantitate=cantitate;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
