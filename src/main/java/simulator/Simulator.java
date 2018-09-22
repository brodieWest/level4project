package simulator;

import javafx.component.ComponentController;
import javafx.fxml.FXMLLoader;
import javafx.main.MainController;
import javafx.scene.Parent;
import javafx.simulation.SimulationController;
import model.component.ComponentInterface;
import model.component.NotGate;

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

    public static synchronized void addComponent(String type, int xCoord, int yCoord) {
        // TODO ComponentInterface newComponent = ComponentFactory(type, xCoord, yCoord)
        //    Parent componentFxml = FXMLLoader.load(Simulator.class.getClassLoader().getResource("fxml/component.fxml"));
    }

    // current plan is that after they are created, component controller will add themselves here
    // using this
    public static synchronized void addComponentController(ComponentController componentController) {
        // TODO
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
