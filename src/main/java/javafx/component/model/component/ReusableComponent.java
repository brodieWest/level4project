package javafx.component.model.component;

import javafx.component.Synchronous;
import model.Coordinates;

public class ReusableComponent extends Component implements Synchronous {





    public ReusableComponent(Coordinates coordinates, String uuid, String type) {
        super(coordinates, uuid, type);
    }

    @Override
    public void processGateDelay() {
        // go through internal collection of components, then wires performing each gate delay
    }

    @Override
    public void processClockTick() {
        // go through internal collection of components, if synchronous process clock tick

    }
}
