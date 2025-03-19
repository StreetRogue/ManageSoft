module co.edu.unicauca.managesoft {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens co.edu.unicauca.managesoft to javafx.fxml;
    opens co.edu.unicauca.managesoft.entities to javafx.base;
    exports co.edu.unicauca.managesoft;
    
    requires java.sql;

}
