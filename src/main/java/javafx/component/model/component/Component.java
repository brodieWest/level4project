package javafx.component.model.component;

import javafx.Onscreen;
import model.Coordinates;
import model.Port;
import javafx.component.model.PortType;
import javafx.wire.Wire;

import java.util.HashMap;
import java.util.Map;

public abstract class Component implements Onscreen {
    private Map<String, Port> inputs = new HashMap<>();
    private Map<String, Port> outputs = new HashMap<>();

    private Coordinates coordinates;

    private String uuid;

    private int SIZE = 100;
    private int PORT_OFFSET = 30;

    Component(Coordinates coordinates){
        this.coordinates = coordinates;
        setUuid();
    }

    private void setUuid() {
        uuid = this.getStringIdentifier() + Integer.toString(this.getUuidGenerator());
    }

    // simulates the component over a single gate delay
    public abstract void processGateDelay();


    // TODO check if valid
    void addNewInput(String name) {
        Coordinates coordinates = new Coordinates(PORT_OFFSET,inputs.size()*10+10);
        inputs.put(name, new Port(coordinates));
    }

    // TODO check if valid
    void addNewOutput(String name) {
        Coordinates coordinates = new Coordinates(SIZE-PORT_OFFSET,outputs.size()*10+10);
        outputs.put(name, new Port(coordinates));
    }

    public Port getOutput(String outputUuid) {
        return outputs.get(outputUuid);
    }

    public Port getInput(String outputUuid) {
        return inputs.get(outputUuid);
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

    public String getImageLocation() {
        return "/images/" + getStringIdentifier() + "Image.png";
    }

    public abstract int getUuidGenerator();

    public abstract String getStringIdentifier();

    public String getUuid() {
        return uuid;
    }

    Map<String, Port> getInputs() {
        return inputs;
    }

    Map<String, Port> getOutputs() {
        return outputs;
    }
}
