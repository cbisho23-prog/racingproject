package model.composite;

/**
 * Leaf in the Composite Pattern.
 */
public class Stage implements RaceComponent {
    private int    stageNumber;
    private int    miles;
    private String route;

    public Stage(int stageNumber, int miles, String route) {
        this.stageNumber = stageNumber;
        this.miles       = miles;
        this.route       = route;
    }

    public int    getStageNumber() { return stageNumber; }
    public int    getMiles()       { return miles; }
    public String getRoute()       { return route; }

    @Override
    public void getDetails() {
        System.out.println("  [Stage " + stageNumber + "] Route: " + route + " | Miles: " + miles);
    }

    @Override
    public void showList() {
        System.out.println("  Stage " + stageNumber + ": " + route);
    }

    @Override
    public boolean isFull() {
        // A single stage cannot be "full" — always available
        return false;
    }

    @Override
    public String toString() {
        return "Stage[" + stageNumber + ", " + route + ", " + miles + " miles]";
    }
}
