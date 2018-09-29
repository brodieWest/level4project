package javafx.component.model;

import javafx.component.model.component.AndGate;
import javafx.component.model.component.Component;
import javafx.component.model.component.NotGate;
import javafx.component.model.component.OrGate;
import model.Coordinates;

public class ComponentFactory {
    public static Component getComponent(String type, Coordinates coordinates) {
        if(type.equals("not")) {
            return new NotGate(coordinates);
        }
        if(type.equals("and")) {
            return new AndGate(coordinates);
        }
        if(type.equals("or")) {
            return new OrGate(coordinates);
        } else {
            return new NotGate(coordinates);
        }
    }
}
