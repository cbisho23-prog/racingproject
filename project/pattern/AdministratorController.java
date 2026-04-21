package model.pattern;

import model.model.License;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Pattern — only one AdministratorController can exist.
 */
public class AdministratorController {

    private static AdministratorController instance;

    private List<License> licenses = new ArrayList<>();
    private int nextLicenseID = 1;

    // Private constructor prevents external instantiation
    private AdministratorController() {}

    public static AdministratorController getInstance() {
        if (instance == null) {
            instance = new AdministratorController();
        }
        return instance;
    }

    public License createLicense(int category, LocalDate expireDate) {
        License l = new License(nextLicenseID++, category, LocalDate.now(), expireDate);
        licenses.add(l);
        System.out.println("License created: " + l);
        return l;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void printAllLicenses() {
        if (licenses.isEmpty()) { System.out.println("  No licenses on record."); return; }
        for (License l : licenses) System.out.println("  " + l);
    }
}
