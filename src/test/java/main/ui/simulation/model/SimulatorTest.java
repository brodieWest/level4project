package main.ui.simulation.model;

import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.Dff;
import main.ui.component.model.component.Io.Input;
import main.ui.component.model.component.Io.Output;
import main.ui.component.model.component.gates.NotGate;
import main.ui.wire.Wire;
import main.model.Coordinates;
import main.ui.wire.WireController;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SimulatorTest extends ApplicationTest {

    @Test
    public void calculatePathDepthOneComponent() {
        Simulator simulator = new Simulator();

        Input input = new Input(new ComponentParameters(new Coordinates(0,0),"input1", "input", new ArrayList<>()));

        NotGate not = new NotGate(new ComponentParameters(new Coordinates(0,0),"not1", "not", new ArrayList<>()));

        Output output = new Output(new ComponentParameters(new Coordinates(0,0), "output1", "output", new ArrayList<>()));

        Wire wire1 = new Wire(mock(WireController.class),"wire1");

        Wire wire2 = new Wire(mock(WireController.class),"wire2");

        input.getOutput(0).setWire(wire1);
        wire1.setInput(input.getOutput(0));

        not.getInput(0).setWire(wire1);
        wire1.addOutput(not.getInput(0));

        not.getOutput(0).setWire(wire2);
        wire2.setInput(not.getOutput(0));

        output.getInput(0).setWire(wire2);
        wire2.addOutput(output.getInput(0));

        List<Component> inputs = new ArrayList<>();
        inputs.add(input);

        simulator.calculatePathDepth(inputs);

        assertEquals(1,simulator.getPathDepth());
    }

    @Test
    public void calculatePathWithFlipFlop() {
        Simulator simulator = new Simulator();

        Input input = new Input(new ComponentParameters(new Coordinates(0,0),"input1", "input", new ArrayList<>()));

        NotGate not = new NotGate(new ComponentParameters(new Coordinates(0,0),"not1", "not", new ArrayList<>()));

        Dff dff = new Dff(new ComponentParameters(new Coordinates(0,0),"dff", "dff", new ArrayList<>()));

        NotGate not2 = new NotGate(new ComponentParameters(new Coordinates(0,0),"not2", "not", new ArrayList<>()));

        Output output = new Output(new ComponentParameters(new Coordinates(0,0), "output1", "output", new ArrayList<>()));

        Wire wire1 = new Wire(mock(WireController.class),"wire1");

        Wire wire2 = new Wire(mock(WireController.class),"wire2");

        Wire wire3 = new Wire(mock(WireController.class),"wire3");

        Wire wire4 = new Wire(mock(WireController.class),"wire4");

        input.getOutput(0).setWire(wire1);
        wire1.setInput(input.getOutput(0));

        not.getInput(0).setWire(wire1);
        wire1.addOutput(not.getInput(0));

        not.getOutput(0).setWire(wire2);
        wire2.setInput(not.getOutput(0));

        dff.getInput(0).setWire(wire2);
        wire2.addOutput(dff.getInput(0));

        dff.getOutput(0).setWire(wire3);
        wire3.setInput(dff.getOutput(0));

        not2.getInput(0).setWire(wire3);
        wire3.addOutput(not2.getInput(0));

        not2.getOutput(0).setWire(wire4);
        wire4.setInput(not2.getOutput(0));

        output.getInput(0).setWire(wire4);
        wire4.addOutput(output.getInput(0));

        List<Component> inputs = new ArrayList<>();
        inputs.add(input);

        simulator.calculatePathDepth(inputs);

        assertEquals(2,simulator.getPathDepth());
    }


}