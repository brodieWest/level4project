package simulator;

import model.component.ComponentInterface;
import model.component.NotGate;

import java.util.ArrayList;

public class Simulator {

    private static ArrayList<ComponentInterface> components = new ArrayList<>();

    public static synchronized void clockTick() {
        // TODO
    }

    public static synchronized void gateDelay() {
        // TODO
    }

    public static synchronized void addComponent(String type, int xCoord, int yCoord) {
        // TODO ComponentInterface newComponent = ComponentFactory(type, xCoord, yCoord)
    }

    public static synchronized void removeComponent() {
        // TODO
    }

    public static synchronized void addWire() {
        // TODO
    }

    public static synchronized void removeWire() {
        // TODO
    }
}
