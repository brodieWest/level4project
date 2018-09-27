package javafx.component.model.component;

import javafx.component.model.Coordinate;
import javafx.component.model.PortType;
import javafx.component.model.Wire;

import java.util.HashMap;
import java.util.Map;

public abstract class Component {
    private Map<String, Wire> inputs = new HashMap<>();
    private Map<String, Wire> outputs = new HashMap<>();

    private Coordinate coordinate;

    private String uuid;

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
        inputs.put(name, null);
    }

    // TODO check if valid
    void addNewOutput(String name) {
        outputs.put(name, null);
    }

    public void deleteIO(String name, PortType portType) {
        //TODO
    }

    public void addWireToIO(String name, Wire wire, PortType portType) {
        //TODO
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
}
