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
import service.Service;
import utils.IObserver;

import java.util.List;
import java.util.Set;

public class FarmacistController implements IObserver {

    public TableView tableView;
    public TableColumn tableColumnNrComanda;
    public TableColumn tableColumnData;
    public TableColumn tableColumnStatus;
    public Button buttonOnoreazaComanda;
    public TextField idOrderTextField;
    public TextField sectionTextField;
    public TextArea medicinesTextArea;

    private Service service;
    private Stage primaryStage;
    private ObservableList<Comanda> modelComenzi  = FXCollections.observableArrayList();

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void setService(Service service){
        this.service = service;
        service.addObserver(this);
        Set<Comanda> medicamentList = service.getAllComenziNeonorate();
        modelComenzi.setAll(medicamentList);
    }

    public void createMedicamenteTableView(){
        tableColumnNrComanda.setCellValueFactory(new PropertyValueFactory<Comanda, String>("nrComanda"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<Comanda, String>("data"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<Comanda, String>("status"));
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Comanda c = (Comanda) newSelection;
                loadDetails(c);
            }
        });
    }

    private void loadDetails(Comanda c){
        int orderID = c.getNrComanda();
        String section = c.getPersonalMedical().getSectie();
        String medicines = "";
        for (ElementComanda m : c.getList()) {
            medicines+=m.getMedicament().getNume()+" | quantity: " + m.getCantitate()+"\n";
        }
        idOrderTextField.setText(String.valueOf(orderID));
        medicinesTextArea.setText(medicines);
        sectionTextField.setText(section);
    }

    @FXML
    public void initialize(){
        createMedicamenteTableView();
        tableView.setItems(modelComenzi);
    }

    public void handleOnoreazaComanda(ActionEvent actionEvent) {
        Comanda comanda = (Comanda) tableView.getSelectionModel().getSelectedItem();
        service.updateStatusComanda(comanda);
        Set<Comanda> lc = service.getAllComenziNeonorate();
        modelComenzi.setAll(lc);
        medicinesTextArea.setText("");
        idOrderTextField.setText("");
        sectionTextField.setText("");
    }

    @Override
    public void update() {
        modelComenzi.setAll(service.getAllComenziNeonorate());
    }
}
