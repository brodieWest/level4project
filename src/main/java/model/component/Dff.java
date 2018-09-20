package model.component;

import model.Logic;

public class Dff extends Component {

    Logic storedValue;

    @Override
    public void processGateDelay() {
        // TODO: set nextLogic on ouput based on input and stored value
    }

    public Dff(int xCoord, int yCoord) {
        super(xCoord, yCoord);
    }
}
