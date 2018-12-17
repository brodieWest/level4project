package main.ui.component.model.component.gates;

import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.model.Logic;

public class NotGate extends Component {

    @Override
    public void processGateDelay() {

        Logic inputLogic = getInput(0).getLogic();
        Logic outputLogic = getOutput(0).getLogic();

        outputLogic.setValue(!inputLogic.value());
        outputLogic.setUndefined(inputLogic.isUndefined());
    }

    @Override
    public int getDefaultInputs() {
        return 1;
    }

    @Override
    public int getDefaultOutputs() {
        return 1;
    }

    public NotGate(ComponentParameters componentParameters) {
        super(componentParameters);
    }
}
