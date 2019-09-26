package model;

import java.util.Set;

public class PersonalMedical {
    private String nume;
    private String password;
    private String sectie;
    //private Set<Medicament> listaMedicamente;
    private Set<Comanda> listaComenzi;

    public PersonalMedical() {}

    public PersonalMedical(String nume, String password) {
        this.nume = nume;
        this.password = password;
    }

    public PersonalMedical(String nume, String password, String sectie) {
        this.nume = nume;
        this.password = password;
        this.sectie=sectie;
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
//
//    public Set<Medicament> getListaMedicamente() {
//        return listaMedicamente;
//    }
//
//    public void setListaMedicamente(Set<Medicament> listaMedicamente) {
//        this.listaMedicamente = listaMedicamente;
//    }

    public Set<Comanda> getListaComenzi() {
        return listaComenzi;
    }

    public void setListaComenzi(Set<Comanda> listaComenzi) {
        this.listaComenzi = listaComenzi;
    }

    @Override
    public String toString() {
        return "PersonalMedical{" +
                "nume='" + nume + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getSectie() {
        return sectie;
    }

    public void setSectie(String sectie) {
        this.sectie = sectie;
    }
}
