package javafx.component.model.component.Io;

import javafx.component.model.component.Component;
import model.Coordinates;

public class Output extends Component {
    @Override
    public void processGateDelay() {
        // TODO: set nextLogic on ouput based on input
    }


    public Output(Coordinates coordinates, String uuid, String type) {
        super(coordinates, uuid, type);
    }
}
