package model;

import model.composite.Race;
import model.composite.Stage;
import model.controller.OrganizerController;
import model.controller.RaceController;
import model.controller.RacerController;
import model.model.Administrator;
import model.model.Organizer;
import model.model.Racer;
import model.pattern.AdministratorController;
import model.view.RacerView;
import model.view.OrganizerView;
import model.view.RaceView;
import model.view.AdministratorView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    // Shared state (in a real app this would come from a database)
    static OrganizerController orgCtrl   = new OrganizerController();
    static RaceController      raceCtrl  = new RaceController();
    static RacerController     racerCtrl = new RacerController();

    static RacerView       racerView = new RacerView();
    static RaceView        raceView  = new RaceView();
    static OrganizerView   orgView   = new OrganizerView();
    static AdministratorView adminView = new AdministratorView();

    // Demo users
    static Racer racer1 = new Racer("Alice",   "R001", 1);
    static Racer         racer2 = new Racer("Bob",     "R002", 1);
    static Organizer org    = new Organizer("Carol", "O001");
    static Administrator admin  = new Administrator("Dave",  "A001");

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║   Racing Management System   ║");
        System.out.println("╚══════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> organizerMenu();
                case "2" -> racerMenu();
                case "3" -> adminMenu();
                case "4" -> demoAll();
                case "0" -> { System.out.println("Goodbye!"); running = false; }
                default  -> System.out.println("Invalid option.");
            }
        }
    }

    // ── Menus ─────────────────────────────────────────────────────────────

    static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Organizer actions");
        System.out.println("2. Racer actions");
        System.out.println("3. Admin actions");
        System.out.println("4. Run full demo");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    static void organizerMenu() {
        System.out.println("\n--- Organizer Menu ---");
        System.out.println("1. Create race");
        System.out.println("2. Add stage to race");
        System.out.println("3. List all races");
        System.out.println("4. View race details");
        System.out.println("5. View organizer profile");
        System.out.println("0. Back");
        System.out.print("Choice: ");
        String c = scanner.nextLine().trim();
        switch (c) {
            case "1" -> createRaceFlow();
            case "2" -> addStageFlow();
            case "3" -> orgCtrl.listRaces();
            case "4" -> viewRaceDetails();
            case "5" -> orgView.displayProfile(org);
            case "0" -> {}
            default  -> System.out.println("Invalid.");
        }
    }

    static void racerMenu() {
        System.out.println("\n--- Racer Menu ---");
        System.out.println("1. View racer profile (Alice)");
        System.out.println("2. View racer profile (Bob)");
        System.out.println("3. Sign up for a race");
        System.out.println("4. Purchase a license");
        System.out.println("5. View available races");
        System.out.println("6. View my race results");
        System.out.println("0. Back");
        System.out.print("Choice: ");
        String c = scanner.nextLine().trim();
        switch (c) {
            case "1" -> racerView.displayProfile(racer1);
            case "2" -> racerView.displayProfile(racer2);
            case "3" -> signUpFlow();
            case "4" -> purchaseLicenseFlow();
            case "5" -> racerView.showRaces(orgCtrl.getRaces());
            case "0" -> {}
            default  -> System.out.println("Invalid.");
        }
    }

    static void adminMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. View admin profile");
        System.out.println("2. Create license");
        System.out.println("3. Show all licenses");
        System.out.println("4. Show all racer results");
        System.out.println("0. Back");
        System.out.print("Choice: ");
        String c = scanner.nextLine().trim();
        switch (c) {
            case "1" -> adminView.displayProfile(admin);
            case "2" -> {
                System.out.print("Category (int): ");
                int cat = Integer.parseInt(scanner.nextLine().trim());
                AdministratorController.getInstance().createLicense(cat, LocalDate.now().plusYears(1));
            }
            case "3" -> adminView.showLicenses();
            case "4" -> adminView.showResults(Arrays.asList(racer1, racer2));
            case "0" -> {}
            default  -> System.out.println("Invalid.");
        }
    }

    // ── Flows ─────────────────────────────────────────────────────────────

    static void createRaceFlow() {
        System.out.print("Race name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Max participants: ");
        int max = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Category (int): ");
        int cat = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Type (road/circuit/rally): ");
        String type = scanner.nextLine().trim();

        Race r = orgCtrl.createRace(name,
                LocalDate.now().plusMonths(2),
                max, cat,
                LocalDate.now().plusMonths(1),
                true, type);
        org.addRaceID(orgCtrl.getRaces().size());
    }

    static void addStageFlow() {
        List<Race> races = orgCtrl.getRaces();
        if (races.isEmpty()) { System.out.println("No races yet."); return; }
        System.out.println("Select race:");
        for (int i = 0; i < races.size(); i++)
            System.out.println("  " + (i + 1) + ". " + races.get(i).getName());
        System.out.print("Choice: ");
        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (idx < 0 || idx >= races.size()) { System.out.println("Invalid."); return; }
        Race race = races.get(idx);

        System.out.print("Stage number: ");
        int num = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Miles: ");
        int miles = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Route description: ");
        String route = scanner.nextLine().trim();

        raceCtrl.addStage(race, new Stage(num, miles, route));
    }

    static void viewRaceDetails() {
        List<Race> races = orgCtrl.getRaces();
        if (races.isEmpty()) { System.out.println("No races yet."); return; }
        System.out.println("Select race:");
        for (int i = 0; i < races.size(); i++)
            System.out.println("  " + (i + 1) + ". " + races.get(i).getName());
        System.out.print("Choice: ");
        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (idx < 0 || idx >= races.size()) { System.out.println("Invalid."); return; }
        Race race = races.get(idx);
        raceCtrl.getDetails(race);
        raceView.showStatus(race);
        raceView.showPodium(race);
    }

    static void signUpFlow() {
        List<Race> races = orgCtrl.getRaces();
        if (races.isEmpty()) { System.out.println("No races available."); return; }
        System.out.println("Select racer: 1. Alice  2. Bob");
        System.out.print("Choice: ");
        Racer racer = scanner.nextLine().trim().equals("1") ? racer1 : racer2;
        System.out.println("Select race:");
        for (int i = 0; i < races.size(); i++)
            System.out.println("  " + (i + 1) + ". " + races.get(i).getName());
        System.out.print("Choice: ");
        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (idx < 0 || idx >= races.size()) { System.out.println("Invalid."); return; }
        racerCtrl.signUp(racer, races.get(idx));
    }

    static void purchaseLicenseFlow() {
        System.out.println("Select racer: 1. Alice  2. Bob");
        System.out.print("Choice: ");
        Racer racer = scanner.nextLine().trim().equals("1") ? racer1 : racer2;
        System.out.print("License category: ");
        int cat = Integer.parseInt(scanner.nextLine().trim());
        racerCtrl.purchaseLicense(racer, cat);
    }

    static void viewRaceResultsFlow() {
        System.out.println("Select racer: 1. Alice  2. Bob");
        System.out.print("Choice: ");
        Racer racer = scanner.nextLine().trim().equals("1") ? racer1 : racer2;
        racerView.showResults(racer, orgCtrl.getRaces());
    }

    // ── Full demo ─────────────────────────────────────────────────────────

    static void demoAll() {
        System.out.println("\n====== FULL DEMO ======");

        // 1. Organizer creates a race
        Race race = orgCtrl.createRace("Tour de Code", LocalDate.of(2026, 8, 1),
                3, 1, LocalDate.of(2026, 7, 1), true, "road");

        // 2. Add stages (Composite Pattern in action)
        raceCtrl.addStage(race, new Stage(1, 50,  "City Centre Loop"));
        raceCtrl.addStage(race, new Stage(2, 80,  "Mountain Pass"));
        raceCtrl.addStage(race, new Stage(3, 60,  "Coastal Sprint"));

        // 3. Racer purchases license
        racerCtrl.purchaseLicense(racer1, 1);
        racerCtrl.purchaseLicense(racer2, 1);

        // 4. Racers sign up
        racerCtrl.signUp(racer1, race);
        racerCtrl.signUp(racer2, race);

        // 5. View full race details (recursive composite traversal)
        System.out.println("\n-- Race details via RaceComponent.getDetails() --");
        raceCtrl.getDetails(race);

        // 6. Race status
        raceView.showStatus(race);

        // 7. Set podiums
        raceCtrl.setPodiums(race, Arrays.asList(racer1, racer2));

        // 8. Show podium
        raceView.showPodium(race);

        // 9. Racer results
        racerView.showResults(racer1, orgCtrl.getRaces());

        // 10. Admin — Singleton demo
        System.out.println("\n-- Singleton AdministratorController --");
        AdministratorController a1 = AdministratorController.getInstance();
        AdministratorController a2 = AdministratorController.getInstance();
        System.out.println("Same instance? " + (a1 == a2));
        adminView.showLicenses();

        // 11. Organizer notification
        racerCtrl.updateCategory(racer1, 2);
        orgView.notifiedRacerUpgrade(racer1);

        System.out.println("====== DEMO END ======\n");
    }
}
