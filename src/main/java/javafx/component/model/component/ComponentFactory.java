package javafx.component.model.component;

import javafx.component.model.component.Io.Input;
import javafx.component.model.component.Io.Output;
import javafx.component.model.component.gates.AndGate;
import javafx.component.model.component.gates.NotGate;
import javafx.component.model.component.gates.OrGate;
import model.Coordinates;

import java.util.*;

public class ComponentFactory {

    private static Map<String,Integer> counter = new HashMap<>();

    private static String[] primatives = {"and", "or", "not", "output", "input", "dff", "nand"}; // remove nand

    public static Component getComponent(String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {

        Component newComponent;

        counter.put(type, counter.get(type) + 1);

        //String uuid = type + counter.get(type);

        if(type.equals("not")) {
            newComponent = new NotGate(coordinates, uuid, type, noInputs,noOutputs);
        } else if(type.equals("and")) {
            newComponent = new AndGate(coordinates, uuid, type, noInputs,noOutputs);
        } else if(type.equals("or")) {
            newComponent = new OrGate(coordinates, uuid, type, noInputs,noOutputs);
        } else if (type.equals("dff")) {
            newComponent = new Dff(coordinates, uuid, type, noInputs,noOutputs);
        } else if(type.equals("input")) {
            newComponent = new Input(coordinates, uuid, type, noInputs,noOutputs);
            newComponent.getOutput(0).getLogic().setValue(false);
            newComponent.getOutput(0).getLogic().setUndefined(false);
        } else if(type.equals("output")) {
            newComponent = new Output(coordinates, uuid, type, noInputs,noOutputs);
        } else if(type.equals("nand")) {
            newComponent = new ReusableComponent(coordinates, uuid, type, noInputs,noOutputs);
        } else {
            // TODO look through blackboxes
            newComponent = new NotGate(coordinates, uuid, type, noInputs,noOutputs);
        }



        return newComponent;
    }

    public static void initialise() {
        for(String primative : primatives) {
            counter.put(primative, 0);
        }
    }

    public static void resetCounter() {
        for(String key : counter.keySet()) {
            counter.put(key, 0);
        }
    }
}
