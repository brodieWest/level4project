package javafx.component.controllers;

import fileIO.Load;
import javafx.component.Synchronous;
import javafx.component.model.component.Component;
import javafx.simulation.InternalController;
import javafx.simulation.SimulationController;
import javafx.wire.Wire;
import model.Coordinates;
import model.Port;
import utils.Fxml;
import utils.FxmlLoaderUtils;

import java.util.List;

public class ReusableComponentController extends ComponentController implements Synchronous {

    private InternalController internalSimulation;

    private static String SIMULATION_FXML_PATH = "fxml/internalSimulation.fxml";

    public ReusableComponentController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {
        super(simulationController, type, coordinates, uuid, noInputs, noOutputs);



        List<Wire> inputWires = internalSimulation.getInputWires();

        for(int i=0; i<inputWires.size();i++) {
            Wire wire = inputWires.get(i);
            Port inputPort = componentModel.getInput(i);

            wire.setInput(inputPort);
            inputPort.setWire(wire);
        }



        List<Wire> outputWires = internalSimulation.getOutputWires();

        for(int i=0; i<outputWires.size();i++) {
            Wire wire = outputWires.get(i);
            Port outputPort = componentModel.getOutput(i);

            wire.addOutput(outputPort);
            outputPort.setWire(wire);
        }
    }

    public void initialize() {

        this.internalSimulation = new InternalController();

        Load.loadFromFile(internalSimulation, "/fileExamples/reusable/nand"); //change this

    }

    @Override
    public void processClockTick() {
        internalSimulation.clockTick();
    }

    @Override
    public void processGateDelay() {
        internalSimulation.gateDelay();
    }

    @Override
    public void reset() {
        internalSimulation.resetSimulation();
    }

}
