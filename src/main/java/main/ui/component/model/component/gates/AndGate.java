package main.ui.component.model.component.gates;

import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.port.Port;

public class AndGate extends Component {

    @Override
    public void processGateDelay() {
        boolean undefined = false;
        boolean value = true;

        for(Port input : getInputs()) {
            undefined |= input.getLogic().isUndefined();
            value &= input.getLogic().value();
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

    public AndGate(ComponentParameters componentParameters) {
        super(componentParameters);
    }
}
