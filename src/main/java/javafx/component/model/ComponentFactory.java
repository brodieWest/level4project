package javafx.component.model;

import javafx.component.model.component.AndGate;
import javafx.component.model.component.Component;
import javafx.component.model.component.NotGate;
import javafx.component.model.component.OrGate;

public class ComponentFactory {
    public static Component getComponent(String type, Coordinate coordinate) {
        if(type.equals("not")) {
            return new NotGate(coordinate);
        }
        if(type.equals("and")) {
            return new AndGate(coordinate);
        }
        if(type.equals("or")) {
            return new OrGate(coordinate);
        } else {
            return new NotGate(coordinate);
        }
    }
}
