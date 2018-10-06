package javafx.component;

import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.component.model.component.Component;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

public class ComponentController implements Controller {

    private Component componentModel;

    @FXML
    private Parent component;

    @FXML
    private VBox componentVBox;

    @FXML
    private Group svgGroup;

    public void initialiseComponent(Component componentModel) {
        this.componentModel = componentModel;
    }

    public Component getComponentModel() {
        return componentModel;
    }

    public void deleteImageAsExample() {
        Shape shape = (Shape)svgGroup.getChildren().get(0);
        shape.setFill(Paint.valueOf("yellow"));
    }

}
