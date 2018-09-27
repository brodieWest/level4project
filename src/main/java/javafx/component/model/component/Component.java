package javafx.component.model.component;

import javafx.component.model.Coordinate;
import javafx.component.model.Port;
import javafx.component.model.PortType;
import javafx.component.model.Wire;

import java.util.HashMap;
import java.util.Map;

public abstract class Component {
    private Map<String, Port> inputs = new HashMap<>();
    private Map<String, Port> outputs = new HashMap<>();

    private Coordinate coordinate;

    private String uuid;

    private int SIZE = 100;
    private int PORT_OFFSET = 30;

    Component(Coordinate coordinate){
        this.coordinate = coordinate;
        setUuid();
    }

    private void setUuid() {
        uuid = this.getStringIdentifier() + Integer.toString(this.getUuidGenerator());
    }

    // simulates the component over a single gate delay
    public abstract void processGateDelay();


    // TODO check if valid
    void addNewInput(String name) {
        Coordinate coordinate = new Coordinate(PORT_OFFSET,inputs.size()*10+10);
        inputs.put(name, new Port(coordinate));
    }

    // TODO check if valid
    void addNewOutput(String name) {
        Coordinate coordinate = new Coordinate(SIZE-PORT_OFFSET,outputs.size()*10+10);
        outputs.put(name, new Port(coordinate));
    }

    public void deleteIO(String name, PortType portType) {
        //TODO
    }

    public void addWireToInput(String inputName, Wire wire, PortType portType) {

    }

    public void deleteWireToIO(String name, PortType portType) {
        //TODO
    }

    public Coordinate getCoordinate() {
        return coordinate;
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
