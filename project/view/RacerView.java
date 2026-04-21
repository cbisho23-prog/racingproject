package model.view;

import model.model.Racer;
import model.composite.Race;
import java.util.List;

public class RacerView {

    public void displayProfile(Racer racer) {
        System.out.println("=== Racer Profile ===");
        System.out.println("Name     : " + racer.getName());
        System.out.println("ID       : " + racer.getID());
        System.out.println("Category : " + racer.getCategory());
        System.out.println("Podiums  : " + racer.getPodiumCount());
        System.out.println("Licenses : " + racer.getLicensesPurchased());
        System.out.println("=====================");
    }

    public void showRaces(List<Race> races) {
        System.out.println("=== Available Races ===");
        if (races.isEmpty()) { System.out.println("  None."); return; }
        for (Race r : races) r.showList();
        System.out.println("=======================");
    }

    public void showResults(Racer racer, List<Race> races) {
        System.out.println("=== Results for " + racer.getName() + " ===");
        for (Race r : races) {
            if (r.getPodiums().contains(racer)) {
                int pos = r.getPodiums().indexOf(racer) + 1;
                System.out.println("  " + r.getName() + " -> Position " + pos);
            }
        }
        System.out.println("Total podiums: " + racer.getPodiumCount());
        System.out.println("=============================");
    }
}
