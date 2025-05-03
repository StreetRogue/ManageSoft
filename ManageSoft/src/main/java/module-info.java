module co.edu.unicauca.managesoft {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.unicauca.managesoft to javafx.fxml;
    opens co.edu.unicauca.managesoft.entities to javafx.base, com.google.gson;
    opens co.edu.unicauca.managesoft.infra to javafx.base, com.google.gson;
    exports co.edu.unicauca.managesoft;

    requires com.rabbitmq.client;
    requires com.google.gson;
    requires java.sql;
}
