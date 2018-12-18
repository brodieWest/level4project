package main.ui.component.model.component.word;

import main.ui.component.WordComponent;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.model.Port;
import main.model.Word;

public class Split extends Component implements WordComponent {

    public Split(ComponentParameters componentParameters) {
        super(componentParameters);
    }

    @Override
    public void wireDelay() {
        Word inputWord = getInput(0).getWord();

        for(int i=0;i<getOutputSize();i++) {
            Port output = getOutput(i);
            output.getLogic().copy(inputWord.get(i));
        }
    }

    @Override
    public void processGateDelay() {

    }

    @Override
    public int getDefaultInputs() {
        return 1;
    }

    @Override
    public int getDefaultOutputs() {
        return 16;
    }
}
