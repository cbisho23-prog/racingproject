package model;

public class Administrator extends User {

    public Administrator(String name, String ID) {
        super(name, ID);
    }

    @Override
    public String toString() {
        return "Administrator[name=" + name + ", ID=" + ID + "]";
    }
}
