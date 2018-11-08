package javafx.component.model.component;

import javafx.component.model.component.Io.Input;
import javafx.component.model.component.Io.Output;
import javafx.component.model.component.gates.AndGate;
import javafx.component.model.component.gates.NotGate;
import javafx.component.model.component.gates.OrGate;
import model.Coordinates;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ComponentFactory {

    private static Map<String, Class<? extends Component>> stdComponents = new HashMap<>();

    static {
        stdComponents.put("not", NotGate.class);
        stdComponents.put("and", AndGate.class);
        stdComponents.put("or", OrGate.class);
        stdComponents.put("dff", Dff.class);
        stdComponents.put("input", Input.class);
        stdComponents.put("output", Output.class);
    }

    public static Component getComponent(String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {

        Component newComponent = null;

        Class<? extends Component> componentClass;

        componentClass = stdComponents.getOrDefault(type, ReusableComponent.class);

        Constructor constructor;
        try {
            constructor = componentClass.getDeclaredConstructor(Coordinates.class , String.class, String.class , int.class, int.class);
            newComponent = (Component) (constructor.newInstance(coordinates, uuid, type, noInputs,noOutputs));
        } catch (NoSuchMethodException |InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return newComponent;
    }

}
