package javafx.component.model.component.gates;

import javafx.component.model.component.Component;
import model.Coordinates;
import model.Logic;
import model.Port;

public class NotGate extends Component {

    @Override
    public String getStringIdentifier() {
        return "not";
    }

    @Override
    public void processGateDelay() {

        Logic inputLogic = getInput(0).getLogic();
        Logic outputLogic = getOutput(0).getLogic();

        outputLogic.setValue(!inputLogic.value());
        outputLogic.setUndefined(inputLogic.isUndefined());
    }

    public NotGate(Coordinates coordinates, String uuid) {
        super(coordinates, uuid);
    }
}
