package controller;

import model.composite.Race;
import model.License;
import model.Racer;
import controller.AdministratorController;
import java.time.LocalDate;

public class RacerController {

    public void signUp(Racer racer, Race race) {
        // Check if racer has a valid license
        if (racer.getLicensesPurchased().isEmpty()) {
            System.out.println("Error: You need a valid license to register for a race.");
            return;
        }
        
        // Check if race is full
        if (race.isFull()) {
            System.out.println("Error: Race is at maximum capacity.");
            return;
        }
        
        if (race.signUp(racer)) {
            System.out.println(racer.getName() + " successfully signed up for " + race.getName());
        }
    }

    public void purchaseLicense(Racer racer, int category) {
        AdministratorController adminCtrl = AdministratorController.getInstance();
        License l = adminCtrl.createLicense(category, LocalDate.now().plusYears(1));
        racer.addLicense(String.valueOf(l.getID()));
        System.out.println(racer.getName() + " purchased license ID " + l.getID());
    }

    public void updateCategory(Racer racer, int newCategory) {
        racer.setCategory(newCategory);
        System.out.println(racer.getName() + "'s category updated to " + newCategory);
    }

    public void updatePaymentInfo(Racer racer, char info) {
        racer.setPaymentInfo(info);
        System.out.println(racer.getName() + "'s payment info updated.");
    }
}
