package javafx.component.model.component.Io;

import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import model.Coordinates;
import model.Port;

public class Input extends Component {

  @Override
    public void processGateDelay() {
        // TODO: set nextLogic on ouput based on input
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
