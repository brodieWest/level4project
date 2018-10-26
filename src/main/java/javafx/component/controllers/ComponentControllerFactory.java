package javafx.component.controllers;

import javafx.simulation.SimulationController;
import model.Coordinates;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ComponentControllerFactory {


    private static Map<String, Class<? extends ComponentController>> stdComponentControllers = new HashMap<>();

    static {
        stdComponentControllers.put("not", ComponentController.class);
        stdComponentControllers.put("and", ComponentController.class);
        stdComponentControllers.put("or", ComponentController.class);
        stdComponentControllers.put("dff", DffController.class);
        stdComponentControllers.put("input", InputController.class);
        stdComponentControllers.put("output", OutputController.class);
        stdComponentControllers.put("nand", ReusableComponentController.class);
    }

    public static ComponentController getComponentController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {

        ComponentController newComponentController = null;

        Class<? extends ComponentController> componentClass = stdComponentControllers.get(type);

        Constructor constructor;
        try {
            constructor = componentClass.getDeclaredConstructor(SimulationController.class, String.class, Coordinates.class, String.class, int.class, int.class);
            newComponentController = (ComponentController) (constructor.newInstance(simulationController, type, coordinates, uuid, noInputs,noOutputs));
        } catch (NoSuchMethodException |InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return newComponentController;
    }

}
