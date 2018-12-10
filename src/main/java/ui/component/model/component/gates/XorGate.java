package ui.component.model.component.gates;

import ui.component.model.component.Component;
import ui.component.model.component.ComponentParameters;
import model.Port;

public class XorGate extends Component {

    @Override
    public void processGateDelay() {
        boolean undefined = false;
        boolean contains1 = false;
        boolean contains0 = false;

        for(Port input : getInputs()) {
            undefined |= input.getLogic().isUndefined();
            if(input.getLogic().value()) {
                contains1 = true;
            }
            if(!input.getLogic().value()) {
                contains0 = true;
            }
        }

        boolean value = false;

        if(contains0 && contains1) {
            value = true;
        }

        getOutput(0).getLogic().setValue(value);
        getOutput(0).getLogic().setUndefined(undefined);
    }

    @Override
    public int getDefaultInputs() {
        return 2;
    }

    @Override
    public int getDefaultOutputs() {
        return 1;
    }

    public XorGate(ComponentParameters componentParameters) {
        super(componentParameters);
    }
}
