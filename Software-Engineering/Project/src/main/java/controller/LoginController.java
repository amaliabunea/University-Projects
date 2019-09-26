package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Farmacist;
import model.PersonalMedical;
import service.Service;
import utils.WarningBox;

import java.io.IOException;

public class LoginController {

    public TextField usernameTextField;
    public Button loginFarmacistButton;
    public Button loginPersonalButton;
    public PasswordField passwordTextField;

    private Service service;
    private Stage primaryStage;


    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void setService(Service service){
        this.service = service;
    }


    public void loginFarmacist(ActionEvent actionEvent) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        Farmacist farmacist = new Farmacist(username, password);
        if(service.loginFarmacie(username, password)){
            Stage secondStage = new Stage();

            FXMLLoader farmacieLoader = new FXMLLoader();
            farmacieLoader.setLocation(getClass().getResource("/View/FarmacieView.fxml"));
            Parent farmacieRoot = farmacieLoader.load();
            Scene farmacieScene = new Scene(farmacieRoot);
            FarmacistController farmacistController = farmacieLoader.getController();
            farmacistController.setPrimaryStage(secondStage);
            farmacistController.setService(service);
            secondStage.setScene(farmacieScene);
            secondStage.show();

            usernameTextField.clear();
            passwordTextField.clear();
        }
        else
            {
                WarningBox warningBox = new WarningBox();
                warningBox.warningMessage(Alert.AlertType.WARNING,"WARNING", "DATE DE AUTENTIFICARE INCORECTE ! VA RUGAM INTRODUCETI DIN NOU DATELE.");
        }
    }

    public void loginPersonal(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if(service.loginPersonalMedical(username, password)){
            Stage stage = new Stage();

            FXMLLoader personalMedicalLoader = new FXMLLoader();
            personalMedicalLoader.setLocation(getClass().getResource("/View/PersonalMedicalView.fxml"));
            Parent menuRoot = null;
            try {
                menuRoot = personalMedicalLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene sectieScene = new Scene(menuRoot);
            PersonalMedicalController personalMedicalController = personalMedicalLoader.getController();
            personalMedicalController.setPersonalMedical(username, password);
            personalMedicalController.setService(service);
            stage.setScene(sectieScene);
            PersonalMedical medic = service.getPersonalMedical(username, password);
            stage.setTitle(medic.getSectie());
            stage.show();
            service.addObserver(personalMedicalController);
            usernameTextField.clear();
            passwordTextField.clear();
        }
        else
        {
            WarningBox warningBox = new WarningBox();
            warningBox.warningMessage(Alert.AlertType.WARNING,"WARNING", "DATE DE AUTENTIFICARE INCORECTE ! VA RUGAM INTRODUCETI DIN NOU DATELE.");
        }

    }
}
