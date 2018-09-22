package javafx.component;

import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.ComponentFactory;
import model.component.Component;
import model.component.ComponentInterface;
import simulator.Simulator;

import java.io.InputStream;

public class ComponentController implements Controller {

    ComponentInterface componentModel;

    @FXML
    private Parent component;

    @FXML
    private VBox componentVBox;

    @FXML
    private ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public void showComponent(Component componentModel) {

        InputStream inputstream = Simulator.class.getResourceAsStream(componentModel.getImageLocation());
        Image image = new Image(inputstream);
        imageView.setImage(image);
    }
}
