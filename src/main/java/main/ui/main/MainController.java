package main.ui.main;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.fileIO.Load;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import main.fxml.Fxml;
import main.fxml.FxmlLoaderUtils;
import main.model.Coordinates;
import main.model.Direction;
import main.model.ExternalPortMapping;
import main.model.SimulationMode;
import main.ui.Controller;
import main.ui.component.InputControllerInterface;
import main.ui.component.controllers.ComponentControllerFactory;
import main.ui.component.controllers.IoController;
import main.ui.component.model.component.ComponentParameters;
import main.ui.simulation.MainSimulationController;
import main.ui.toolbar.ToolbarButtonController;
import main.utils.TutorialUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.UnaryOperator;


public class MainController implements Controller {

    private MainSimulationController simulationController;

    @FXML
    private VBox componentDescription;

    @FXML
    private VBox simulationVBox;

    @FXML
    private MenuItem load;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label gateDelayCount;

    @FXML
    private Label clockTickCount;

    @FXML
    private CheckMenuItem checkMenuItem;

    @FXML
    private Label pathDepthLabel;

    @FXML
    private VBox toolbox;

    @FXML
    private Button startSimulation;

    @FXML
    private HBox simulationButtons;

    @FXML
    private HBox bottomButtons;

    @FXML
    private HBox buildButtons;

    @FXML
    private VBox leftPane;

    @FXML
    private ScrollPane leftScrollPane;

    @FXML
    private Button deleteWires;

    @FXML
    private Button deleteComponents;

    @FXML
    private VBox saveComponentOptions;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private TextField positionText;

    @FXML
    private MenuItem file;

    private List<ExternalPortMapping> portMappings = new ArrayList<>();

    private static String REUSABLE_PATH = "fileExamples/reusable/";

    @FXML
    protected void loadFile() {
        //TutorialUtils.unhighlightText(file);
        //TutorialUtils.unhighlightText(load);

        if(simulationController.getSimulationMode() == SimulationMode.SIMULATE) {
            buildMode();
        }

        Load.loadWithFileChooser(simulationController);
        simulationController.resetSimulation();
        simulationController.wireDelay();
    }

    @FXML
    private void saveFile() {
        simulationController.saveFile();
    }

    @FXML
    private void startSaveComponent() {
        if(simulationController.getSimulationMode() == SimulationMode.SIMULATE) {
            buildMode();
        }
        hideToolbox();
        leftPane.getChildren().add(saveComponentOptions);
        leftScrollPane.setPrefWidth(200);

        if(!simulationController.startIterator()) {
            endSaveComponent();
        }
        startSimulation.setDisable(true);
        deleteWires.setDisable(true);
        deleteComponents.setDisable(true);
    }

    private boolean hasNextIo = true;

    @FXML
    private void saveComponent() {
        String id = simulationController.getNextIo();
        Direction direction = Direction.valueOf((String)toggleGroup.getSelectedToggle().getUserData());
        int position = Integer.parseInt(positionText.getText());
        portMappings.add(new ExternalPortMapping(id,direction,position));

        if(!simulationController.hasNextIo()) {
            if(hasNextIo) {
                hasNextIo = false;
            } else {
                Collections.sort(portMappings);
                simulationController.saveAsComponent(portMappings);
                endSaveComponent();
                hasNextIo = true;
            }
        }
    }

    @FXML
    private void endSaveComponent() {
        leftPane.getChildren().remove(saveComponentOptions);
        showToolbox();
        portMappings.clear();
        startSimulation.setDisable(false);
        deleteWires.setDisable(false);
        deleteComponents.setDisable(false);
    }

    @FXML
    private void clear() {
        simulationController.clear();
    }

    @FXML
    private void gateDelay() {
        simulationController.gateDelay();
    }

    @FXML
    private void clockTick() {
        simulationController.clockTick();
    }

    @FXML
    private void zoomIn() {
        simulationController.zoomIn();
    }

    @FXML
    private void zoomOut() {
        simulationController.zoomOut();
    }

    @FXML
    private void startTutorial() {

        TutorialUtils.highlightText(file);
        TutorialUtils.highlightText(load);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Try loading a simple circuit.\n\nPress the load button that is highlighted and load the circuit notnotnotnot from the file choices given.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        alert.showAndWait();

    }

