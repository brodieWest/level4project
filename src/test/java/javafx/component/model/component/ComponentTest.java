package javafx.component.model.component;

import javafx.component.model.component.gates.AndGate;
import javafx.component.model.component.gates.NotGate;
import javafx.component.model.component.gates.OrGate;
import model.Coordinates;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class ComponentTest {

    @Before
    public void setup() {
    }


    @Test
    public void addNewInput() {
        Component and = new AndGate(new ComponentParameters(new Coordinates(1,1), "testand","and", new ArrayList<>()));

        assertEquals(50,and.getInput(0).getOffset().getX());
        assertEquals(40,and.getInput(0).getOffset().getY());

        assertEquals(50,and.getInput(1).getOffset().getX());
        assertEquals(60,and.getInput(1).getOffset().getY());
    }

    @Test
    public void addNewOutput() {
        Component not = new NotGate(new ComponentParameters(new Coordinates(1,1), "testnot","not", new ArrayList<>()));

        assertEquals(50,not.getOutput(0).getOffset().getX());
        assertEquals(50,not.getOutput(0).getOffset().getY());

    }

    @Test
    public void getPortLocations() {
        Component and = new AndGate(new ComponentParameters(new Coordinates(100,200), "testand","and", new ArrayList<>()));

        Map<String,Integer> map = and.getPortLocations();

        assertEquals(150, (int)map.get("testand.input1.x"));
        assertEquals(240, (int)map.get("testand.input0.y"));
        assertEquals(150, (int)map.get("testand.output0.x"));
    }
}