package model;

import model.component.AndGate;
import model.component.Component;
import model.component.NotGate;
import model.component.OrGate;

public class ComponentFactory {
    public static Component getComponent(String type, int xCoord, int yCoord) {
        if(type.equals("not")) {
            return new NotGate(xCoord, yCoord);
        }
        if(type.equals("and")) {
            return new AndGate(xCoord, yCoord);
        }
        if(type.equals("or")) {
            return new OrGate(xCoord, yCoord);
        } else {
            return null;
        }
    }
}
