package javafx.component.controllers;

import javafx.component.factories.*;
import javafx.component.model.component.Component;
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


    private static Map<String, SingleComponentFactory> stdComponentControllers = new HashMap<>();

    static {
        stdComponentControllers.put("not", new NotFactory());
        stdComponentControllers.put("and", new AndFactory());
        stdComponentControllers.put("or", new OrFactory());
        stdComponentControllers.put("dff", new DffFactory());
        stdComponentControllers.put("input", new InputFactory());
        stdComponentControllers.put("output", new OutputFactory());
    }

    public static ComponentController getComponentController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {
        SingleComponentFactory factory = stdComponentControllers.getOrDefault(type, new ReusableFactory());
        return factory.getComponentController(simulationController, type, coordinates, uuid, noInputs, noOutputs);
    }

}
