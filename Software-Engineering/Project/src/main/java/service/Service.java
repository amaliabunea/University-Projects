package service;

import model.Comanda;
import model.ElementComanda;
import model.Medicament;
import model.PersonalMedical;
import repository.ComandaRepository;
import repository.FarmacistRepository;
import repository.MedicamentRepository;
import repository.PersonalMedicalRepository;
import utils.IObservable;
import utils.IObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Service implements IObservable {

    private FarmacistRepository farmacistRepository;
    private MedicamentRepository medicamentRepository;
    private ComandaRepository comandaRepository;
    private PersonalMedicalRepository personalMedicalRepository;
    private List<IObserver> observers = new ArrayList<>();

    public Service(FarmacistRepository farmacistRepository, MedicamentRepository medicamentRepository, ComandaRepository comandaRepository, PersonalMedicalRepository personalMedicalRepository) {
        this.farmacistRepository = farmacistRepository;
        this.medicamentRepository = medicamentRepository;
        this.comandaRepository = comandaRepository;
        this.personalMedicalRepository = personalMedicalRepository;
    }

    public boolean loginFarmacie(String nume, String password){
        return farmacistRepository.findOne(nume, password);
    }

    public boolean loginPersonalMedical(String nume, String password){
        return personalMedicalRepository.findByUsenameAndPassword(nume, password);
    }

    public List<Medicament> getAllMedicamente(){
        return medicamentRepository.getAll();
    }

    public PersonalMedical getPersonalMedical(String username, String password){
        return personalMedicalRepository.findOne(username, password);
    }

    public Set<Comanda> getAllComenziForSectie(PersonalMedical personalMedical){
        return comandaRepository.getAllComenziForSectie(personalMedical);
    }
    public Set<Comanda> getAllComenziNeonorate(){
        return comandaRepository.getAllComenziNeonorate();
    }

    public void adaugaComanda(Comanda comanda){
        comandaRepository.save(comanda);
        notifyObservers();
    }
    public void stergeComanda(Comanda comanda){
        comandaRepository.delete(comanda);
        notifyObservers();
    }

    public void updateStatusComanda(Comanda comanda){
        comandaRepository.updateStatus(comanda);
        notifyObservers();
    }

    public void updateComanda(Comanda comanda) {
        comandaRepository.update(comanda);
        notifyObservers();
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(x -> x.update());
    }

}
