package javafx.component.model.component;

import model.Coordinates;
import model.Logic;

public class Input extends Component {

    private static int uuidGenerator = 0;

    private Logic logic = new Logic();


    @Override
    public void processGateDelay() {
        // TODO: set nextLogic on ouput based on input
    }

    @Override
    public int getUuidGenerator() {
        uuidGenerator++;
        return uuidGenerator;
    }

    @Override
    public String getStringIdentifier() {
        return "input";
    }


    Input(Coordinates coordinates) {
        super(coordinates);
        logic.setValue(false);
        logic.setUndefined(false);
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }
}
