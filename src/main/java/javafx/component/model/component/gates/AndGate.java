package javafx.component.model.component.gates;

import javafx.component.model.component.Component;
import model.Coordinates;
import model.Logic;
import model.Port;

public class AndGate extends Component {

    @Override
    public void processGateDelay() {
        Logic inputLogic0 = getInput(0).getLogic();
        Logic inputLogic1 = getInput(1).getLogic();
        Logic outputLogic = getOutput(0).getLogic();

        outputLogic.setValue(inputLogic0.value() && inputLogic1.value());
        outputLogic.setUndefined(inputLogic0.isUndefined() || inputLogic1.isUndefined());

    }

    public AndGate(Coordinates coordinates, String uuid, String type) {
        super(coordinates, uuid, type);
    }
}
