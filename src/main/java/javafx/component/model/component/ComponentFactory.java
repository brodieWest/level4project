package javafx.component.model.component;

import model.Coordinates;

public class ComponentFactory {
    public static Component getComponent(String type, Coordinates coordinates) {
        if(type.equals("not")) {
            NotGate not = new NotGate(coordinates);
            not.addNewInput();
            not.addNewOutput();
            return not;
        } else if(type.equals("and")) {
            AndGate and = new AndGate(coordinates);
            and.addNewInput();
            and.addNewInput();
            and.addNewOutput();
            return and;
        } else if(type.equals("or")) {
            OrGate or = new OrGate(coordinates);
            or.addNewInput();
            or.addNewInput();
            or.addNewOutput();
            return or;
        } else if(type.equals("input")) {
                Input input = new Input(coordinates);
                input.addNewOutput();
                return input;
        } else if(type.equals("output")) {
            Output output = new Output(coordinates);
            output.addNewInput();
            return output;
        } else {
            return new NotGate(coordinates);
        }
    }
}