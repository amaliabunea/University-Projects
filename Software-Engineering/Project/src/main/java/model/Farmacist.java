package model;

import java.util.Set;

public class Farmacist {

    private String nume;
    private String password;
    private Set<Comanda> listaComenzi;
    public Farmacist() {};

    public Farmacist(String nume, String password) {
        this.nume = nume;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }


    public Set<Comanda> getListaComenzi() {
        return listaComenzi;
    }

    public void setListaComenzi(Set<Comanda> listaComenzi) {
        this.listaComenzi = listaComenzi;
    }


}
