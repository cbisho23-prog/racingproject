package model.model;

public abstract class User {
    protected String name;
    protected String ID;

    public User(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() { return name; }
    public String getID()   { return ID; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", ID=" + ID + "]";
    }
}
