package model.component;

import model.PortType;
import model.Wire;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class Component {
    Map<String, Wire> inputs = new HashMap<>();
    Map<String, Wire> outputs = new HashMap<>();

    private int xCoord;
    private int yCoord;

    String imageLocation;

    private String uuid;

    Component(int xCoord, int yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        setUuid();
    }

    private void setUuid() {
        uuid = this.getStringIdentifier() + Integer.toString(this.getUuidGenerator());
    }

    // simulates the component over a single gate delay
    public abstract void processGateDelay();


    public void addNewIO(String name, PortType portType) {
        //TODO
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

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public abstract int getUuidGenerator();

    public abstract String getStringIdentifier();

    public String getUuid() {
        return uuid;
    }
}
