package main.ui.component.model.component;

import main.ui.wire.Wire;
import main.ui.port.Port;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReusableComponent extends Component{

    private List<Wire> internalInputs;

    private List<Wire> internalOutputs;

    private int defaultInputs = 0;

    private int defaultOutputs = 0;

    private boolean portsAdded = false;


    public ReusableComponent(ComponentParameters componentParameters) {
        super(componentParameters);
    }

    public void initialiseWires(List<Wire> inputWires, List<Wire> outputWires) {
        internalInputs = inputWires;
        internalOutputs = outputWires;

        if(!hasPorts()) {
            addPorts(new ArrayList<>());
            portsAdded = true;
        }

        for(int i=0; i<inputWires.size();i++) {
            Wire wire = inputWires.get(i);
            Port inputPort = this.getInput(i);

            wire.setInput(inputPort);
            //inputPort.setWire(wire);
        }

        for(int i=0; i<outputWires.size();i++) {
            Wire wire = outputWires.get(i);
            Port outputPort = this.getOutput(i);

            wire.addOutput(outputPort);
            //outputPort.setWire(wire);
        }
    }

    @Override
    public void processGateDelay() {
    }

    @Override
    public int getDefaultInputs() {
        return defaultInputs;
    }

    @Override
    public int getDefaultOutputs() {
        return defaultOutputs;
    }

    public void setDefaultInputs(int defaultInputs) {
        this.defaultInputs = defaultInputs;
    }

    public void setDefaultOutputs(int defaultOutputs) {
        this.defaultOutputs = defaultOutputs;
    }

    public List<Wire> getInternalWires() {
        return internalInputs;
    }

    public boolean isPortsAdded() {
        return portsAdded;
    }
}
