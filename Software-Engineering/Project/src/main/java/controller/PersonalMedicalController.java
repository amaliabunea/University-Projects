package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Comanda;
import model.ElementComanda;
import model.Medicament;
import model.PersonalMedical;
import service.Service;
import utils.IObserver;
import utils.WarningBox;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonalMedicalController implements IObserver {

    public Button buttonAdaugaComanda;
    public TableView tableViewMedicamente;
    public TableColumn tableColumnNume;
    public TableColumn tableColumnProspect;
    public TableColumn tableColumnPret;
    public DatePicker datePicker;
    public ListView listViewComenzi;
    public Button buttonStergeComanda;
    public Button buttonModificaComanda;
    public TextArea medicinesTextArea, quantitesTextArea;

    private String username;
    private String password;
    private Service service;
    private Stage primaryStage;
    private ObservableList<Medicament> modelMedicament  = FXCollections.observableArrayList();
    private ObservableList<Comanda> listViewModelComenzi  = FXCollections.observableArrayList();
    private Comanda selectedOrder;
    private Medicament selectedMedicine;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void setService(Service service){
        this.service = service;

        List<Medicament> medicamentList = service.getAllMedicamente();
        System.out.println(medicamentList);
        modelMedicament.setAll(medicamentList);

        PersonalMedical personalMedical = new PersonalMedical(username, password);
        Set<Comanda> comandaList = service.getAllComenziForSectie(personalMedical);
        if(comandaList != null){
            listViewModelComenzi.setAll(comandaList);
        }
    }

    public void setPersonalMedical(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void createMedicamenteTableView(){
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Medicament, String>("nume"));
        tableColumnProspect.setCellValueFactory(new PropertyValueFactory<Medicament, String>("prospect"));
        tableColumnPret.setCellValueFactory(new PropertyValueFactory<Medicament, String>("pret"));
        listViewComenzi.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedOrder = (Comanda) newSelection;
                loadDetails();
            }
        });
        tableViewMedicamente.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedMedicine = (Medicament) newSelection;
                loadMedicineDetails();
            }
        });
    }

    private void loadDetails(){
        String medicines = "";
        for (ElementComanda m : selectedOrder.getList()) {
            medicines+=m.getMedicament().getNume()+ " | quantity: "+m.getCantitate()+" \n";
        }
        medicinesTextArea.setText(medicines);
    }

    private void loadMedicineDetails() {
        quantitesTextArea.appendText(selectedMedicine.getNume()+": \n");
    }

    @FXML
    public void initialize(){
        createMedicamenteTableView();
        tableViewMedicamente.setItems(modelMedicament);
        tableViewMedicamente.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listViewComenzi.setItems(listViewModelComenzi);
    }

    private List<Integer> getQuantities() {
        return getQuantities(quantitesTextArea);
    }

    private List<Integer> getQuantitiesForUpdate() {
        return getQuantities(medicinesTextArea);
    }

    private List<Integer> getQuantities(TextArea textArea) {
        List<Integer> quantites = new ArrayList<>();
        textArea.appendText("\n");
        String parts[] = textArea.getText().split(":");
        int i=1;
        while (i<parts.length) {
            quantites.add(Integer.parseInt(parts[i].split("\\r?\\n")[0].trim()));
            i+=1;
        }
        return quantites;
    }

    public void handleAdaugaComanda(ActionEvent actionEvent) {
        LocalDate date = datePicker.getValue();
        Set<Medicament> listMedicament = (Set<Medicament>) tableViewMedicamente.getSelectionModel().getSelectedItems().stream().collect(Collectors.toSet());
        List<Integer> quantities = getQuantities();
        int i=0;
        Set<ElementComanda> list = new HashSet<>();
        for (Medicament m : listMedicament) {
            ElementComanda elem = new ElementComanda(m, quantities.get(i));
            list.add(elem);
            i++;
        }
        String err = "";
        if(listMedicament.size() == 0){
            err += "Va rugam selectati medicamentele dorite! \n";
        }
        if(date == null){
            err += "Va rugam alegeti data comenzii ! ";
        }

        if(err.length() != 0){
            WarningBox warningBox = new WarningBox();
            warningBox.warningMessage(Alert.AlertType.WARNING,"WARNING", err);
        }
        else {
            PersonalMedical personalMedical = service.getPersonalMedical(username, password);
            Comanda comanda = new Comanda(list, date, personalMedical);
            service.adaugaComanda(comanda);
            quantitesTextArea.setText("");
        }
    }

    public void handleStergeComanda(ActionEvent actionEvent) {

        Comanda comanda = (Comanda) listViewComenzi.getSelectionModel().getSelectedItem();
        if(comanda == null){
            WarningBox warningBox = new WarningBox();
            warningBox.warningMessage(Alert.AlertType.WARNING,"WARNING", "Selectati comanda pe care doriti sa o stergeti! ");
        }
        else {
            service.stergeComanda(comanda);
            medicinesTextArea.setText("");
        }

    }

    @Override
    public void update() {
        listViewModelComenzi.setAll(service.getAllComenziForSectie(new PersonalMedical(username, password)));
        modelMedicament.setAll(service.getAllMedicamente());
    }

    public void handleModificaComanda(ActionEvent actionEvent) {
        Comanda comanda = (Comanda) listViewComenzi.getSelectionModel().getSelectedItem();

         if(comanda.getStatus().equals("Neonorata")){
             List<Integer> quantities = getQuantitiesForUpdate();
             int i=0;
             for (ElementComanda elem : comanda.getList()) {
                 elem.setCantitate(quantities.get(i));
                 i++;
             }
            service.updateComanda(comanda);
        }
        else{
            WarningBox warningBox = new WarningBox();
            warningBox.warningMessage(Alert.AlertType.WARNING,"WARNING", "Comanda nu mai poate fi modificata, ea a fost deja onorata !");
        }


    }

}