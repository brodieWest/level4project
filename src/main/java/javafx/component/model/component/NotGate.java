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
        Port input = getInput(0);
        Port output = getOutput(0);

        Logic inputLogic = input.getLogic();
        Logic outputLogic = output.getLogic();

        if(inputLogic.isUndefined()) {
            outputLogic.setUndefined(true);
        } else {
            outputLogic.setValue(!inputLogic.value());
            outputLogic.setUndefined(false);
        }
    }

    NotGate(Coordinates coordinates) {
        super(coordinates);
    }
}
