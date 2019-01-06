package main.ui.component.model.component.Io;

import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;

public class WordOutput extends Component {
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


    public WordOutput(ComponentParameters componentParameters) {
        super(componentParameters);
    }
}
