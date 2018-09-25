package javafx.simulation;

import javafx.Controller;
import javafx.component.ComponentController;
import javafx.component.model.ComponentFactory;
import javafx.component.model.Wire;
import javafx.component.model.component.Component;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.main.MainController;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.simulation.model.Simulator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimulationController implements Controller {
    @FXML
    GridPane simulationGridPane;


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

    public void addComponent(String type, int xCoord, int yCoord) {

        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getClassLoader().getResource("fxml/component.fxml"));

        Parent componentNode;

        try {
            componentNode = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ComponentController componentController = fxmlLoader.getController();

        placeComponent(componentNode,xCoord,yCoord);

        Component componentModel = ComponentFactory.getComponent(type, xCoord, yCoord);

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

    private void placeComponent(Parent componentNode, int xCoord, int yCoord) {
        GridPane.setConstraints(componentNode, yCoord, xCoord);
        simulationGridPane.getChildren().add(componentNode);
    }
}
