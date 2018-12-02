package javafx.component.model.component.Io;

import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import model.Coordinates;

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
