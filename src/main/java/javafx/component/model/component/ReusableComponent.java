package javafx.component.model.component;

import javafx.component.Synchronous;
import javafx.wire.Wire;
import model.Coordinates;
import model.Port;

import java.util.List;

public class ReusableComponent extends Component{

    private List<Wire> internalInputs;

    private List<Wire> internalOutputs;


    public ReusableComponent(Coordinates coordinates, String uuid, String type, int noInputs, int noOutputs) {
        super(coordinates, uuid, type, noInputs, noOutputs);
    }

    public void initialiseWires(List<Wire> inputWires, List<Wire> outputWires) {
        internalInputs = inputWires;
        internalOutputs = outputWires;

        for(int i=0; i<inputWires.size();i++) {
            Wire wire = inputWires.get(i);
            Port inputPort = this.getInput(i);

            wire.setInput(inputPort);
            inputPort.setWire(wire);
        }

        for(int i=0; i<outputWires.size();i++) {
            Wire wire = outputWires.get(i);
            Port outputPort = this.getOutput(i);

            wire.addOutput(outputPort);
            outputPort.setWire(wire);
        }
    }

    @Override
    public void processGateDelay() {
    }

    @Override
    public List<Wire> getNextWires() {
        return internalInputs;
    }

}
