package model;
import java.time.LocalDate;
import java.util.Set;

public class Comanda {

    private int nrComanda;
    private LocalDate data;
    private String status;
    private Set<ElementComanda> list;
    private PersonalMedical personalMedical;

    public Comanda(){}

    public Comanda(Set<ElementComanda> medicamentList, LocalDate data, PersonalMedical personalMedical) {
        this.list = medicamentList;
        this.personalMedical = personalMedical;
        this.status = "Neonorata";
        this.data = data;
    }

    public PersonalMedical getPersonalMedical() {
        return personalMedical;
    }

    public void setPersonalMedical(PersonalMedical personalMedical) {
        this.personalMedical = personalMedical;
    }

    public Set<ElementComanda> getList() {
        return list;
    }

    public void setList(Set<ElementComanda> list) {
        this.list = list;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getNrComanda() {
        return nrComanda;
    }

    public void setNrComanda(int nrComanda) {
        this.nrComanda = nrComanda;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final String[] medicamente = {""};
        for (ElementComanda elem : list) {
            System.out.println(list.size());
            System.out.println("heiiii"+elem.getMedicament());
        }
        list.forEach(x -> medicamente[0] += x.getMedicament().getNume() + "; " );

        return nrComanda  +
                " | " + data  +
                " | " + medicamente[0] +
                " | " + status;
    }
}
