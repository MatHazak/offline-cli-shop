/**
 * Item object. its attributes and required operations.
 *
 * @author MatHazak
 */

public class Item {
    private static int idFeed = 0; // A feed to assign a unique ID to each item.
    
    private final String name;
    private final int id;
    private final int price;
    private final String tag;
    
    // Constructor used customer
    public Item(String name, int price, String tag) {
        this.name = name;
        idFeed += 1 + (int) (Math.random() * 10);
        this.id = idFeed;
        this.price = price;
        this.tag = tag;
    }
    
    // Constructor used by database
    public Item(String name, int id, int price, String tag) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.tag = tag;
    }
    
    // Setter used by database
    public static void setIdFeed(int lastFeed) {
        idFeed = lastFeed;
    }
    
    
    // Getter
    
    public static int getIdFeed() {
        return idFeed;
    }
    
    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public int getPrice() {
        return price;
    }
    
    public String getTag() {
        return tag;
    }
}