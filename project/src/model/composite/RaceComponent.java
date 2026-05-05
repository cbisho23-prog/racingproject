package model.composite;

/**
 * Component interface for the Composite Pattern.
 * Both Stage (leaf) and Race (composite) implement this.
 */
public interface RaceComponent {
    void getDetails();
    void showList();
    boolean isFull();
}
