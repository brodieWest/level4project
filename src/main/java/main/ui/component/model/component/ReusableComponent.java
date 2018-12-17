package main.ui.component.model.component;

import main.ui.wire.Wire;
import main.model.Port;

import java.util.List;

public class ReusableComponent extends Component{

    private List<Wire> internalInputs;

    private List<Wire> internalOutputs;


    public ReusableComponent(ComponentParameters componentParameters) {
        super(componentParameters);
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
    public int getDefaultInputs() {
        return 0;
    }

    @Override
    public int getDefaultOutputs() {
        return 0;
    }

    public List<Wire> getInternalWires() {
        return internalInputs;
    }

}