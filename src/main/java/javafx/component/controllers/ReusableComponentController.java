package javafx.component.controllers;

import fileIO.Load;
import javafx.component.Synchronous;
import javafx.scene.Parent;
import javafx.simulation.SimulationController;
import utils.Fxml;
import utils.FxmlLoaderUtils;

public class ReusableComponentController extends ComponentController implements Synchronous {

    private SimulationController internalSimulation;

    private static String SIMULATION_FXML_PATH = "fxml/simulation.fxml";

    public void initialize() {
        Fxml fxml = FxmlLoaderUtils.loadFxml(SIMULATION_FXML_PATH);

        this.simulationController = (SimulationController)fxml.getController();

        Load.loadFromFile(simulationController);

        // connect Io ports
    }

    @Override
    public void processClockTick() {
        internalSimulation.clockTick();
    }

    @Override
    public void processGateDelay() {
        internalSimulation.gateDelay();
    }

}
