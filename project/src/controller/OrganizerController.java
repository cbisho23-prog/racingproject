package controller;

import model.composite.Race;
import model.composite.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrganizerController {

    private List<Race> races = new ArrayList<>();

    public Race createRace(String name, LocalDate eventDate, int maxParticipants,
                           int category, LocalDate lastDay, boolean official, String type) {
        Race r = new Race(name, eventDate, maxParticipants, category, lastDay, official, type);
        races.add(r);
        System.out.println("Race created: " + name);
        return r;
    }

    public void setLimit(Race race, int max) {
        System.out.println("Max participants for " + race.getName() + " is already set via constructor. Update not supported in this version.");
    }

    public void setStage(Race race, Stage stage) {
        race.add(stage);
        System.out.println("Stage added to " + race.getName());
    }

    public void listRaces() {
        if (races.isEmpty()) { System.out.println("  No races available."); return; }
        System.out.println("All races:");
        for (Race r : races) r.showList();
    }

    public void updateRace(Race race, String newName) {
        System.out.println("Race rename not supported via field access in this version. Use a setter if needed.");
    }

    public List<Race> getRaces() { return races; }
}
