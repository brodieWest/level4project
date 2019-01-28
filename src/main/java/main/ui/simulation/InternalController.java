package main.ui.simulation;

import main.ui.component.InputControllerInterface;
import main.ui.component.OutputControllerInterface;
import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.DffController;
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
            hideComponent((ComponentController)input);
        }
    }

    public String getDffValue() {
        if(dffControllers.isEmpty()) return "";
        return ((DffController)dffControllers.values().iterator().next()).getStoredValue();
    }

}
