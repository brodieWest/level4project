package javafx.component;

import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.component.model.component.Component;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import model.Logic;

public class ComponentController implements Controller {

    private Component componentModel;

    @FXML
    private Text text;

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
        //Shape shape = (Shape)svgGroup.getChildren().get(0);
        //shape.setFill(Paint.valueOf("yellow"));
    }

    public void switchInputValue() {
        Component input = this.componentModel;
        Logic inputLogic = input.getOutput(0).getLogic();
        Shape shape = (Shape)svgGroup.getChildren().get(0);

        //TODO set all ports to undefined

        if(inputLogic.value()) {
            inputLogic.setValue(false);
            text.setText("0");
            shape.setFill(Paint.valueOf("white"));
        } else {
            inputLogic.setValue(true);
            text.setText("1");
            shape.setFill(Paint.valueOf("yellow"));
        }
    }

    public void switchOutputValue() {
        Component output = this.componentModel;
        Logic inputLogic = output.getInput(0).getLogic();
        Shape shape = (Shape)svgGroup.getChildren().get(0);

        if(inputLogic.value()) {
            text.setText("1");
            shape.setFill(Paint.valueOf("yellow"));
        } else {
            text.setText("0");
            shape.setFill(Paint.valueOf("white"));
        }
    }

}
