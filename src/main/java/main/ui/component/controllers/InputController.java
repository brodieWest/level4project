package main.ui.component.controllers;

import main.model.SimulationMode;
import main.ui.component.model.component.Component;
import main.ui.simulation.MainSimulationController;
import main.ui.simulation.SimulationController;
import main.ui.wire.Wire;
import main.model.Logic;

public class InputController extends IoController {


    public InputController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
    }

    public void switchInputValue() {
        if(((MainSimulationController)simulationController).getSimulationMode() != SimulationMode.SIMULATE) return;
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

    @Override
    public void setDeletable(boolean deletable) {
        super.setDeletable(deletable);
        if(!deletable) {
            getComponent().setOnMouseClicked(event -> switchInputValue());
        }
    }

    public Wire getWire() {
        return componentModel.getOutput(0).getWire();
    }
}
