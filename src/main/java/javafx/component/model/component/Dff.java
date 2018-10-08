package javafx.component.model.component;

import model.Coordinates;
import model.Logic;

public class Dff extends Component {

    Logic storedValue = new Logic();

    @Override
    public String getStringIdentifier() {
        return "dff";
    }


    @Override
    public void processGateDelay() {
        getOutput(0).getLogic().copy(storedValue);
    }

    public void processClockTick() {
        storedValue.copy(getInput(0).getLogic());
    }

    public Dff(Coordinates coordinates, String uuid) {
        super(coordinates, uuid);
    }
}
