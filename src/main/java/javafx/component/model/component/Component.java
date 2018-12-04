package javafx.component.model.component;

import javafx.Onscreen;
import javafx.scene.Parent;
import model.Coordinates;
import model.Port;
import model.PortType;
import javafx.wire.Wire;

import java.util.*;

public abstract class Component implements Onscreen {
    private List<Port> inputs = new ArrayList<>();
    private List<Port> outputs = new ArrayList<>();

    private Coordinates coordinates;

    private String uuid;
    private String type;

    private int pathDepth = 0;

    private int SIZE = 100;
    private int PORT_OFFSET = 20;

    public Component(ComponentParameters componentParameters){
        this.coordinates = componentParameters.getCoordinates();
        this.uuid = componentParameters.getUuid();
        this.type = componentParameters.getType();
        int noInputs = componentParameters.getNoInputs();
        int noOutputs = componentParameters.getNoOutputs();
        if(noInputs == -1) noInputs = this.getDefaultInputs();
        if(noOutputs == -1) noOutputs = this.getDefaultOutputs();
        for(int i=0;i<noInputs;i++) {
            addNewInput();
        }
        for(int i=0;i<noOutputs;i++) {
            addNewOutput();
        }
    }

    // simulates the component over a single gate delay
    public abstract void processGateDelay();

    public abstract int getDefaultInputs();

    public abstract int getDefaultOutputs();

    public void reset() {
        for(Port input : inputs) {
            input.getLogic().setUndefined(true);
        }
        for(Port output : outputs) {
            output.getLogic().setUndefined(true);
        }
    }


    // TODO check if valid
    public void addNewInput() {
        inputs.add(new Port(this));
        for(int i = 0; i < inputs.size(); i++) {
            Port port = inputs.get(i);
            port.setOffset(new Coordinates(SIZE/2, PORT_OFFSET + (i+1) * ((SIZE-2*PORT_OFFSET)/(inputs.size()+1)) ));
        }
    }

    // TODO check if valid
    public void addNewOutput() {
        outputs.add(new Port(this));
        for(int i = 0; i < outputs.size(); i++) {
            Port port = outputs.get(i);
            port.setOffset(new Coordinates(SIZE/2, PORT_OFFSET + (i+1) * ((SIZE-2*PORT_OFFSET)/(outputs.size()+1)) ));
        }
    }

    public Port getOutput(int outputNo) {
        return outputs.get(outputNo);
    }

    public Port getInput(int inputNo) {
        return inputs.get(inputNo);
    }

    public List<Port> getInputs() {
        return inputs;
    }

    public int getInputSize() {
        return inputs.size();
    }

    public int getOutputSize() {
        return outputs.size();
    }

    public List<Wire> getNextWires() {
        List<Wire> wires = new ArrayList<>();

        for(Port output : outputs) {
            wires.add(output.getWire());
        }
        return wires;
    }

    public void deleteIO(String name, PortType portType) {
        //TODO
    }

    public void addWireToInput(String inputName, Wire wire, PortType portType) {

    }

    public void deleteWireToIO(String name, PortType portType) {
        //TODO
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getStringIdentifier() {
        return type;
    }

    public String getUuid() {
        return uuid;
    }

    public int getPathDepth() {
        return pathDepth;
    }

    public void setPathDepth(int pathDepth) {
        this.pathDepth = pathDepth;
    }

    public Map<String,Integer> getPortLocations() {

        Map<String,Integer> portLocations = new TreeMap<>();

        for(int i=0;i<inputs.size();i++) {
            String portId = uuid+".input"+Integer.toString(i)+".";
            Coordinates portPosition = inputs.get(i).getPosition();

            portLocations.put(portId+"x", portPosition.getX());
            portLocations.put(portId+"y", portPosition.getY());
        }

        for(int i=0;i<outputs.size();i++) {
            String portId = uuid+".output"+Integer.toString(i)+".";
            Coordinates portPosition = outputs.get(i).getPosition();

            portLocations.put(portId+"x", portPosition.getX());
            portLocations.put(portId+"y", portPosition.getY());
        }

        return portLocations;
    }
}
