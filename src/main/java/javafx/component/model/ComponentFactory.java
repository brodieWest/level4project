package javafx.component.model;

import javafx.component.model.component.AndGate;
import javafx.component.model.component.Component;
import javafx.component.model.component.NotGate;
import javafx.component.model.component.OrGate;

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
            return new NotGate(xCoord, yCoord);
        }
    }
}
