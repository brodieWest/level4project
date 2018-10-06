package javafx.component.model.component;

import model.Coordinates;

public class ComponentFactory {
    public static Component getComponent(String type, Coordinates coordinates) {
        if(type.equals("not")) {
            NotGate not = new NotGate(coordinates);
            not.addNewInput("input1");
            not.addNewOutput("output1");
            return not;
        }
        if(type.equals("and")) {
            AndGate and = new AndGate(coordinates);
            and.addNewInput("input1");
            and.addNewInput("input2");
            and.addNewOutput("output1");
            return and;
        }
        if(type.equals("or")) {
            OrGate or = new OrGate(coordinates);
            or.addNewInput("input1");
            or.addNewInput("input2");
            or.addNewOutput("output1");
            return or;
        } else {
            return new NotGate(coordinates);
        }
    }
}
