package model;

import java.util.ArrayList;
import java.util.List;

public class Organizer extends User {
    private List<Integer> raceList = new ArrayList<>();

    public Organizer(String name, String ID) {
        super(name, ID);
    }

    public List<Integer> getRaceList() { return raceList; }
    public void addRaceID(int raceID)  { raceList.add(raceID); }

    @Override
    public String toString() {
        return "Organizer[name=" + name + ", ID=" + ID + ", races=" + raceList + "]";
    }
}
