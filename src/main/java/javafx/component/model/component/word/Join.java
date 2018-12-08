package javafx.component.model.component.word;

import javafx.component.WordComponent;
import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import model.Port;
import model.Word;

public class Join extends Component implements WordComponent {
    public Join(ComponentParameters componentParameters) {
        super(componentParameters);
    }

    @Override
    public void processGateDelay() {
    }

    @Override
    public int getDefaultInputs() {
        return  16;
    }

    @Override
    public int getDefaultOutputs() {
        return 1;
    }

    @Override
    public void wireDelay() {
        Word outputWord = getOutput(0).getWord();

        for(int i=0;i<inputs.size();i++) {
            Port input = inputs.get(i);
            outputWord.get(i).copy(input.getLogic());
        }
    }
}
