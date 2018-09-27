package javafx.component.model.component;

import javafx.component.model.Coordinate;

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

    public OrGate(Coordinate coordinate) {
        super(coordinate);
        addNewInput("input1");
        addNewInput("input2");
        addNewOutput("ouput1");
    }
}
