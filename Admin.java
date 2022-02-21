import java.util.*;

/**
 * Management side of shop. sellers, customers, items and some management operation.
 *
 * @author MatHazak
 */

public class Admin {
    private static final List<Seller> sellers = new LinkedList<>();
    private static final List<Customer> customers = new LinkedList<>();
    private static final Map<String, String> userMap = new HashMap<>(); // mapping usernames to roles
    private static final Map<Integer, Item> itemMap = new HashMap<>(); // mapping items ID to items object
    
    
    // Add operations
    
    public static void addAdmin() {
        userMap.put("admin", "admin");
    }
    
    public static void addUser(String role, String username, String password) {
        if (role.equals("seller")) {
            userMap.put(username, role);
            Seller newSeller = new Seller(username, password);
            sellers.add(newSeller);
        } else {
            userMap.put(username, role);
            Customer newCustomer = new Customer(username, password);
            customers.add(newCustomer);
        }
    }
    
    public static void addItem(int id, Item item) {
        itemMap.put(id, item);
    }
    
    
    // modify operations
    
    public static void verifySeller(String username) {
        for (Seller seller : sellers) {
            if (seller.getUsername().equals(username)) {
                seller.setVerification();
                System.out.println("seller with username \"" + username + "\" verified successfully.");
                return;
            }
        }
        System.out.println("there is no such seller with username \"" + username + "\" between signed up sellers.");
    }
    
    public static void removeUser(String username) {
        if (userRole(username).equals("seller")) {
            for (Seller seller : sellers) {
                if (seller.getUsername().equals(username)) {
                    seller.removeAllItems();
                    userMap.remove(username);
                    sellers.remove(seller);
                    return;
                }
            }
        } else {
            for (Customer customer : customers) {
                if (customer.getUsername().equals(username)) {
                    userMap.remove(username);
                    customers.remove(customer);
                }
            }
        }
    }
    
    public static void removeItem (int id) {
        itemMap.remove(id);
    }
    
    
    // string getters
    
    public static String unverifiedSellers() {
        String out = "   Username\t\tPassword\t\tFirst Name\t\tLast Name\t\tEmail\t\tAddress\t\tPostal Code\n";
        int i = 1;
        for (Seller seller : sellers) {
            if (!seller.isVerified()) {
                out = out.concat(i++ + "> " + seller.getUsername() + "\t\t  " + seller.getPassword() + "\t\t  " + seller.getFirstName() +
                        "\t\t  " + seller.getLastName() + "\t\t  " + seller.getEmail() + "\t\t  " + seller.getAddress() + "\t\t  " + seller.getPostalCode() + "\n");
            }
        }
        return out;
    }
    
    public static String userList() {
        String sellerList = "Sellers:\n   Username\t\tPassword\t\tFirst Name\t\tLast Name\t\tEmail\t\tAddress\t\tPostal Code\t\tVerification Status\n";
        int i = 1, j = 1;
        for (Seller seller : sellers) {
            sellerList = sellerList.concat(i++ + "> " + seller.getUsername() + "\t\t  " + seller.getPassword() + "\t\t  " + seller.getFirstName() +
                    "\t\t  " + seller.getLastName() + "\t\t  " + seller.getEmail() + "\t\t  " + seller.getAddress() +
                    "\t\t  " + seller.getPostalCode() + "\t\t  " + (seller.isVerified()? "verified" : "unverified") + "\n");
        }
        if (i == 1) {
            sellerList = "there is no any signed up seller yet.\n";
        }
        
        String customersList = "Customers:\n   Username\t\tPassword\t\tFirst Name\t\tLast Name\t\tEmail\t\tAddress\t\tPostal Code\t\tTotal Balance\n";
        for (Customer customer : customers) {
            customersList = customersList.concat(j++ + "> " + customer.getUsername() + "\t\t  " + customer.getPassword() + "\t\t  " + customer.getFirstName() + "\t\t  " + customer.getLastName() +
                    "\t\t  " + customer.getEmail() + "\t\t  " + customer.getAddress() + "\t\t  " + customer.getPostalCode() + "\t\t  " + customer.getBalance() + "\n");
        }
        if (j == 1) {
            customersList = "there is no any signed up customer yet.\n";
        }
        
        return sellerList.concat("\n").concat(customersList);
    }
    
    // getting List of all items
    public static String itemsList() {
        String out = "   Name\t\tID\t\tPrice\t\tTag\n";
        int i = 1;
        for (Item item : itemMap.values()) {
            out = out.concat(i++ + "> " + item.getName() + "\t\t  " + item.getId() + "\t\t  " + item.getPrice() + "\t\t  " + item.getTag() + "\n");
        }
        if (i == 1) {
            out = "there is no any item in the list yet.";
        }
        return out;
    }
    
