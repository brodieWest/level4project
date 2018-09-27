package utils;

import javafx.Controller;
import javafx.scene.Node;
import javafx.scene.Parent;

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
