module co.edu.unicauca.managesoft {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens co.edu.unicauca.managesoft to javafx.fxml;
<<<<<<< Updated upstream
    opens co.edu.unicauca.managesoft.entities to javafx.base;
    exports co.edu.unicauca.managesoft;
    
=======
    opens co.edu.unicauca.managesoft.entities to javafx.base, com.google.gson;
    opens co.edu.unicauca.managesoft.infra to javafx.base, com.google.gson;
    exports co.edu.unicauca.managesoft;

    requires com.rabbitmq.client;
    requires com.google.gson;
>>>>>>> Stashed changes
    requires java.sql;
}