    public void initialize(){

        this.simulationController = new MainSimulationController(this);

        borderPane.setCenter(simulationController.getScrollPane());

        initialiseToolbar();

        bottomButtons.getChildren().remove(simulationButtons);

        leftPane.getChildren().remove(saveComponentOptions);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("([0-9])*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);

        positionText.setTextFormatter(textFormatter);
    }

    private void initialiseToolbar() {

        for(Node node : toolbox.getChildren()) {
            if(node instanceof ToolbarButtonController) {
                ((ToolbarButtonController) node).setMainController(this);
            }
        }

        String currentPath = Paths.get("./examples/reusables").toAbsolutePath().normalize().toString();
        File folder = new File(currentPath);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for(File file : listOfFiles) {
                String type = file.getName();
                ToolbarButtonController button = new ToolbarButtonController();
                button.setMainController(this);
                button.setType(type);
                button.setButtonText(type);
                toolbox.getChildren().add(button);
            }
        }
    }

    public void setGateDelayCount(int count) {
        gateDelayCount.setText(String.valueOf(count));
    }

    public void setClockTickCount(int count) {
        clockTickCount.setText(String.valueOf(count));
    }

    public void setPathDepth(int pathDepth) {
        pathDepthLabel.setText(String.format("Path Depth: %s", String.valueOf(pathDepth)));
    }

    public void displayText(Parent parent) {
        borderPane.setRight(parent);
    }

    public boolean getLongClockTick() {
        return checkMenuItem.isSelected();
    }

    public void addComponent(String type) {
        String uuid = "a" + type + Long.toHexString(Double.doubleToLongBits(Math.random()));
        simulationController.addComponent(new ComponentParameters(simulationController.getScrollPosition(), uuid,type,0,new ArrayList<>()));
    }

    public void startSimulation() {
        if(!simulationController.canSimulate()) return;
        simulationController.setSimulationMode(SimulationMode.SIMULATE);
        bottomButtons.getChildren().remove(buildButtons);
        bottomButtons.getChildren().add(simulationButtons);
        simulationController.resetSimulation();
        simulationController.wireDelay();
        setGateDelayCount(0);
        leftPane.getChildren().remove(toolbox);
        leftScrollPane.setPrefWidth(20);
        if(!simulationController.calculatePathDepth()) {
            buildMode();
        }

    }

    public void buildMode() {
        //simulationController.resetSimulation();
        simulationController.setSimulationMode(SimulationMode.BUILD);
        bottomButtons.getChildren().add(buildButtons);
        bottomButtons.getChildren().remove(simulationButtons);
        leftPane.getChildren().add(toolbox);
        leftScrollPane.setPrefWidth(200);
    }

    public void deleteWires() {
        simulationController.makeWiresDeletable();
        startSimulation.setDisable(true);
        deleteComponents.setDisable(true);
        hideToolbox();
        deleteWires.setText("Finish");
        deleteWires.setOnMouseClicked(event -> {
            showToolbox();
            deleteWires.setText("Delete Wires");
            startSimulation.setDisable(false);
            deleteComponents.setDisable(false);
            simulationController.wiresNotDeletable();
            deleteWires.setOnMouseClicked(event1 -> deleteWires());
        });
    }

    public void deleteComponents() {
        simulationController.makeComponentsDeletable();
        startSimulation.setDisable(true);
        deleteWires.setDisable(true);
        hideToolbox();
        deleteComponents.setText("Finish");
        deleteComponents.setOnMouseClicked(event -> {
            showToolbox();
            deleteComponents.setText("Delete Components");
            startSimulation.setDisable(false);
            deleteWires.setDisable(false);
            simulationController.componentsNotDeletable();
            deleteComponents.setOnMouseClicked(event1 -> deleteComponents());
        });
    }

    private void showToolbox() {
        leftPane.getChildren().add(toolbox);
        leftScrollPane.setPrefWidth(200);
    }

    private void hideToolbox() {
        leftPane.getChildren().remove(toolbox);
        leftScrollPane.setPrefWidth(20);
    }

    public Parent getRoot() {
        return borderPane;
    }
}
