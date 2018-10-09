package javafx.component.controllers;

import javafx.component.model.component.Component;
import model.Logic;

public class InputController extends IoController {


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
}
