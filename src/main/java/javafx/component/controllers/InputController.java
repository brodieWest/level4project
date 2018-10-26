package javafx.component.controllers;

import javafx.component.model.component.Component;
import javafx.simulation.SimulationController;
import javafx.wire.Wire;
import model.Coordinates;
import model.Logic;

public class InputController extends IoController {


    public InputController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {
        super(simulationController, type, coordinates, uuid, noInputs, noOutputs);
    }

    public void switchInputValue() {
        Component input = this.componentModel;
        Logic inputLogic = input.getOutput(0).getLogic();

        simulationController.resetSimulation();

        if(inputLogic.value()) {
            inputLogic.setValue(false);
            ioShowValue(LOGIC_0, LOGIC_0_COLOUR);
        } else {
            inputLogic.setValue(true);
            ioShowValue(LOGIC_1, LOGIC_1_COLOUR);
        }
        simulationController.wireDelay();
    }

    public Wire getWire() {
        return componentModel.getOutput(0).getWire();
    }
}
