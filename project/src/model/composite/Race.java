package model.composite;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Racer;

/**
 * Composite in the Composite Pattern.
 * A Race contains zero or more Stages.
 */
public class Race implements RaceComponent {
    private String    name;
    private LocalDate eventDate;
    private int       maxParticipants;
    private int       category;
    private LocalDate lastDay;
    private boolean   official;
    private int       miles;
    private String    route;
    private String    type;
    private List<Racer> podiums   = new ArrayList<>();
    private List<Stage> stages    = new ArrayList<>();
    private List<Racer> signedUp  = new ArrayList<>();

    public Race(String name, LocalDate eventDate, int maxParticipants,
                int category, LocalDate lastDay, boolean official, String type) {
        this.name           = name;
        this.eventDate      = eventDate;
        this.maxParticipants = maxParticipants;
        this.category       = category;
        this.lastDay        = lastDay;
        this.official       = official;
        this.type           = type;
    }

    // ── Composite operations ──────────────────────────────────────────────
    public void add(Stage stage)    { stages.add(stage); }
    public void remove(Stage stage) { stages.remove(stage); }
    public List<Stage> getStages()  { return stages; }

    // ── Participant management ────────────────────────────────────────────
    public boolean signUp(Racer r) {
        if (isFull()) { System.out.println("Race is full."); return false; }
        if (r.getCategory() != category) {
            System.out.println("Racer category does not match race category."); return false;
        }
        signedUp.add(r);
        return true;
    }
    public List<Racer> getSignedUp() { return signedUp; }

    public void setPodiums(List<Racer> topThree) { this.podiums = topThree; }
    public List<Racer> getPodiums()              { return podiums; }

    // ── RaceComponent interface ───────────────────────────────────────────
    @Override
    public void getDetails() {
        System.out.println("Race: " + name + " | Date: " + eventDate
                + " | Category: " + category + " | Type: " + type
                + " | Official: " + official
                + " | Participants: " + signedUp.size() + "/" + maxParticipants);
        if (!stages.isEmpty()) {
            System.out.println("  Stages (" + stages.size() + "):");
            for (Stage s : stages) s.getDetails();
        }
    }

    @Override
    public void showList() {
        System.out.println("- " + name + " (" + eventDate + ") [" + type + "]");
        for (Stage s : stages) s.showList();
    }

    @Override
    public boolean isFull() {
        return signedUp.size() >= maxParticipants;
    }

    // ── Getters ───────────────────────────────────────────────────────────
    public String    getName()           { return name; }
    public int       getCategory()       { return category; }
    public LocalDate getEventDate()      { return eventDate; }
    public LocalDate getLastDay()        { return lastDay; }
    public int       getMaxParticipants(){ return maxParticipants; }
    public boolean   isOfficial()        { return official; }
    public String    getType()           { return type; }

    @Override
    public String toString() { return "Race[" + name + "]"; }
}
