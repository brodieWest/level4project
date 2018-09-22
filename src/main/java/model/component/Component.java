package model.component;

import model.PortType;
import model.Wire;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

abstract class Component implements ComponentInterface {
    Map<String, Wire> inputs = new HashMap<>();
    Map<String, Wire> outputs = new HashMap<>();

    private int xCoord;
    private int yCoord;

    private URL imageUrl;

    Component(int xCoord, int yCoord){
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
