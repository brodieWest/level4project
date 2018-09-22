package javafx.main;

import fileIO.Load;
import javafx.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import simulator.Simulator;

import java.io.IOException;


public class MainController implements Controller {
    @FXML
    private MenuItem load;

    @FXML
    protected void loadFile() {
        Load.loadFromFile("/FileFormatExample");

    }

    public void initialize(){
        Simulator.setMainController(this);
    }
}
