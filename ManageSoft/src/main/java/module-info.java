module co.edu.unicauca.managesoft {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens co.edu.unicauca.managesoft to javafx.fxml;
    exports co.edu.unicauca.managesoft;
    
    requires java.sql;

}
