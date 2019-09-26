package main;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Medicament;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.ComandaRepository;
import repository.FarmacistRepository;
import repository.MedicamentRepository;
import repository.PersonalMedicalRepository;
import service.Service;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
    }

    private void init(Stage primaryStage) throws IOException {

        initialize();
        FarmacistRepository farmacistRepository = new FarmacistRepository(sessionFactory);
        MedicamentRepository medicamentRepository = new MedicamentRepository(sessionFactory);
        PersonalMedicalRepository personalMedicalRepository = new PersonalMedicalRepository(sessionFactory);
        ComandaRepository comandaRepository = new ComandaRepository(sessionFactory);
        Service service = new Service(farmacistRepository, medicamentRepository, comandaRepository, personalMedicalRepository);

        //login
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/LoginView.fxml"));
        Parent rootLayout = loader.load();
        LoginController loginController = loader.getController();
        Scene loginScene = new Scene(rootLayout);
        loginController.setPrimaryStage(primaryStage);
        loginController.setService(service);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

}
