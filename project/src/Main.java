package main;

import model.composite.Race;
import model.composite.Stage;
import controller.OrganizerController;
import controller.RaceController;
import controller.RacerController;
import model.Administrator;
import model.Organizer;
import model.Racer;
import controller.AdministratorController;
import view.RacerView;
import view.OrganizerView;
import view.RaceView;
import view.AdministratorView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    // Shared state
    static OrganizerController orgCtrl   = new OrganizerController();
    static RaceController      raceCtrl  = new RaceController();
    static RacerController     racerCtrl = new RacerController();

    static RacerView       racerView = new RacerView();
    static RaceView        raceView  = new RaceView();
    static OrganizerView   orgView   = new OrganizerView();
    static AdministratorView adminView = new AdministratorView();

    // Registered racers list
    static ArrayList<Racer> registeredRacers = new ArrayList<>();
    
    // Demo users
    static Racer racer1 = new Racer("Alice", "R001", 1);
    static Racer racer2 = new Racer("Bob", "R002", 1);
    static Organizer org = new Organizer("Carol", "O001");
    static Administrator admin = new Administrator("Dave", "A001");
    
    // Logged in racer
    static Racer loggedInRacer = null;

    static {
        registeredRacers.add(racer1);
        registeredRacers.add(racer2);
    }

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║   Racing Management System   ║");
        System.out.println("╚══════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> loginFlow();
                case "2" -> registerFlow();
                case "3" -> organizerMenu();
                case "4" -> adminMenu();
                case "5" -> demoAll();
                case "0" -> { System.out.println("Goodbye!"); running = false; }
                default  -> System.out.println("Invalid option.");
            }
        }
    }

    // ── Main Menu ─────────────────────────────────────────────────────────

    static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Organizer actions");
        System.out.println("4. Admin actions");
        System.out.println("5. Run full demo");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    // ── Registration ───────────────────────────────────────────────────────

    static void registerFlow() {
        System.out.println("\n--- Register New Racer ---");
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter category (1-5, 1 is best): ");
        int cat = Integer.parseInt(scanner.nextLine().trim());
        
        String racerId = "R" + (registeredRacers.size() + 100);
        Racer newRacer = new Racer(name, racerId, cat);
        registeredRacers.add(newRacer);
        
        System.out.println("\n✅ Account created successfully!");
        System.out.println("Your Racer ID is: " + racerId);
        System.out.println("Please login using your ID.\n");
    }

    // ── Login ──────────────────────────────────────────────────────────────

    static void loginFlow() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter your Racer ID: ");
        String id = scanner.nextLine().trim();
        
        for (Racer r : registeredRacers) {
            if (r.getID().equals(id)) {
                loggedInRacer = r;
                System.out.println("✅ Login successful! Welcome " + r.getName());
                racerMenuLoggedIn();
                return;
            }
        }
        System.out.println("❌ Racer ID not found. Please register first.\n");
    }

    // ── Racer Menu (Logged In) ─────────────────────────────────────────────

    static void racerMenuLoggedIn() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- Racer Menu ---");
            System.out.println("Welcome " + loggedInRacer.getName() + " (Category: " + loggedInRacer.getCategory() + ")");
            System.out.println("1. View my profile");
            System.out.println("2. Purchase a license");
            System.out.println("3. Sign up for a race");
            System.out.println("4. View available races");
            System.out.println("5. View my race results");
            System.out.println("0. Logout");
            System.out.print("Choice: ");
            String c = scanner.nextLine().trim();
            switch (c) {
                case "1" -> racerView.displayProfile(loggedInRacer);
                case "2" -> purchaseLicenseForLoggedInRacer();
                case "3" -> signUpForRaceLoggedIn();
                case "4" -> racerView.showRaces(orgCtrl.getRaces());
                case "5" -> racerView.showResults(loggedInRacer, orgCtrl.getRaces());
                case "0" -> { 
                    System.out.println("Logged out.\n");
                    loggedInRacer = null;
                    inMenu = false;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static void purchaseLicenseForLoggedInRacer() {
        System.out.print("Enter license category (1-5): ");
        int cat = Integer.parseInt(scanner.nextLine().trim());
        racerCtrl.purchaseLicense(loggedInRacer, cat);
    }

    static void signUpForRaceLoggedIn() {
        List<Race> races = orgCtrl.getRaces();
        if (races.isEmpty()) { 
            System.out.println("No races available."); 
            return; 
        }
        System.out.println("Select race:");
        for (int i = 0; i < races.size(); i++)
            System.out.println("  " + (i + 1) + ". " + races.get(i).getName());
        System.out.print("Choice: ");
        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (idx < 0 || idx >= races.size()) { 
            System.out.println("Invalid."); 
            return; 
        }
        racerCtrl.signUp(loggedInRacer, races.get(idx));
    }

    // ── Organizer Menu ─────────────────────────────────────────────────────

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

    // ── Admin Menu ─────────────────────────────────────────────────────────

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
            case "4" -> adminView.showResults(registeredRacers);
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

    // ── Full demo ─────────────────────────────────────────────────────────

    static void demoAll() {
        System.out.println("\n====== FULL DEMO ======");

        Race race = orgCtrl.createRace("Tour de Code", LocalDate.of(2026, 8, 1),
                3, 1, LocalDate.of(2026, 7, 1), true, "road");

        raceCtrl.addStage(race, new Stage(1, 50, "City Centre Loop"));
        raceCtrl.addStage(race, new Stage(2, 80, "Mountain Pass"));
        raceCtrl.addStage(race, new Stage(3, 60, "Coastal Sprint"));

        racerCtrl.purchaseLicense(racer1, 1);
        racerCtrl.purchaseLicense(racer2, 1);

        racerCtrl.signUp(racer1, race);
        racerCtrl.signUp(racer2, race);

        System.out.println("\n-- Race details via RaceComponent.getDetails() --");
        raceCtrl.getDetails(race);

        raceView.showStatus(race);

        raceCtrl.setPodiums(race, Arrays.asList(racer1, racer2));

        raceView.showPodium(race);

        racerView.showResults(racer1, orgCtrl.getRaces());

        System.out.println("\n-- Singleton AdministratorController --");
        AdministratorController a1 = AdministratorController.getInstance();
        AdministratorController a2 = AdministratorController.getInstance();
        System.out.println("Same instance? " + (a1 == a2));
        adminView.showLicenses();

        racerCtrl.updateCategory(racer1, 2);
        orgView.notifiedRacerUpgrade(racer1);

        System.out.println("====== DEMO END ======\n");
    }
}