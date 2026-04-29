package view;

import model.Organizer;
import model.composite.Race;
import model.Racer;
import java.util.List;

public class OrganizerView {

    public void displayProfile(Organizer organizer) {
        System.out.println("=== Organizer Profile ===");
        System.out.println("Name  : " + organizer.getName());
        System.out.println("ID    : " + organizer.getID());
        System.out.println("Races : " + organizer.getRaceList());
        System.out.println("=========================");
    }

    public void showRaces(List<Race> races) {
        System.out.println("=== Managed Races ===");
        if (races.isEmpty()) { System.out.println("  None."); return; }
        for (Race r : races) r.getDetails();
        System.out.println("=====================");
    }

    public void showResults(List<Race> races) {
        System.out.println("=== Race Results ===");
        for (Race r : races) {
            System.out.println("Race: " + r.getName());
            List<Racer> podium = r.getPodiums();
            if (podium.isEmpty()) { System.out.println("  No results yet."); continue; }
            for (int i = 0; i < podium.size(); i++)
                System.out.println("  " + (i + 1) + ". " + podium.get(i).getName());
        }
        System.out.println("====================");
    }

    public void notifiedRacerUpgrade(Racer racer) {
        System.out.println("[NOTIFICATION] Racer " + racer.getName()
                + " has been upgraded to category " + racer.getCategory());
    }
}
