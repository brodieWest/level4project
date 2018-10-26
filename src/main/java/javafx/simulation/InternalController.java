package javafx.simulation;

import javafx.component.controllers.ComponentController;
import javafx.component.controllers.InputController;
import javafx.component.controllers.OutputController;
import javafx.main.MainController;
import javafx.wire.Wire;

import java.util.ArrayList;
import java.util.List;

public class InternalController extends SimulationController {

    public InternalController() {
        super(null);
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
