package main.ui.simulation;

import main.ui.component.InputControllerInterface;
import main.ui.component.OutputControllerInterface;
import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.InputController;
import main.ui.component.controllers.OutputController;
import main.ui.wire.Wire;

import java.util.ArrayList;
import java.util.List;

public class InternalController extends SimulationController {

    public InternalController() {
        super();
    }

    public void removeInputs() {
        for(InputControllerInterface input : inputControllers.values()) {
            //if(componentController instanceof InputController) {
                hideComponent((ComponentController)input);
            //}
        }
    }

    public List<Wire> getInputWires() {
        List<Wire> wires = new ArrayList<>();
        for(InputControllerInterface input : inputControllers.values()) {
            //if(componentController instanceof InputController) {
                //InputController inputController = (InputController) componentController;
                wires.add(input.getWire());
            //}
        }
        return wires;


    }

    public List<Wire> getOutputWires() {
        List<Wire> wires = new ArrayList<>();
        for(OutputControllerInterface output : outputControllers.values()) {
            //if(componentController instanceof OutputController) {
            //    OutputController outputController = (OutputController) componentController;
                wires.add(output.getWire());
            //}
        }
        return wires;


    }
}
