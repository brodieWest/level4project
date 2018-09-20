package model.component;

import model.Wire;

import java.util.Map;

abstract class Component {
    Map<String, Wire> inputs;
    Map<String, Wire> outputs;


    // simulates the component over a single gate delay
    public abstract void processGateDelay();
}
