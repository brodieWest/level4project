package ui.component.model.component.word;

import ui.component.WordComponent;
import ui.component.model.component.Component;
import ui.component.model.component.ComponentParameters;
import model.Port;
import model.Word;

public class Split extends Component implements WordComponent {

    public Split(ComponentParameters componentParameters) {
        super(componentParameters);
    }

    @Override
    public void wireDelay() {
        Word inputWord = getInput(0).getWord();

        for(int i=0;i<outputs.size();i++) {
            Port output = outputs.get(i);
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
