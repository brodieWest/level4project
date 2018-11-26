package javafx.component.model.component;

import javafx.component.Synchronous;
import model.Coordinates;

public class ReusableComponent extends Component{


    public ReusableComponent(Coordinates coordinates, String uuid, String type, int noInputs, int noOutputs) {
        super(coordinates, uuid, type, noInputs, noOutputs);
    }

    @Override
    public void processGateDelay() {
    }

}
