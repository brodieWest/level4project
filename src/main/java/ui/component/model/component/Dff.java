package ui.component.model.component;

import ui.component.Synchronous;
import model.Logic;

public class Dff extends Component implements Synchronous {

    private Logic storedValue = new Logic();


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
        if(!getInput(0).getLogic().isUndefined()) {
            storedValue.copy(getInput(0).getLogic());
        }
    }

    public Dff(ComponentParameters componentParameters) {
        super(componentParameters);
        storedValue.setUndefined(false);
        storedValue.setValue(false);
    }

    @Override
    public int getPathDepth() {
        return 1;
    }
}
