package fileIO;

import javafx.simulation.SimulationController;
import model.Coordinates;
import model.PortIdentifier;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class LoadTest {

    private final String file = "{\n" +
            "  \"components\": [\n" +
            "    {\n" +
            "      \"type\" : \"not\",\n" +
            "      \"uuid\": \"not1\",\n" +
            "      \"xCoord\": 100,\n" +
            "      \"yCoord\": 900,\n" +
            "      \"inputPorts\" : 1,\n" +
            "      \"outputPorts\" : 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\" : \"input\",\n" +
            "      \"uuid\": \"input1\",\n" +
            "      \"xCoord\": 0,\n" +
            "      \"yCoord\": 0,\n" +
            "      \"outputPorts\" : 1\n" +
            "    }" +
            "  ],\n" +
            "  \"wires\": [\n" +
            "    {\n" +
            "      \"uuid\" : \"wire1\",\n" +
            "      \"input\" : {\n" +
            "        \"component\" : \"input1\",\n" +
            "        \"port\" : 0\n" +
            "      },\n" +
            "      \"output\" : [{\n" +
            "        \"component\" : \"not1\",\n" +
            "        \"port\" : 10\n" +
            "      }]\n" +
            "    }" +
            "  ]\n" +
            "}";

    private final JSONObject testWireJson = new JSONObject(" { \"wires\": [\n" +
            "    {\n" +
            "      \"uuid\" : \"wire2\",\n" +
            "      \"input\" : {\n" +
            "        \"component\" : \"not1\",\n" +
            "        \"port\" : 0\n" +
            "      },\n" +
            "      \"output\" : [{\n" +
            "        \"component\" : \"not1\",\n" +
            "        \"port\" : 10\n" +
            "      }]" +
            "    },\n" +
            "    {  \"uuid\" : \"wire1\",\n" +
            "      \"input\" : {\n" +
            "        \"component\" : \"input1\",\n" +
            "        \"port\" : 0\n" +
            "      },\n" +
            "      \"output\" : [{\n" +
            "        \"component\" : \"not1\",\n" +
            "        \"port\" : 0\n" +
            "      }," +
            "     {\n" +
            "        \"component\" : \"not4\",\n" +
            "        \"port\" : 6\n" +
            "      }" +
            "     ]\n" +
            "    }\n" +
            "  ]}");

    private final JSONObject testComponentJson = new JSONObject( "  {\"components\": [\n" +
            "    {\n" +
            "      \"type\" : \"input\",\n" +
            "      \"uuid\": \"input1\",\n" +
            "      \"xCoord\": 0,\n" +
            "      \"yCoord\": 0,\n" +
            "      \"outputPorts\" : 1\n" +
            "    }," +
            "    {\n" +
            "      \"type\" : \"not\",\n" +
            "      \"uuid\": \"not1\",\n" +
            "      \"xCoord\": 100,\n" +
            "      \"yCoord\": 900,\n" +
            "      \"inputPorts\" : 1,\n" +
            "      \"outputPorts\" : 1\n" +
            "    }\n" +
            "]}");

    @Test
    public void loadFromFileError() {
        SimulationController mocksim = mock(SimulationController.class);

        assertFalse(Load.loadFromFile(mocksim, "notAFilePath"));
    }

    @Test
    public void load() {
        SimulationController mocksim = mock(SimulationController.class);

        when(mocksim.addComponent(anyString(), any(Coordinates.class), anyString(), anyInt(),anyInt())).thenReturn(true);
        when(mocksim.addWire(any(PortIdentifier.class),any(ArrayList.class))).thenReturn(true);



        Load.load(mocksim,file);

        ArgumentCaptor<Coordinates> notCoordinatesCaptor = ArgumentCaptor.forClass(Coordinates.class);
        ArgumentCaptor<Coordinates> inputCoordinatesCaptor = ArgumentCaptor.forClass(Coordinates.class);

        verify(mocksim).addComponent(eq("input"), inputCoordinatesCaptor.capture(), eq("input1"), eq(0), eq(1));
        verify(mocksim).addComponent(eq("not"), notCoordinatesCaptor.capture(), eq("not1"), eq(1), eq(1));
        verify(mocksim).resetSimulation();
        verify(mocksim).wireDelay();

        assertEquals(new Coordinates(0,0), inputCoordinatesCaptor.getValue());
        assertEquals(new Coordinates(100,900), notCoordinatesCaptor.getValue());

        ArgumentCaptor<PortIdentifier> inputPort = ArgumentCaptor.forClass(PortIdentifier.class);
        ArgumentCaptor<ArrayList<PortIdentifier>> outputPorts = ArgumentCaptor.forClass(ArrayList.class);

        verify(mocksim).addWire(inputPort.capture(),outputPorts.capture());

        assertEquals(new PortIdentifier("input1",0), inputPort.getValue());
        assertEquals(new PortIdentifier("not1",10), outputPorts.getValue().get(0));
    }


    @Test
    public void loadWithComponentError() {
        SimulationController mocksim = mock(SimulationController.class);

        when(mocksim.addComponent(anyString(), any(Coordinates.class), anyString(), anyInt(),anyInt())).thenReturn(false);


        Load.load(mocksim,file);

        verify(mocksim, times(2)).clear();
    }

    @Test
    public void loadWithWireError() {
        SimulationController mocksim = mock(SimulationController.class);

        when(mocksim.addComponent(anyString(), any(Coordinates.class), anyString(), anyInt(),anyInt())).thenReturn(true);
        //when(mocksim.addWire(anyString(),anyInt(),anyString(),anyInt())).thenReturn(false);
        when(mocksim.addWire(any(PortIdentifier.class),any(ArrayList.class))).thenReturn(false);

        Load.load(mocksim,file);

        verify(mocksim, times(2)).clear();
    }

    @Test
    public void loadComponents() {
        SimulationController mocksim = mock(SimulationController.class);

        when(mocksim.addComponent(anyString(), any(Coordinates.class), anyString(), anyInt(),anyInt())).thenReturn(true);

        Load.loadComponents(testComponentJson, mocksim);

        ArgumentCaptor<Coordinates> notCoordinatesCaptor = ArgumentCaptor.forClass(Coordinates.class);
        ArgumentCaptor<Coordinates> inputCoordinatesCaptor = ArgumentCaptor.forClass(Coordinates.class);

        verify(mocksim).addComponent(eq("input"), inputCoordinatesCaptor.capture(), eq("input1"), eq(0), eq(1));
        verify(mocksim).addComponent(eq("not"), notCoordinatesCaptor.capture(), eq("not1"), eq(1), eq(1));

        assertEquals(new Coordinates(0,0), inputCoordinatesCaptor.getValue());
        assertEquals(new Coordinates(100,900), notCoordinatesCaptor.getValue());
    }

    @Test
    public void loadWires() {
        SimulationController mocksim = mock(SimulationController.class);
        when(mocksim.addWire(any(PortIdentifier.class),any(ArrayList.class))).thenReturn(true);

        Load.loadWires(testWireJson, mocksim);

        ArgumentCaptor<PortIdentifier> inputPort = ArgumentCaptor.forClass(PortIdentifier.class);
        ArgumentCaptor<ArrayList<PortIdentifier>> outputPorts = ArgumentCaptor.forClass(ArrayList.class);


        verify(mocksim, times(2)).addWire(inputPort.capture(),outputPorts.capture());

        assertEquals(new PortIdentifier("input1",0), inputPort.getValue());
        assertEquals(new PortIdentifier("not1",0), outputPorts.getValue().get(0));
        assertEquals(new PortIdentifier("not4",6), outputPorts.getValue().get(1));

    }

}