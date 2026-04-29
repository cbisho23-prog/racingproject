package controller;

import model.composite.Race;
import model.composite.RaceComponent;
import model.composite.Stage;
import model.Racer;
import java.util.List;

public class RaceController {

    public void setPodiums(Race race, List<Racer> topThree) {
        race.setPodiums(topThree);
        for (Racer r : topThree) r.incrementPodium();
        System.out.println("Podiums set for " + race.getName());
    }

    public void getDetails(RaceComponent component) {
        component.getDetails();
    }

    public void isFull(RaceComponent component) {
        System.out.println("Is full: " + component.isFull());
    }

    public void showList(RaceComponent component) {
        component.showList();
    }

    public void addStage(Race race, Stage stage) {
        race.add(stage);
        System.out.println("Stage " + stage.getStageNumber() + " added to " + race.getName());
    }
}
