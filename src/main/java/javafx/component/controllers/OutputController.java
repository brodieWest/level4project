package javafx.component.controllers;

import javafx.component.model.component.Component;
import model.Logic;

public class OutputController extends IoController {

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
}
