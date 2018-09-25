package javafx.component.model.component;

import javafx.component.model.Logic;

public class Dff extends Component {

    Logic storedValue;

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
        // TODO: set nextLogic on ouput based on input and stored value
    }

    public Dff(int xCoord, int yCoord) {
        super(xCoord, yCoord);
        addNewInput("input1");
        addNewInput("input2");
        addNewOutput("ouput1");
    }
}