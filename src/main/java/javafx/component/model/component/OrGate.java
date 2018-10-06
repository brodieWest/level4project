package javafx.component.model.component;

import model.Coordinates;

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
        // TODO: set nextLogic on ouput based on input
    }

    OrGate(Coordinates coordinates) {
        super(coordinates);
    }
}
