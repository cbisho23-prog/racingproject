package view;

import model.Administrator;
import model.Racer;
import controller.AdministratorController;
import java.util.List;

public class AdministratorView {

    public void displayProfile(Administrator admin) {
        System.out.println("=== Administrator Profile ===");
        System.out.println("Name : " + admin.getName());
        System.out.println("ID   : " + admin.getID());
        System.out.println("=============================");
    }

    public void showLicenses() {
        System.out.println("=== All Licenses ===");
        AdministratorController.getInstance().printAllLicenses();
        System.out.println("====================");
    }

    public void showResults(List<Racer> racers) {
        System.out.println("=== All Racer Results ===");
        for (Racer r : racers)
            System.out.println("  " + r.getName() + " — podiums: " + r.getPodiumCount()
                    + " | category: " + r.getCategory());
        System.out.println("=========================");
    }
}
