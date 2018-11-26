package javafx.component.controllers;

import javafx.component.factories.*;
import javafx.simulation.SimulationController;
import model.Coordinates;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ComponentControllerFactory {
    private static ReusableFactory reusableFactory = new ReusableFactory();


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
        SingleComponentFactory factory = stdComponentControllers.getOrDefault(type, reusableFactory);
        try {
            return factory.getComponentController(simulationController, type, coordinates, uuid, noInputs, noOutputs);
        } catch (IOException e) {
            return null;
        }

    }

}
