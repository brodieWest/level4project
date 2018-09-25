package fileIO;

import model.Wire;
import org.json.JSONArray;
import org.json.JSONObject;
import simulator.Simulator;

import java.io.*;
import java.util.stream.Collectors;

public class Load {

    public static void loadFromFile(String fileName) {
        JSONObject circuit = new JSONObject(loadTextFromFile(fileName));

        loadComponents(circuit);

        JSONArray wires = circuit.getJSONArray("wires");

        for(Object wireOject : wires) {
            JSONObject wire = (JSONObject)wireOject;
            // Wire wire = Simulator.addWire();
        }
    }

    private static void loadComponents(JSONObject circuit) {
        JSONArray components = circuit.getJSONArray("components");

        for(Object componentObject : components) {
            JSONObject component = (JSONObject)componentObject;
            Simulator.addComponent(component.getString("type"), component.getInt("xCoord"), component.getInt("yCoord"));
        }
    }

    static String loadTextFromFile(String fileName) {
        InputStream inputStream = Load.class.getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        return reader.lines().collect(Collectors.joining());
    }
}
