package ui.component.controllers;

import ui.component.model.component.Component;
import ui.simulation.SimulationController;
import ui.wire.Wire;
import model.Logic;

public class InputController extends IoController {


    public InputController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
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
