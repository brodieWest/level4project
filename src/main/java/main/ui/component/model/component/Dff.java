package main.ui.component.model.component;

import main.ui.component.Synchronous;
import main.model.Logic;

public class Dff extends Component implements Synchronous {

    private Logic storedValue = new Logic();


    @Override
    public void processGateDelay() {

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
        if(!getInput(0).getLogic().isUndefined()) {
            storedValue.copy(getInput(0).getLogic());
        }
    }

    @Override
    public void wireDelay() {
        getOutput(0).getLogic().copy(storedValue);
    }

    public Dff(ComponentParameters componentParameters) {
        super(componentParameters);
        storedValue.setUndefined(false);
        storedValue.setValue(false);
    }

    public Logic getStoredValue() {
        return storedValue;
    }

    @Override
    public int getPathDepth() {
        return 1;
    }
}
