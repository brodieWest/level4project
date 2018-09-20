package model.component;

import model.PortType;
import model.Wire;

import java.util.Map;

abstract class Component {
    Map<String, Wire> inputs;
    Map<String, Wire> outputs;

    private int xCoord;
    private int yCoord;

    public Component(int xCoord, int yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
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
}
