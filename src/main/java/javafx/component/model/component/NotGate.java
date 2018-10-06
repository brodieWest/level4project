package javafx.component.model.component;

import model.Coordinates;
import model.Logic;
import model.Port;

public class NotGate extends Component {


    private static int uuidGenerator = 0;

    @Override
    public int getUuidGenerator() {
        uuidGenerator++;
        return uuidGenerator;
    }

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

    NotGate(Coordinates coordinates) {
        super(coordinates);
    }
}
