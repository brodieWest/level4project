package javafx.component.model.component.Io;

import javafx.component.model.component.Component;
import model.Coordinates;

public class Output extends Component {
    @Override
    public void processGateDelay() {
        // TODO: set nextLogic on ouput based on input
    }

    @Override
    public String getStringIdentifier() {
        return "output";
    }


    public Output(Coordinates coordinates, String uuid) {
        super(coordinates, uuid);
    }
}
