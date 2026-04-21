package model.model;

import java.util.ArrayList;
import java.util.List;

public class Racer extends User {
    private char paymentInfo;
    private int  category;
    private int  podiumCount;
    private List<String> licensesPurchased = new ArrayList<>();

    public Racer(String name, String ID, int category) {
        super(name, ID);
        this.category = category;
    }

    public char getPaymentInfo()            { return paymentInfo; }
    public void setPaymentInfo(char p)      { this.paymentInfo = p; }
    public int  getCategory()               { return category; }
    public void setCategory(int c)          { this.category = c; }
    public int  getPodiumCount()            { return podiumCount; }
    public void incrementPodium()           { this.podiumCount++; }
    public List<String> getLicensesPurchased() { return licensesPurchased; }
    public void addLicense(String licenseID)   { licensesPurchased.add(licenseID); }

    @Override
    public String toString() {
        return "Racer[name=" + name + ", ID=" + ID
                + ", category=" + category + ", podiums=" + podiumCount + "]";
    }
}
