package model.component;

public class OrGate extends Component {

    @Override
    public void processGateDelay() {
        // TODO: set nextLogic on ouput based on input
    }

    public OrGate(int xCoord, int yCoord) {
        super(xCoord, yCoord);
        imageLocation = "/images/orImage.png";
    }
}
