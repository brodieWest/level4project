package javafx.component.model.component.gates;

import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import model.Coordinates;
import model.Logic;
import model.Port;

public class OrGate extends Component {


    @Override
    public void processGateDelay() {
        boolean undefined = false;
        boolean value = false;

        for(Port input : getInputs()) {
            undefined |= input.getLogic().isUndefined();
            value |= input.getLogic().value();
        }

        getOutput(0).getLogic().setValue(value);
        getOutput(0).getLogic().setUndefined(undefined);
    }

    @Override
    public int getDefaultInputs() {
        return 2;
    }

    @Override
    public int getDefaultOutputs() {
        return 1;
    }

    public OrGate(ComponentParameters componentParameters) {
        super(componentParameters);
    }
}
