package main.ui.simulation;

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
        List<ComponentController> controllersToRemove = new ArrayList<>();
        for(ComponentController componentController : componentControllers.values()) {
            if(componentController instanceof InputController) {
                InputController inputController = (InputController) componentController;
                controllersToRemove.add(inputController);
            }
        }
        for(int i=0;i<controllersToRemove.size();i++) {
            removeComponent(controllersToRemove.remove(0));
        }
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
