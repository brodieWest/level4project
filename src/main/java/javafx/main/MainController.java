package javafx.main;

import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import simulator.Simulator;


public class MainController implements Controller {
    @FXML
    private MenuItem load;

    @FXML
    protected void loadFile() {
    }

    public void initialize(){
        Simulator.setMainController(this);
    }
}
