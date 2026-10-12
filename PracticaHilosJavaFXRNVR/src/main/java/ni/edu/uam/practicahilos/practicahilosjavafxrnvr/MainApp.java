package ni.edu.uam.practicahilos.practicahilosjavafxrnvr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("procesador-view.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Monitor de Procesos Empresarial - NicaData Solutions");
        stage.setScene(scene);
        stage.show();

        ProcesadorController controller = loader.getController();

        // NUEVO: Interrumpe tanto el hilo de Clientes como el de Productos al cerrar la app
        stage.setOnCloseRequest(event -> controller.detenerAlCerrar());
    }

    public static void main(String[] args) {
        launch(args);
    }
}