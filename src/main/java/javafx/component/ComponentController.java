package javafx.component;

import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.component.ComponentInterface;

public class ComponentController implements Controller {

    ComponentInterface componentModel;

    @FXML
    private Parent component;

    @FXML
    private VBox componentVBox;

    @FXML
    private ImageView imageView;

}
