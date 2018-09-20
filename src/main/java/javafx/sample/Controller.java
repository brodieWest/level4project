package javafx.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;


public class Controller {
    @FXML
    MenuItem load;

    @FXML
    protected void loadFile(ActionEvent event) {
        System.out.println("working");
    }
}
