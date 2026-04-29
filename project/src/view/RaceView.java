package view;

import model.composite.Race;
import model.Racer;
import java.util.List;

public class RaceView {

    public void showPodium(Race race) {
        System.out.println("=== Podium: " + race.getName() + " ===");
        List<Racer> podiums = race.getPodiums();
        if (podiums.isEmpty()) { System.out.println("  No results yet."); return; }
        String[] medals = {"1st", "2nd", "3rd"};
        for (int i = 0; i < podiums.size(); i++) {
            System.out.println("  " + medals[i] + ": " + podiums.get(i).getName());
        }
        System.out.println("================================");
    }

    public void showStatus(Race race) {
        System.out.println("=== Race Status: " + race.getName() + " ===");
        System.out.println("  Participants : " + race.getSignedUp().size() + "/" + race.getMaxParticipants());
        System.out.println("  Full         : " + race.isFull());
        System.out.println("  Stages       : " + race.getStages().size());
        System.out.println("  Event date   : " + race.getEventDate());
        System.out.println("  Reg. closes  : " + race.getLastDay());
        System.out.println("=====================================");
    }
}
