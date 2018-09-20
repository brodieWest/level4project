package model;

public class Wire {

    private Logic logic;
    //value after gate delay
    private Logic nextLogic;

    public Wire() {
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public Logic getLogic() {
        return logic;
    }

    public void setNextLogic(Logic nextLogic) {
        this.nextLogic = nextLogic;
    }

    public void processGateDelay() {
        logic = nextLogic;
    }
}
