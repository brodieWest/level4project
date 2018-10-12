package javafx.component.model.component;

import javafx.component.Synchronous;
import model.Coordinates;
import model.Logic;

public class Dff extends Component implements Synchronous {

    Logic storedValue = new Logic();


    @Override
    public void processGateDelay() {
        getOutput(0).getLogic().copy(storedValue);
    }

    @Override
    public void processClockTick() {
        storedValue.copy(getInput(0).getLogic());
    }

    public Dff(Coordinates coordinates, String uuid, String type, int noInputs, int noOutputs) {
        super(coordinates, uuid, type, noInputs, noOutputs);
    }
}
