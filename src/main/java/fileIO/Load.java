package fileIO;

import javafx.component.model.component.ComponentParameters;
import javafx.main.Mainfx;
import model.Coordinates;
import javafx.simulation.SimulationController;
import model.PortIdentifier;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Load {

    private static String COMPONENTS = "components";
    private static String COMPONENT = "component";
    private static String TYPE = "type";
    private static String WIRES = "wires";
    private static String INPUT = "input";
    private static String OUTPUT = "output";
    private static String PORT = "port";
    private static String XCOORD = "xCoord";
    private static String YCOORD = "yCoord";
    private static String UUID = "uuid";
    private static String INPUTPORTS = "inputPorts";
    private static String OUTPUTPORTS = "outputPorts";
    private static String CORNER = "corner";
    private static String CONSTANTS = "constants";

    private static Logger logger = LogManager.getLogger(Load.class);

    public static void loadWithFileChooser(SimulationController simulationController) {

        String jsonText = loadTextWithFileChooser();
        if(jsonText == null) {
            return;
        }
        load(simulationController,jsonText);
    }

    public static boolean loadFromFile(SimulationController simulationController, String fileName) {
        String file = loadTextFromFile(fileName);
        if(file == null) {
            logger.error(String.format("failed to load from: %s", fileName));
            return false;
        }

        load(simulationController, file);
        return true;
    }

    static String loadTextFromFile(String fileName) {
        InputStream inputStream = Load.class.getResourceAsStream(fileName);
        if(inputStream == null) return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().collect(Collectors.joining());
    }

    static void load(SimulationController simulationController, String file) {
        simulationController.clear();

        JSONObject circuit = new JSONObject(file);

        Evaluator evaluator = new Evaluator();

        boolean loadVariablesCorrect = loadVariables(circuit, evaluator);

        boolean loadComponentsCorrect = loadComponents(circuit, simulationController, evaluator);

        boolean loadWiresCorrect = loadWires(circuit, simulationController, evaluator);

        if(!loadVariablesCorrect || !loadComponentsCorrect || !loadWiresCorrect) {
            logger.info("clearing screen as loading failed");
            simulationController.clear();
            return;
        }

        simulationController.resetSimulation();
        simulationController.wireDelay();

    }

    private static boolean loadVariables(JSONObject circuit, Evaluator evaluator) {
        JSONObject variableJson = circuit.getJSONObject(CONSTANTS);

        for(String key : variableJson.keySet()) {
            evaluator.putVariable(key, Integer.toString(variableJson.getInt(key)));
        }
        return true;
    }

    static boolean loadComponents(JSONObject circuit, SimulationController simulationController, Evaluator evaluator) {
        JSONArray components = circuit.getJSONArray(COMPONENTS);

        for(Object componentObject : components) {
            JSONObject component = (JSONObject)componentObject;
            String uuid = component.getString(UUID);

            int xCoord;
            int yCoord;
            try {
                xCoord = parseEval(component, XCOORD, evaluator);
                yCoord = parseEval(component, YCOORD, evaluator);
            } catch (EvaluationException e) {
                logger.error("failed to parse expression");
                return false;
            }

            Coordinates coordinates = new Coordinates(xCoord,yCoord);

            int inputPorts, outputPorts;

            if(component.has(INPUTPORTS)) {
                inputPorts = component.getInt(INPUTPORTS);
            } else {
                inputPorts = -1;
            }

            if(component.has(OUTPUTPORTS)) {
                outputPorts = component.getInt(OUTPUTPORTS);
            } else {
                outputPorts = -1;
            }

            ComponentParameters componentParameters = new ComponentParameters(coordinates, uuid, component.getString(TYPE), inputPorts, outputPorts);

            if (!simulationController.addComponent(componentParameters)) {
                logger.error("failed to load components");
                return false;
            }
        }
        return true;
    }

    static boolean loadWires(JSONObject circuit, SimulationController simulationController, Evaluator evaluator) {
        JSONArray wires = circuit.getJSONArray(WIRES);

        for(Object wireObject : wires) {
            JSONObject wireJson = (JSONObject)wireObject;
            JSONObject inputJson = wireJson.getJSONObject(INPUT);
            JSONArray outputsJson = wireJson.getJSONArray(OUTPUT);

            PortIdentifier inputPort = new PortIdentifier(inputJson.getString(COMPONENT), inputJson.getInt(PORT));

            ArrayList<PortIdentifier> outputPorts = new ArrayList<>();

            for(Object object : outputsJson) {
                JSONObject outputJson = (JSONObject) object;

                PortIdentifier portIdentifier = new PortIdentifier(outputJson.getString(COMPONENT), outputJson.getInt(PORT));

                if(outputJson.has(CORNER)) {
                    JSONArray cornersJson = outputJson.getJSONArray(CORNER);

                    for(Object cornerObject : cornersJson) {
                        JSONObject cornerJson = (JSONObject) cornerObject;

                        int xCoord;
                        int yCoord;
                        try {
                            xCoord = parseEval(cornerJson, XCOORD, evaluator);
                            yCoord = parseEval(cornerJson, YCOORD, evaluator);
                        } catch (EvaluationException e) {
                            logger.error("failed to parse expression");
                            return false;
                        }

                        portIdentifier.addCorner(new Coordinates(xCoord, yCoord));
                    }
                }

                outputPorts.add(portIdentifier);
            }

            if (!simulationController.addWire(wireJson.getString(UUID), inputPort, outputPorts)) {
                logger.error(String.format("wire loading failed at input %s %d", inputJson.getString(COMPONENT), inputJson.getInt(PORT)));
                return false;
            }
        }
        return true;
    }

    private static String loadTextWithFileChooser() {
        File file = Mainfx.openFileWindow();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            return reader.lines().collect(Collectors.joining());
        } catch (FileNotFoundException | NullPointerException e) {
            logger.error("No file was chosen");
        }

        return null;
    }

    private static int parseEval(JSONObject jsonObject, String key, Evaluator evaluator) throws EvaluationException {
        int result;
        if(jsonObject.get(key) instanceof Integer) {
            result = jsonObject.getInt(key);
        } else {
            result = (int)Math.floor(Float.valueOf(evaluator.evaluate(jsonObject.getString(key))));
        }
        return result;
    }
}
