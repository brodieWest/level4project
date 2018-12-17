module main.ui.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires jeval;
    requires json;

    opens main.ui.main to javafx.fxml;
    opens main.ui.simulation to javafx.fxml;
    opens main.ui.component.controllers to javafx.fxml;
    opens main.ui.wire to javafx.fxml;
    opens main.ui to javafx.fxml;
    exports main.ui.main;
}