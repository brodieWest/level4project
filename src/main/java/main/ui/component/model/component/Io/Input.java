package main.ui.component.model.component.Io;

import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;

public class Input extends Component {

  @Override
    public void processGateDelay() {
    }

    @Override
    public int getDefaultInputs() {
        return 0;
    }

    @Override
    public int getDefaultOutputs() {
        return 1;
    }

    @Override
    public void reset() {
        getOutput(0).getLogic().setUndefined(false);
    }


    public Input(ComponentParameters componentParameters) {
        super(componentParameters);
        this.getOutput(0).getLogic().setValue(false);
        this.getOutput(0).getLogic().setUndefined(false);
    }
}
