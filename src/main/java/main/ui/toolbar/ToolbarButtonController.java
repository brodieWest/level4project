package main.ui.toolbar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.fxml.FxmlLoaderUtils;
import main.ui.Controller;
import main.ui.main.MainController;
import main.ui.main.Mainfx;

public class ToolbarButtonController implements Controller {
    @FXML
    private Button toolbarButton;

    private static String BUTTON_FXML_PATH = "fxml/toolbarButton.fxml";

    private String type;

    private MainController mainController;

    @FXML
    private void createComponent() {
        mainController.addComponent(type);
    }

    public ToolbarButtonController(MainController mainController, String type) {
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(BUTTON_FXML_PATH), this);
        this.type = type;
        this.mainController = mainController;

        toolbarButton.setText(type);
    }

    public Button getToolbarButton() {
        return toolbarButton;
    }
}
