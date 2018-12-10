package utils.fxml;

import javafx.scene.Parent;
import ui.Controller;

public class Fxml {
    private Controller controller;
    private Parent node;

    Fxml(Controller controller, Parent node) {
        this.controller = controller;
        this.node = node;
    }

    public Controller getController() {
        return controller;
    }

    public Parent getNode() {
        return node;
    }
}
