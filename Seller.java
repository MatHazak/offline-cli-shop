import java.util.LinkedList;
import java.util.List;

/**
 * Seller side of shop. seller attributes and all operations related to them.
 *
 * @author MatHazak
 */

public class Seller {
    private final String username;
    private String password;
    private boolean verification;
    private final List<Integer> itemsId = new LinkedList<>(); // List of customers item stored as their ID
    private String firstName = "\"blank\"";
    private String lastName = "\"blank\"";
    private String email = "\"blank\"";
    private String address = "\"blank\"";
    private String postalCode = "\"blank\"";
    
    
    // Constructor
    public Seller(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    
    // Operations
    
    public String accountInfo() {
        return "** User Role: Seller" +
                "\n** Username: " + username +
                "\n1. Password: " + password +
                "\n2. First Name: " + firstName +
                "\n3. Last Name: " + lastName +
                "\n4. Email: " + email +
                "\n5. Address: " + address +
                "\n6. Postal Code: " + postalCode +
                "\n** Verification Status: " + (verification ? "verified" : "unverified");
    }
    
    public void addItem(String name, int price, String tag) {
        if (tag.equals("")) tag = "\"blank\"";
        Item newItem = new Item(name, price, tag);
        itemsId.add(newItem.getId());
        Admin.addItem(newItem.getId(), newItem);
        System.out.println("item with ID " + newItem.getId() + " added successfully.");
        Data.writeLog("<add item> username: " + username + " - item: " + name + " - price: " + price + " - tag: " + tag + " - ID: " + newItem.getId());
    }
    
    // List of sellers item to print
    public String itemsList() {
        String out = "   Name\tID\tPrice\tTag\n";
        int i = 1;
        for (int itemId : itemsId) {
            Item item = Admin.getItem(itemId);
            if (item == null) continue;
            out = out.concat(i++ + "> " + item.getName() + "\t" + item.getId() + "\t" + item.getPrice() + "\t" + item.getTag() + "\n");
        }
        if (i ==1) {
            out = "you don't have available item.";
        }
        return out;
    }
    
    // Returns true if item removed successfully
    public boolean removeItem(int id) {
        Integer idInt = id; // to remove item from itemsId List, we need Integer object of ID
        for (Integer itemId : itemsId) {
            if (itemId.equals(id)) {
                if (! Admin.itemAvailable(id)) break; // checks if admin has removed this item or not.
                itemsId.remove(idInt);
                Item removedItem = Admin.getItem(id);
                Data.writeLog("<remove item> username: " + username + " - item: " + removedItem.getName() + " - price: " + removedItem.getPrice() + " - tag: " + removedItem.getTag() + " - ID: " + id);
                Admin.removeItem(id);
                return true;
            }
        }
        return false;
    }
    
    // Remove all sellers items if admin removed themselves.
    public void removeAllItems() {
        for(Integer itemId : itemsId) {
            Admin.removeItem(itemId); // we just need to remove item from admins item map.
        }
    }
    
    // List of sellers items to store in database
    public String itemsListDatabase() {
        String output = "";
        for(int itemId : itemsId) {
            output = output.concat(itemId + "\n");
        }
        return output.concat("0");
    }
    
    // Adding sellers items to sellers list from database
    public void addItemDatabase(int id) {
        itemsId.add(id);
    }
    
    
    // Setter
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public void setVerification() {
        verification = true;
    }
    
    
    // Getter
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public boolean isVerified() {
        return verification;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
}