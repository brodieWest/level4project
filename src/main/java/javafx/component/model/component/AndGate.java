package javafx.component.model.component;

import model.Coordinates;

public class AndGate extends Component {

    private static int uuidGenerator = 0;


    @Override
    public void processGateDelay() {
        // TODO: set nextLogic on ouput based on input
    }

    @Override
    public int getUuidGenerator() {
        uuidGenerator++;
        return uuidGenerator;
    }

    @Override
    public String getStringIdentifier() {
        return "and";
    }


    AndGate(Coordinates coordinates) {
        super(coordinates);
    }
}
