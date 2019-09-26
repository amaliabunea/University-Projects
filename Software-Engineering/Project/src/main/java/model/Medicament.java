package model;

import java.util.HashSet;
import java.util.Set;

public class Medicament {

    private String nume;
    private Double pret;
    private String prospect;
    private Integer cantitate;
    private Set<ElementComanda> list;

    public Medicament(){}

    public Medicament(String nume, Double pret, String prospect) {
        this.nume = nume;
        this.pret = pret;
        this.prospect=prospect;
        this.list = new HashSet<>();
    }

    public Set<ElementComanda> getList() {
        return list;
    }

    public void setList(Set<ElementComanda> list) {
        this.list = list;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "nume='" + nume + '\'' +
                '}';
    }

    public Double getPret() {
        return pret;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public String getProspect() {
        return prospect;
    }

    public void setProspect(String prospect) {
        this.prospect = prospect;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }
}
