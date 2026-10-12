module ni.edu.uam.practicahilos.practicahilosjavafxrnvr {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.practicahilos.practicahilosjavafxrnvr to javafx.fxml;
    exports ni.edu.uam.practicahilos.practicahilosjavafxrnvr;
}