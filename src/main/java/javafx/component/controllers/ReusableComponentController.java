package javafx.component.controllers;

import fileIO.Load;
import javafx.component.Synchronous;
import javafx.simulation.SimulationController;
import utils.Fxml;
import utils.FxmlLoaderUtils;

public class ReusableComponentController extends ComponentController implements Synchronous {

    private SimulationController internalSimulation;

    private static String SIMULATION_FXML_PATH = "fxml/simulation.fxml";

    public void initialize() {
        Fxml fxml = FxmlLoaderUtils.loadFxml(SIMULATION_FXML_PATH);

        this.internalSimulation = (SimulationController)fxml.getController();

        Load.loadFromFile(internalSimulation, "/fileExamples/reusable/nand");

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
