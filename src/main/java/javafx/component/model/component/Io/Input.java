package javafx.component.model.component.Io;

import javafx.component.model.component.Component;
import model.Coordinates;
import model.Port;

public class Input extends Component {

  @Override
    public void processGateDelay() {
        // TODO: set nextLogic on ouput based on input
    }

    @Override
    public void reset() {
        getOutput(0).getLogic().setUndefined(false);
    }


    public Input(Coordinates coordinates, String uuid, String type, int noInputs, int noOutputs) {
        super(coordinates, uuid, type, noInputs, noOutputs);
        this.getOutput(0).getLogic().setValue(false);
        this.getOutput(0).getLogic().setUndefined(false);
    }
}
