package main.ui.simulation;

import main.fileIO.Load;
import main.model.Logic;
import main.ui.component.InputControllerInterface;
import main.ui.component.OutputControllerInterface;
import main.ui.component.Synchronous;
import main.ui.component.controllers.*;
import main.ui.component.model.component.Dff;
import main.ui.wire.Wire;

import java.util.ArrayList;
import java.util.Collections;
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
        return (dffControllers.values().iterator().next()).getStoredValue();
    }

    public Logic getDffLogic() {
        return ((Dff)(dffControllers.values().iterator().next()).getComponentModel()).getLogic();
    }

    public String getRegValues() {
        List<RegController> regControllers = new ArrayList<>();
        for(ReusableComponentController reusableController : reusableControllers.values()) {
            if(reusableController.getType().equals("reg1")) {
                regControllers.add((RegController) reusableController);
            }
        }
        Collections.sort(regControllers);
        int value = 0;
        for(int i=0;i<regControllers.size();i++) {
            RegController regController = regControllers.get(i);
            Logic logic = regController.getDffLogic();

            if(logic.isUndefined()) return "U";

            if(logic.value()) value += Math.pow(2,regControllers.size()-i-1);
        }
        return Integer.toString(value,16);
    }
}
