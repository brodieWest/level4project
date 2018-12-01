package javafx.simulation.model;

import fileIO.Load;
import javafx.component.model.component.Io.Input;
import javafx.component.model.component.Io.Output;
import javafx.component.model.component.gates.NotGate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.simulation.MainSimulationController;
import javafx.stage.Stage;
import javafx.wire.Wire;
import model.Coordinates;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SimulatorTest extends ApplicationTest {

    @Test
    public void calculatePathDepthOneComponent() {
        Simulator simulator = new Simulator();

        Input input = new Input(new Coordinates(0,0),"input1", "input", 0, 1);

        NotGate not = new NotGate(new Coordinates(0,0),"not1", "not", 2,1);

        Output output = new Output(new Coordinates(0,0), "output1", "output", 1,0);

        Wire wire1 = new Wire("wire1");

        Wire wire2 = new Wire("wire2");

        input.getOutput(0).setWire(wire1);
        wire1.setInput(input.getOutput(0));

        not.getInput(0).setWire(wire1);
        wire1.addOutput(not.getInput(0));

        not.getOutput(0).setWire(wire2);
        wire2.setInput(not.getOutput(0));

        output.getInput(0).setWire(wire2);
        wire2.addOutput(output.getInput(0));

        List<Input> inputs = new ArrayList<>();
        inputs.add(input);

        simulator.calculatePathDepth(inputs);

        assertEquals(1,simulator.getPathDepth());
    }

}