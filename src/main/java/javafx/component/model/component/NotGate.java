package javafx.component.model.component;

import javafx.component.model.Coordinates;

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
        // TODO: set nextLogic on ouput based on input
    }

    public NotGate(Coordinates coordinates) {
        super(coordinates);
        addNewInput("input1");
        addNewOutput("ouput1");
    }
}
