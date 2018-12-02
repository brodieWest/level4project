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
    public int getDefaultInputs() {
        return 1;
    }

    @Override
    public int getDefaultOutputs() {
        return 1;
    }

    @Override
    public void processClockTick() {
        storedValue.copy(getInput(0).getLogic());
    }

    public Dff(ComponentParameters componentParameters) {
        super(componentParameters);
    }

    @Override
    public int getPathDepth() {
        return 1;
    }
}
