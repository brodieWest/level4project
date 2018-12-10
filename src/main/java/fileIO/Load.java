package fileIO;

import ui.component.model.component.ComponentParameters;
import ui.main.Mainfx;
import model.*;
import ui.simulation.SimulationController;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    private static String DIRECTION = "direction";
    private static String SIZE = "size";

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

        boolean loadConstantsCorrect = loadConstants(circuit, evaluator, simulationController);

        boolean loadComponentsCorrect = loadComponents(circuit, simulationController, evaluator);

        addPortVariables(evaluator,simulationController);

        boolean loadWiresCorrect = loadWires(circuit, simulationController, evaluator);

        if(!loadConstantsCorrect || !loadComponentsCorrect || !loadWiresCorrect) {
            logger.info("clearing screen as loading failed");
            simulationController.clear();
            return;
        }

        simulationController.resetSimulation();
        simulationController.wireDelay();

    }

    private static boolean loadConstants(JSONObject circuit, Evaluator evaluator, SimulationController simulationController) {
        if(!circuit.has(CONSTANTS)) return true;
        JSONObject variableJson = circuit.getJSONObject(CONSTANTS);

        for(String key : variableJson.keySet()) {
            evaluator.putVariable(key, Integer.toString(variableJson.getInt(key)));
        }
        return true;
    }

    private static void addPortVariables(Evaluator evaluator, SimulationController simulationController) {
        var portLocations = simulationController.getPortLocations();

        for(String key : portLocations.keySet()) {
            evaluator.putVariable(key, Integer.toString(portLocations.get(key)));
        }
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

            List<PortParameters> portParameters = new ArrayList<>();

            if(component.has(INPUTPORTS)) {
                if(component.get(INPUTPORTS) instanceof Integer) {
                    int inputPorts = component.getInt(INPUTPORTS);
                    for (int i = 0; i < inputPorts; i++) {
                        portParameters.add(new PortParameters(Direction.WEST, PortType.INPUT,1));
                    }
                } else {
                    JSONArray inputsJson = component.getJSONArray(INPUTPORTS);
                    for (Object inputObject : inputsJson) {
                        JSONObject inputJson = (JSONObject) inputObject;
                        int size=1;
                        if(inputJson.has(SIZE)) {
                            size = inputJson.getInt(SIZE);
                        }
                        portParameters.add(new PortParameters(Direction.valueOf(inputJson.getString(DIRECTION)), PortType.INPUT, size));
                    }
                }
            }

            if(component.has(OUTPUTPORTS)) {
                if(component.get(OUTPUTPORTS) instanceof Integer) {
                    int outputPorts = component.getInt(OUTPUTPORTS);
                    for (int i = 0; i < outputPorts; i++) {
                        portParameters.add(new PortParameters(Direction.EAST, PortType.OUTPUT,1));
                    }
                } else {
                    JSONArray outputsJson = component.getJSONArray(OUTPUTPORTS);
                    for (Object inputObject : outputsJson) {
                        JSONObject inputJson = (JSONObject) inputObject;
                        int size=1;
                        if(inputJson.has(SIZE)) {
                            size = inputJson.getInt(SIZE);
                        }
                        portParameters.add(new PortParameters(Direction.valueOf(inputJson.getString(DIRECTION)), PortType.OUTPUT, size));
                    }
                }
            }

            ComponentParameters componentParameters = new ComponentParameters(coordinates, uuid, component.getString(TYPE), portParameters);

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
                            logger.error("failed to parse expression in wires");
                            e.printStackTrace();
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
