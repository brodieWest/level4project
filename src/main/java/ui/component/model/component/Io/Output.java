package ui.component.model.component.Io;

import ui.component.model.component.Component;
import ui.component.model.component.ComponentParameters;

public class Output extends Component {
    @Override
    public void processGateDelay() {
        // TODO: set nextLogic on ouput based on input
    }

    @Override
    public int getDefaultInputs() {
        return 1;
    }

    @Override
    public int getDefaultOutputs() {
        return 0;
    }


    public Output(ComponentParameters componentParameters) {
        super(componentParameters);
    }
}
