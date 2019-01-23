package main.ui.toolbar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import main.fxml.FxmlLoaderUtils;
import main.ui.Controller;
import main.ui.main.MainController;
import main.ui.main.Mainfx;

import java.io.IOException;

public class ToolbarButtonController extends Group implements Controller {
    @FXML
    private Button toolbarButton;

    private static String BUTTON_FXML_PATH = "fxml/toolbarButton.fxml";

    private String type;

    private MainController mainController;

    private String buttonText;

    @FXML
    private void createComponent() {
        mainController.addComponent(type);
    }

    public ToolbarButtonController() {
        FXMLLoader fxmlLoader = new FXMLLoader(Mainfx.class.getResource(BUTTON_FXML_PATH));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
        toolbarButton.setText(buttonText);
    }

    public String getButtonText() {
        return buttonText;
    }

}
