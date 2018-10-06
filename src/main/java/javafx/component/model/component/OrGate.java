package javafx.component.model.component;

import model.Coordinates;
import model.Logic;

public class OrGate extends Component {


    private static int uuidGenerator = 0;

    @Override
    public int getUuidGenerator() {
        uuidGenerator++;
        return uuidGenerator;
    }

    @Override
    public String getStringIdentifier() {
        return "or";
    }


    @Override
    public void processGateDelay() {
        Logic inputLogic0 = getInput(0).getLogic();
        Logic inputLogic1 = getInput(1).getLogic();
        Logic outputLogic = getOutput(0).getLogic();

        outputLogic.setValue(inputLogic0.value() || inputLogic1.value());
        outputLogic.setUndefined(inputLogic0.isUndefined() || inputLogic1.isUndefined());
    }

    OrGate(Coordinates coordinates) {
        super(coordinates);
    }
}
