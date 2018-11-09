package javafx.component.controllers;

import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentFactory;
import javafx.simulation.SimulationController;
import model.Coordinates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.simple.SimpleLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ComponentControllerFactory {

    private static Logger logger = LogManager.getLogger(ComponentControllerFactory.class);


    private static Map<String, Class<? extends ComponentController>> stdComponentControllers = new HashMap<>();

    static {
        stdComponentControllers.put("not", ComponentController.class);
        stdComponentControllers.put("and", ComponentController.class);
        stdComponentControllers.put("or", ComponentController.class);
        stdComponentControllers.put("dff", DffController.class);
        stdComponentControllers.put("input", InputController.class);
        stdComponentControllers.put("output", OutputController.class);
    }

    public static ComponentController getComponentController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {
        Component componentModel = ComponentFactory.getComponent(type, coordinates, uuid, noInputs,noOutputs);

        ComponentController newComponentController = null;

        Class<? extends ComponentController> componentClass;

        componentClass = stdComponentControllers.getOrDefault(type, ReusableComponentController.class);

        Constructor constructor;
        try {
            constructor = componentClass.getDeclaredConstructor(SimulationController.class, Component.class);
            newComponentController = (ComponentController) (constructor.newInstance(simulationController, componentModel));
        } catch (NoSuchMethodException |InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error(String.format("failed to build controller for %s", type));
        }

        return newComponentController;
    }

}
