package javafx.component.model.component.gates;

import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import model.Coordinates;
import model.Logic;
import model.Port;

public class NotGate extends Component {

    @Override
    public void processGateDelay() {

        Logic inputLogic = getInput(0).getLogic();
        Logic outputLogic = getOutput(0).getLogic();

        outputLogic.setValue(!inputLogic.value());
        outputLogic.setUndefined(inputLogic.isUndefined());
    }

    @Override
    public int getDefaultInputs() {
        return 1;
    }

    @Override
    public int getDefaultOutputs() {
        return 1;
    }

    public NotGate(ComponentParameters componentParameters) {
        super(componentParameters);
    }
}
