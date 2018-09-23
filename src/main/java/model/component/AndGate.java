package model.component;

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


    public AndGate(int xCoord, int yCoord) {
        super(xCoord, yCoord);
        imageLocation = "/images/andImage.png";

    }
}
