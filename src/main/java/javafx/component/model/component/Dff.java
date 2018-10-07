package javafx.component.model.component;

import model.Coordinates;
import model.Logic;

public class Dff extends Component {

    Logic storedValue = new Logic();

    private static int uuidGenerator = 0;

    @Override
    public int getUuidGenerator() {
        uuidGenerator++;
        return uuidGenerator;
    }

    @Override
    public String getStringIdentifier() {
        return "dff";
    }


    @Override
    public void processGateDelay() {
        Logic outputLogic = getOutput(0).getLogic();

        outputLogic.setValue(storedValue.value());
        outputLogic.setUndefined(storedValue.isUndefined());
    }

    public void processClockTick() {
        Logic inputLogic = getInput(0).getLogic();

        storedValue.setValue(inputLogic.value());
        storedValue.setUndefined(inputLogic.isUndefined());
    }

    public Dff(Coordinates coordinates) {
        super(coordinates);
    }
}
