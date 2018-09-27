package javafx.component;

import fileIO.Load;
import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.component.model.component.Component;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.simulation.model.Simulator;

import java.io.InputStream;

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
        //setImage();
    }

    private void setImage() {
        Rectangle rectangle = new Rectangle(20, 20, 5, 60);

        Arc arc = new Arc();
        arc.setCenterX(25);
        arc.setCenterY(50);
        arc.setRadiusX(50);
        arc.setRadiusY(30);
        arc.setStartAngle(-90);
        arc.setLength(180);

        Arc arc2 = new Arc();
        arc2.setCenterX(25);
        arc2.setCenterY(50);
        arc2.setRadiusX(45);
        arc2.setRadiusY(25);
        arc2.setStartAngle(-90);
        arc2.setLength(180);
        arc2.setFill(Paint.valueOf("white"));

        svgGroup.getChildren().addAll(rectangle,arc,arc2);
    }

    public Component getComponentModel() {
        return componentModel;
    }

    public void deleteImageAsExample() {

    }

}
