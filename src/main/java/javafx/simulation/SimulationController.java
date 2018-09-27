package javafx.simulation;

import javafx.Controller;
import javafx.component.ComponentController;
import javafx.component.model.ComponentFactory;
import javafx.component.model.Coordinate;
import javafx.component.model.Wire;
import javafx.component.model.component.Component;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.main.MainController;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.simulation.model.Simulator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimulationController implements Controller {
    @FXML
    AnchorPane simulationPane;


    private Map<String,ComponentController> componentControllers = new HashMap<>();

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public synchronized void clockTick() {
        // TODO
    }

    public synchronized void gateDelay() {
        // TODO
    }

    public void addComponent(String type, Coordinate coordinate) {

        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getClassLoader().getResource("fxml/components/" + type + ".fxml"));

        Parent componentNode;

        try {
            componentNode = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ComponentController componentController = fxmlLoader.getController();

        placeComponent(componentNode,coordinate);

        Component componentModel = ComponentFactory.getComponent(type, coordinate);

        componentController.initialiseComponent(componentModel);

        // TODO: should check that uuid does not already exist
        componentControllers.put(componentModel.getUuid(), componentController);

    }

    public void removeComponent() {
        // TODO
    }

    public Wire addWire() {
        //Wire wire = new Wire();
        return null;
    }

    public void removeWire() {
        // TODO
    }

    private void placeComponent(Parent componentNode, Coordinate coordinate) {
        simulationPane.getChildren().add(componentNode);
        AnchorPane.setTopAnchor(componentNode, coordinate.getX()*100.0);
        AnchorPane.setLeftAnchor(componentNode, coordinate.getY()*100.0);
    }
}
