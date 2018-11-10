package fileIO;

import javafx.simulation.SimulationController;
import model.Coordinates;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

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
            "      \"output\" : {\n" +
            "        \"component\" : \"not1\",\n" +
            "        \"port\" : 10\n" +
            "      }\n" +
            "    }" +
            "  ]\n" +
            "}";

    private final JSONObject testWireJson = new JSONObject(" { \"wires\": [\n" +
            "    {\n" +
            "      \"uuid\" : \"wire1\",\n" +
            "      \"input\" : {\n" +
            "        \"component\" : \"input1\",\n" +
            "        \"port\" : 0\n" +
            "      },\n" +
            "      \"output\" : {\n" +
            "        \"component\" : \"not1\",\n" +
            "        \"port\" : 0\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"uuid\" : \"wire2\",\n" +
            "      \"input\" : {\n" +
            "        \"component\" : \"not1\",\n" +
            "        \"port\" : 0\n" +
            "      },\n" +
            "      \"output\" : {\n" +
            "        \"component\" : \"output1\",\n" +
            "        \"port\" : 10\n" +
            "      }\n" +
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
    public void load() {
        SimulationController mocksim = mock(SimulationController.class);

        when(mocksim.addComponent(anyString(), any(Coordinates.class), anyString(), anyInt(),anyInt())).thenReturn(true);



        Load.load(mocksim,file);

        ArgumentCaptor<Coordinates> notCoordinatesCaptor = ArgumentCaptor.forClass(Coordinates.class);
        ArgumentCaptor<Coordinates> inputCoordinatesCaptor = ArgumentCaptor.forClass(Coordinates.class);

        verify(mocksim).addComponent(eq("input"), inputCoordinatesCaptor.capture(), eq("input1"), eq(0), eq(1));
        verify(mocksim).addComponent(eq("not"), notCoordinatesCaptor.capture(), eq("not1"), eq(1), eq(1));

        assertEquals(new Coordinates(0,0), inputCoordinatesCaptor.getValue());
        assertEquals(new Coordinates(100,900), notCoordinatesCaptor.getValue());

        verify(mocksim).addWire(eq("input1"), eq(0), eq("not1"), eq(10));
    }


    @Test
    public void loadWithError() {
        SimulationController mocksim = mock(SimulationController.class);

        when(mocksim.addComponent(anyString(), any(Coordinates.class), anyString(), anyInt(),anyInt())).thenReturn(false);


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

        Load.loadWires(testWireJson, mocksim);

        verify(mocksim).addWire(eq("input1"), eq(0), eq("not1"), eq(0));
        verify(mocksim).addWire(eq("not1"), eq(0), eq("output1"), eq(10));
    }
}