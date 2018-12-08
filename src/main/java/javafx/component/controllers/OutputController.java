package javafx.component.controllers;

import javafx.component.OutputControllerInterface;
import javafx.component.model.component.Component;
import javafx.simulation.SimulationController;
import javafx.wire.Wire;
import model.Logic;

public class OutputController extends IoController implements OutputControllerInterface {


    public OutputController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
        simulationController.addOutput(this);
    }

    public void showOutputValue() {
        Component output = this.componentModel;
        Logic inputLogic = output.getInput(0).getLogic();


        if(inputLogic.isUndefined()) {
            ioShowValue(LOGIC_UNDEFINED, LOGIC_UNDEFINED_COLOUR);
        } else if(inputLogic.value()) {
            ioShowValue(LOGIC_1, LOGIC_1_COLOUR);
        } else {
            ioShowValue(LOGIC_0, LOGIC_0_COLOUR);
        }
    }

    public Wire getWire() {
        return componentModel.getInput(0).getWire();
    }
}
