package javafx.component.controllers;

import fileIO.Load;
import javafx.component.Synchronous;
import javafx.component.model.component.Component;
import javafx.simulation.InternalController;
import javafx.simulation.SimulationController;
import javafx.wire.Wire;
import model.Port;
import utils.Fxml;
import utils.FxmlLoaderUtils;

import java.util.List;

public class ReusableComponentController extends ComponentController implements Synchronous {

    private InternalController internalSimulation;

    private static String SIMULATION_FXML_PATH = "fxml/internalSimulation.fxml";

    public void initialize() {
        Fxml fxml = FxmlLoaderUtils.loadFxml(SIMULATION_FXML_PATH);

        this.internalSimulation = (InternalController)fxml.getController();

        Load.loadFromFile(internalSimulation, "/fileExamples/reusable/nand");


    }

    @Override
    public void initialiseComponent(Component componentModel, SimulationController simulationController) {
        super.initialiseComponent(componentModel, simulationController);


        List<Wire> inputWires = internalSimulation.getInputWires();

        for(int i=0; i<inputWires.size();i++) {
            Wire wire = inputWires.get(i);
            Port inputPort = componentModel.getInput(i);

            wire.setInput(inputPort);
            inputPort.setWire(wire);
        }
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