    // getting List of searched items by tag
    public static String itemsList(String tag) {
        String out = "items with tag \"" + tag + "\":\n   Name\t\tID\t\tPrice\n";
        int i = 1;
        for (Item item : itemMap.values()) {
            // tags can be more than one e.g. "electrical, sport".
            if(item.getTag().contains(tag)) {
                out = out.concat(i++ + "> " +item.getName() + "\t\t" + item.getId() + "\t\t" + item.getPrice() + "\n");
            }
        }
        if (i == 1) {
            out = "there is no any item with tag \"" + tag + "\" in list.";
        }
        return out;
    }
    
    public static String userRole(String username) {
        return userMap.get(username);
    }
    
    
    
    // boolean getter
    
    public static boolean itemAvailable(int id) {
        return itemMap.containsKey(id);
    }
    
    public static boolean userAvailable(String username) {
        return userMap.containsKey(username);
    }
    
    
    // object getters
    
    public static Seller getSeller(String username, String password) {
        for (Seller seller : sellers) {
            if (seller.getUsername().equals(username) && seller.getPassword().equals(password)) {
                return seller;
            }
        }
        return null;
    }
    
    public static Customer getCustomer(String username, String password) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }
    
    public static Item getItem(int id) {
        return itemMap.get(id);
    }
    
    
    // database writer
    
    public static void writeSellers() {
        for (Seller seller : sellers) {
            Data.writeDataBase(seller.getUsername());
            Data.writeDataBase(seller.getPassword());
            Data.writeDataBase(seller.isVerified()? "1" : "0");
            Data.writeDataBase(seller.getFirstName());
            Data.writeDataBase(seller.getLastName());
            Data.writeDataBase(seller.getEmail());
            Data.writeDataBase(seller.getAddress());
            Data.writeDataBase(seller.getPostalCode());
            Data.writeDataBase(seller.itemsListDatabase());
        }
        Data.writeDataBase("---/---");
    }
    
    public static void writeCustomers() {
        for (Customer customer : customers) {
            Data.writeDataBase(customer.getUsername());
            Data.writeDataBase(customer.getPassword());
            Data.writeDataBase(customer.getBalance());
            Data.writeDataBase(customer.getFirstName());
            Data.writeDataBase(customer.getLastName());
            Data.writeDataBase(customer.getEmail());
            Data.writeDataBase(customer.getAddress());
            Data.writeDataBase(customer.getPostalCode());
            Data.writeDataBase(customer.purchasedItemsDatabase());
        }
        Data.writeDataBase("---/---");
    }
    
    public static void writeItems() {
        for (Item item : itemMap.values()) {
            Data.writeDataBase(item.getName());
            Data.writeDataBase(item.getId());
            Data.writeDataBase(item.getPrice());
            Data.writeDataBase(item.getTag());
        }
        Data.writeDataBase("---/---");
        Data.writeDataBase(String.valueOf(Item.getIdFeed()));
    }
    
    
    // database reader
    
    public static void readSellers() {
        while (true) {
            String username = Data.readDataBase();
            if (username.equals("---/---")) break;
            String password = Data.readDataBase();
            Seller seller = new Seller(username, password);
            sellers.add(seller);
            userMap.put(username, "seller");
            if (Data.readDataBase().equals("1")) seller.setVerification();
            seller.setFirstName(Data.readDataBase());
            seller.setLastName(Data.readDataBase());
            seller.setEmail(Data.readDataBase());
            seller.setAddress(Data.readDataBase());
            seller.setPostalCode(Data.readDataBase());
            while (true) {
                String id = Data.readDataBase();
                if (id.equals("0")) break;
                seller.addItemDatabase(Integer.parseInt(id));
            }
        }
    }
    
    public static void readCustomers() {
        while (true) {
            String username = Data.readDataBase();
            if (username.equals("---/---")) break;
            String password = Data.readDataBase();
            Customer customer = new Customer(username, password);
            customers.add(customer);
            userMap.put(username, "customer");
            customer.increaseBalance(Integer.parseInt(Data.readDataBase()));
            customer.setFirstName(Data.readDataBase());
            customer.setLastName(Data.readDataBase());
            customer.setEmail(Data.readDataBase());
            customer.setAddress(Data.readDataBase());
            customer.setPostalCode(Data.readDataBase());
            while (true) {
                String name = Data.readDataBase();
                if (name.equals("0")) break;
                int id = Integer.parseInt(Data.readDataBase());
                int price = Integer.parseInt(Data.readDataBase());
                String tag = Data.readDataBase();
                Item item = new Item(name, id, price, tag);
                customer.addPurchasedItemsDatabase(item);
            }
        }
    }
    
    public static void readItems() {
        while (true) {
            String name = Data.readDataBase();
            if (name.equals("---/---")) break;
            int id = Integer.parseInt(Data.readDataBase());
            int price = Integer.parseInt(Data.readDataBase());
            String tag = Data.readDataBase();
            Item item = new Item(name, id, price, tag);
            itemMap.put(id, item);
        }
        Item.setIdFeed(Integer.parseInt(Data.readDataBase()));
    }
}
