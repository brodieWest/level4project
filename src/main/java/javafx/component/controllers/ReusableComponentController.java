package javafx.component.controllers;

import fileIO.Load;
import javafx.component.Synchronous;
import javafx.component.model.component.Component;
import javafx.simulation.InternalController;
import javafx.simulation.SimulationController;
import javafx.wire.Wire;
import model.Port;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ReusableComponentController extends ComponentController implements Synchronous {

    private Logger logger = LogManager.getLogger(ReusableComponentController.class);

    private InternalController internalSimulation;

    private static String REUSABLE_FILE_PATH = "/fileExamples/reusable/";

    public ReusableComponentController(SimulationController  simulationController, Component componentModel) {
        super(simulationController, componentModel);

        this.internalSimulation = new InternalController();

        if(!Load.loadFromFile(internalSimulation, REUSABLE_FILE_PATH + componentModel.getStringIdentifier())) {
            logger.error(String.format("Failed to build internal simulation for %s", componentModel.getStringIdentifier()));
        }

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

    @Override
    public void processClockTick() {
        internalSimulation.clockTick();
    }

    @Override
    public void processGateDelay() {
        internalSimulation.wireDelay();
        internalSimulation.gateDelay();
    }

    @Override
    public void reset() {
        super.reset();
        internalSimulation.resetSimulation();
    }

}
