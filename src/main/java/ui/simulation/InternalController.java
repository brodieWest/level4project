package ui.simulation;

import ui.component.controllers.ComponentController;
import ui.component.controllers.InputController;
import ui.component.controllers.OutputController;
import ui.wire.Wire;

import java.util.ArrayList;
import java.util.List;

public class InternalController extends SimulationController {

    public InternalController() {
        super();
    }

    public List<Wire> getInputWires() {
        List<Wire> wires = new ArrayList<>();
        for(ComponentController componentController : componentControllers.values()) {
            if(componentController instanceof InputController) {
                InputController inputController = (InputController) componentController;
                wires.add(inputController.getWire());
            }
        }
        return wires;


    }

    public List<Wire> getOutputWires() {
        List<Wire> wires = new ArrayList<>();
        for(ComponentController componentController : componentControllers.values()) {
            if(componentController instanceof OutputController) {
                OutputController outputController = (OutputController) componentController;
                wires.add(outputController.getWire());
            }
        }
        return wires;


    }
}
