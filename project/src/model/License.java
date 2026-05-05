package model;

import java.time.LocalDate;

public class License {
    private LocalDate dateIssue;
    private int ID;
    private LocalDate expireDate;
    private int category;

    public License(int ID, int category, LocalDate dateIssue, LocalDate expireDate) {
        this.ID        = ID;
        this.category  = category;
        this.dateIssue = dateIssue;
        this.expireDate = expireDate;
    }

    public int getID()               { return ID; }
    public int getCategory()         { return category; }
    public LocalDate getDateIssue()  { return dateIssue; }
    public LocalDate getExpireDate() { return expireDate; }

    public boolean isValid() {
        return LocalDate.now().isBefore(expireDate);
    }

    @Override
    public String toString() {
        return "License[ID=" + ID + ", category=" + category
                + ", issued=" + dateIssue + ", expires=" + expireDate
                + ", valid=" + isValid() + "]";
    }
}
