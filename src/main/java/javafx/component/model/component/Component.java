package javafx.component.model.component;

import javafx.Onscreen;
import model.Coordinates;
import model.Port;
import model.PortType;
import javafx.wire.Wire;

import java.util.ArrayList;
import java.util.List;

public abstract class Component implements Onscreen {
    private List<Port> inputs = new ArrayList<>();
    private List<Port> outputs = new ArrayList<>();

    private Coordinates coordinates;

    private String uuid;

    private int SIZE = 100;
    private int PORT_OFFSET = 20;

    public Component(Coordinates coordinates, String uuid){
        this.coordinates = coordinates;
        this.uuid = uuid;
    }

    // simulates the component over a single gate delay
    public abstract void processGateDelay();

    public void reset() {
        for(Port input : inputs) {
            input.getLogic().setUndefined(true);
        }
        for(Port output : outputs) {
            output.getLogic().setUndefined(true);
        }
    }


    // TODO check if valid
    protected void addNewInput() {
        inputs.add(new Port());
        for(int i = 0; i < inputs.size(); i++) {
            Port port = inputs.get(i);
            port.setOffset(new Coordinates(PORT_OFFSET, PORT_OFFSET + (i+1) * ((SIZE-2*PORT_OFFSET)/(inputs.size()+1)) ));
        }
    }

    // TODO check if valid
    protected void addNewOutput() {
        outputs.add(new Port());
        for(int i = 0; i < outputs.size(); i++) {
            Port port = outputs.get(i);
            port.setOffset(new Coordinates(SIZE-PORT_OFFSET, PORT_OFFSET + (i+1) * ((SIZE-2*PORT_OFFSET)/(outputs.size()+1)) ));
        }
    }

    public Port getOutput(int outputNo) {
        return outputs.get(outputNo);
    }

    public Port getInput(int inputNo) {
        return inputs.get(inputNo);
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

    public abstract String getStringIdentifier();

    public String getUuid() {
        return uuid;
    }
}
