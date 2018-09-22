package simulator;

import javafx.component.ComponentController;
import javafx.fxml.FXMLLoader;
import javafx.main.MainController;
import javafx.scene.Parent;
import javafx.simulation.SimulationController;
import model.ComponentFactory;
import model.component.Component;

import java.io.IOException;
import java.util.ArrayList;

public class Simulator {

    private static ArrayList<ComponentController> components = new ArrayList<>();

    private static MainController mainController;

    private static SimulationController simulationController;

    // should be set at startup
    public static void setMainController(MainController mainController) {
        Simulator.mainController = mainController;
    }

    // should be set at startup
    public static void setSimulationController(SimulationController simulationController) {
        Simulator.simulationController = simulationController;
    }

    public static synchronized void clockTick() {
        // TODO
    }

    public static synchronized void gateDelay() {
        // TODO
    }

    public static void addComponent(String type, int xCoord, int yCoord) {

        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getClassLoader().getResource("fxml/component.fxml"));

        Parent componentNode;

        try {
            componentNode = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ComponentController controller = fxmlLoader.getController();

        simulationController.placeComponent(componentNode,xCoord,yCoord);

        Component componentModel = ComponentFactory.getComponent(type, xCoord, yCoord);

        controller.showComponent(componentModel);

    }

    public static synchronized void removeComponent() {
        // TODO
    }

    public static synchronized void addWire() {
        // TODO
    }

    public static synchronized void removeWire() {
        // TODO
    }
}
