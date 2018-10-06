package javafx.component.model.component;

import model.Coordinates;

public class Output extends Component {

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
        return "output";
    }


    Output(Coordinates coordinates) {
        super(coordinates);
    }
}
