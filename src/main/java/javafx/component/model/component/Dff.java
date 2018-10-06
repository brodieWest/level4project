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
        // TODO: set nextLogic on ouput based on input and stored logic
    }

    public Dff(Coordinates coordinates) {
        super(coordinates);
    }
}
