package main.ui.component.controllers;

import main.ui.component.factories.*;
import main.ui.component.model.component.ComponentParameters;
import main.ui.simulation.SimulationController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        stdComponentControllers.put("xor", new XorFactory());
        stdComponentControllers.put("wordInput", new WordInputFactory());
        stdComponentControllers.put("wordOutput", new WordOutputFactory());
        stdComponentControllers.put("split", new SplitFactory());
        stdComponentControllers.put("join", new JoinFactory());
        stdComponentControllers.put("text", new TextFactory());
        stdComponentControllers.put("reg", new RegFactory());
    }

    public static ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        SingleComponentFactory factory = stdComponentControllers.getOrDefault(componentParameters.getType(), reusableFactory);
        try {
            return factory.getComponentController(simulationController, componentParameters);
        } catch (IOException e) {
            return null;
        }

    }

}
