package model.controller;

import model.composite.Race;
import model.model.License;
import model.model.Racer;
import model.pattern.AdministratorController;
import java.time.LocalDate;

public class RacerController {

    public void signUp(Racer racer, Race race) {
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
