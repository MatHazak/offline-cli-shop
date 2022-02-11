import java.util.LinkedList;
import java.util.List;

/**
 * Customer side of shop. customer attributes and all operations related to them.
 *
 * @author MatHazak
 */

public class Customer {
    private final String username;
    private String password;
    private int balance = 0;
    private final List<Item> purchasedItems = new LinkedList<>();
    private String firstName = "\"blank\"";
    private String lastName = "\"blank\"";
    private String email = "\"blank\"";
    private String address = "\"blank\"";
    private String postalCode = "\"blank\"";
    
    
    // constructor
    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    
    // operations
    
    public String accountInfo() {
        return 	"** User Role: Customer" +
                "\n** Username: " + username +
                "\n1. Password: " + password +
                "\n2. First Name: " + firstName +
                "\n3. Last Name: " + lastName +
                "\n4. Email: " + email +
                "\n5. Address: " + address +
                "\n6. Postal Code: " + postalCode +
                "\n** Total Balance: " + balance;
    }
    
    // List of customers purchased items to print
    public String purchasedItems() {
        String out = "   Name\tID\tPrice\tTag\n";
        int i = 1;
        for (Item item : purchasedItems) {
            out = out.concat(i++ + "> " +item.getName() + "\t" + item.getId() + "\t" + item.getPrice() + "\t" + item.getTag() + "\n");
        }
        if (i == 1) {
            out = "you didn't purchase any item yet.";
        }
        return out;
    }
    
    // returns true if item purchased successfully
    public boolean purchaseItem(int id) {
        Item newItem = Admin.getItem(id);
        if (balance >= newItem.getPrice()) {
            balance -= newItem.getPrice();
            purchasedItems.add(newItem);
            Data.writeLog("<purchase item> username: " + username + " - item: " + newItem.getName() + " - price: " + newItem.getPrice() + " - tag: " + newItem.getTag() + " - ID: " + newItem.getId());
            Admin.removeItem(id);
            return true;
        } else {
            return false;
        }
    }
    
    public void increaseBalance (int amount) {
        balance += amount;
    }
    
    // List of customers purchased items to store in database
    public String purchasedItemsDatabase() {
        String output = "";
        for (Item item : purchasedItems) {
            output = output.concat(item.getName() + "\n" + item.getId() + "\n" + item.getPrice() + "\n" + item.getTag() + "\n");
        }
        return output.concat("0");
    }
    
    // Adding purchased items to customers list from database
    public void addPurchasedItemsDatabase(Item item) {
        purchasedItems.add(item);
    }
    
    
    // setters
    
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
    
    
    // getters
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public int getBalance() {
        return balance;
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