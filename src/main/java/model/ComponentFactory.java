package model;

import model.component.Component;
import model.component.NotGate;

public class ComponentFactory {
    public static Component getComponent(String type, int xCoord, int yCoord) {
        // TODO: proper implimentation
        return new NotGate(xCoord,yCoord);
    }
}
