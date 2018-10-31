package javafx.component.model.component;

import javafx.component.Synchronous;
import model.Coordinates;

public class ReusableComponent extends Component implements Synchronous {


    ReusableComponent(Coordinates coordinates, String uuid, String type, int noInputs, int noOutputs) {
        super(coordinates, uuid, type, noInputs, noOutputs);
    }

    @Override
    public void processGateDelay() {
    }

    @Override
    public void processClockTick() {

    }
}
