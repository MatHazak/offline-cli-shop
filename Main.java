import java.io.IOException;
import java.util.Scanner;

/**
 * An offline shop with a command line interface.
 *
 * @author MatHazak
 */

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner myScanner = new Scanner(System.in);
        Data.runDatabaseReader();
        Data.runLog();
        Admin.addAdmin();
        if (Data.nonEmptyDatabase()) {
            Admin.readSellers();
            Admin.readCustomers();
            Admin.readItems();
        }
        System.out.println("welcome to our offline shop. Here is some options to start with:");
        String mainMenu;
        do {
            System.out.println("""
					Main Menu:
					\t1. sign up
					\t2. log in
					\t3. close""");
            mainMenu = myScanner.nextLine();
            switch (mainMenu) {
                case "1" -> {
                    System.out.println("in each step you can return to Main Menu by \"back\" command.");
                    String username;
                    String password;
                    String role;
                    while (true) {
                        System.out.println("pleas choose a role: \"customer\" to buy items or \"seller\" to sell your goods.");
                        role = myScanner.nextLine().toLowerCase();
                        if (role.isBlank()) {
                            System.out.println("your input is invalid. please try again.");
                            continue;
                        }
                        if (role.equals("back")) break;
                        if (! (role.equals("customer") || role.equals("seller"))) {
                            System.out.println("you can't sign up as \"" + role + "\"; there is only two option: \"customer\" and \"seller\".");
                            continue;
                        }
                        
                        System.out.println("please choose a username; usernames are case-insensitive.");
                        username = myScanner.nextLine().toLowerCase();
                        if (username.isBlank()) {
                            System.out.println("invalid username. please try again.");
                            continue;
                        }
                        if (username.equals("back")) break;
                        if (Admin.userAvailable(username)) {
                            System.out.println("unfortunately this username already have been taken. try another username or enter \"back\" to return to Main Menu.");
                            continue;
                        }
                        
                        System.out.println("please choose a password; in spite of usernames, passwords are case-sensitive!");
                        password = myScanner.nextLine();
                        if (password.isBlank()) {
                            System.out.println("your input is invalid. please try again.");
                            continue;
                        }
                        if (password.equals("back")) break;
                        if (password.isBlank()) {
                            System.out.println("invalid password. pleas try again.");
                            continue;
                        }
                        Admin.addUser(role, username, password);
                        System.out.println("congrats! you successfully signed up as a " + role + ". now you can log in and complete your profile.");
                        break;
                    }
                }
                case "2" -> {
                    System.out.println("in each step you can return to Main Menu by \"back\" command.");
                    String username;
                    String password;
                    String role;
                    while (true) {
                        System.out.println("please enter your username.");
                        username = myScanner.nextLine().toLowerCase();
                        if (username.isBlank()) {
                            System.out.println("your input is invalid. please try again.");
                            continue;
                        }
                        if (username.equals("back")) break;
                        role = Admin.userRole(username);
                        if (role == null) {
                            System.out.println("there is no such user between signed up users." +
                                    "\nplease try again or return to Main Menu by entering \"back\" and sign up first.");
                            continue;
                        }
                        
                        System.out.println("please enter your password. caution: passwords are case-sensitive!");
                        password = myScanner.nextLine();
                        if (password.isBlank()) {
                            System.out.println("your input is invalid. please try again.");
                            continue;
                        }
                        if (password.equals("back")) break;
                        
                        switch (role) {
                            case "customer" -> {
                                Customer customer = Admin.getCustomer(username, password);
                                if (customer == null) {
                                    System.out.println("password doesn't match with username; it's wrong." +
                                            "\nplease try again or return to Main Menu by entering \"back\" and sign up first.");
                                    continue;
                                }
                                System.out.println("you successfully logged in as a customer. Here is your available commands:");
                                String customerMenu;
                                do {
                                    System.out.println("""
											customer Menu:
											\t1. account info
											\t2. purchased items
											\t3. increase balance
											\t4. list of items
											\t5. search items by tag
											\t6. log out""");
                                    
                                    customerMenu = myScanner.nextLine();
                                    switch (customerMenu) {
                                        case "1" -> {
                                            while (true) {
                                                System.out.println(customer.accountInfo());
                                                System.out.println("Enter a number to modify correspond field; ** fields are unalterable." +
                                                        "\nyou can return to Customer Menu by entering \"back\".");
                                                String fieldNumber = myScanner.nextLine();
                                                if (fieldNumber.equals("back")) break;
                                                String input;
                                                switch (fieldNumber) {
                                                    case "1" -> {
                                                        System.out.println("input new password:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        customer.setPassword(input);
                                                    }
                                                    case "2" -> {
                                                        System.out.println("input new first name:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        customer.setFirstName(input);
                                                    }
                                                    case "3" -> {
                                                        System.out.println("input new last name:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        customer.setLastName(input);
                                                    }
                                                    case "4" -> {
                                                        System.out.println("input new email:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        customer.setEmail(input);
                                                    }
                                                    case "5" -> {
                                                        System.out.println("input new address:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        customer.setAddress(input);
                                                    }
                                                    case "6" -> {
                                                        System.out.println("input new postal code:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        customer.setPostalCode(input);
                                                    }
                                                    default -> System.out.println("your input is invalid. please try again.");
                                                }
                                            }
                                        }
                                        case "2" -> {
                                            System.out.println(customer.purchasedItems());
                                            System.out.println("press \"Enter\" to return to Customer Menu.");
                                            String breakPoint = myScanner.nextLine();
                                        }
                                        case "3" -> {
                                            System.out.println("your current balance is " + customer.getBalance() + ". enter your desired amount to increase balance.");
                                            while (true) {
                                                System.out.println("enter \"back\" to return to Customer Menu.");
                                                String entry = myScanner.nextLine().toLowerCase();
                                                if (entry.equals("back")) break;
                                                if (! entry.matches("\\d+")) {
                                                    System.out.println("your input is invalid. please enter a positive integer.");
                                                    continue;
                                                }
                                                int amount = Integer.parseInt(entry);
                                                customer.increaseBalance(amount);
                                                System.out.println("your balance increased successfully. current balance is " + customer.getBalance() + ".");
                                                Data.writeLog("<increase balance> username: " + customer.getUsername() + " - amount: " + amount);
                                                break;
                                            }
                                        }
                                        case "4" -> {
                                            while (true) {
                                                System.out.println(Admin.itemsList());
                                                System.out.println("you can buy items by their IDs; just enter desired items ID." +
                                                        "\nalso you can return to Customer Menu by entering \"back\".");
                                                String input = myScanner.nextLine().toLowerCase();
                                                if (input.equals("back")) break;
                                                if (! input.matches("\\d+")) {
                                                    System.out.println("your input is invalid. please enter items ID.");
                                                    continue;
                                                }
                                                int id = Integer.parseInt(input);
                                                if (! Admin.itemAvailable(id)) {
                                                    System.out.println("there is no such item with ID " + id + " in list!");
                                                    continue;
                                                }
                                                if (customer.purchaseItem(id)) {
                                                    System.out.println("you purchased item with ID \"" + id + "\" successfully.");
                                                } else {
                                                    System.out.println("unfortunately you don't have enough balance to purchase this item." +
                                                            "\n you can return to Customer Menu by \"back\" command and increase your balance.");
                                                }
                                            }
                                        }
                                        case "5" -> {
                                            while (true) {
                                                System.out.println("please enter your desired tag. you can return to Customer Menu by entering \"back\".");
                                                String tag = myScanner.nextLine().toLowerCase();
                                                if (tag.equals("back")) break;
                                                if (tag.isBlank()) {
                                                    System.out.println("your input is invalid. please try again.");
                                                    continue;
                                                }
                                                while (true) {
                                                    System.out.println(Admin.itemsList(tag));
                                                    System.out.println("now you can buy items by their IDs; just input ID." +
                                                            "\nenter \"back\" to change search tag.");
                                                    String input = myScanner.nextLine().toLowerCase();
                                                    if (input.equals("back")) break;
                                                    if (! input.matches("\\d+")) {
                                                        System.out.println("your input is invalid. please enter a positive integer.");
                                                        continue;
                                                    }
                                                    int id = Integer.parseInt(input);
                                                    if (! Admin.itemAvailable(id)) {
                                                        System.out.println("there is no such item with ID " + id + " in list!");
                                                        continue;
                                                    }
                                                    if (customer.purchaseItem(id)) {
                                                        System.out.println("you purchased item with ID \"" + id + "\" successfully.");
                                                    } else {
                                                        System.out.println("unfortunately you don't have enough balance to purchase this item." +
                                                                "\n you can return to Customer Menu by \"back\" command and increase your balance.");
                                                    }
                                                }
                                            }
                                        }
                                        case "6" -> {
                                        }
                                        default -> System.out.println("your input is invalid. Here is your options:");
                                    }
                                } while (! customerMenu.equals("6"));
                            }
                            
                            case "seller" -> {
                                Seller seller = Admin.getSeller(username, password);
                                if (seller == null) continue;
                                System.out.println("you successfully logged in as a seller. Here is your available commands:");
                                String sellerMenu;
                                do {
                                    System.out.println("""
											Seller Menu:
											\t1. account info
											\t2. add item
											\t3. list of my items
											\t4. log out""");
                                    
                                    sellerMenu = myScanner.nextLine();
                                    switch (sellerMenu) {
                                        case "1" -> {
                                            while (true) {
                                                System.out.println(seller.accountInfo());
                                                System.out.println("Enter a number to modify correspond field; ** fields are unalterable." +
                                                        "\nyou can return to Customer Menu by entering \"back\".");
                                                String fieldNumber = myScanner.nextLine();
                                                if (fieldNumber.equals("back")) break;
                                                String input;
                                                switch (fieldNumber) {
                                                    case "1" -> {
                                                        System.out.println("input new password:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        seller.setPassword(input);
                                                    }
                                                    case "2" -> {
                                                        System.out.println("input new first name:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        seller.setFirstName(input);
                                                    }
                                                    case "3" -> {
                                                        System.out.println("input new last name:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        seller.setLastName(input);
                                                    }
                                                    case "4" -> {
                                                        System.out.println("input new email:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        seller.setEmail(input);
                                                    }
                                                    case "5" -> {
                                                        System.out.println("input new address:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        seller.setAddress(input);
                                                    }
                                                    case "6" -> {
                                                        System.out.println("input new postal code:");
                                                        input = myScanner.nextLine();
                                                        if (input.isBlank()) {
                                                            System.out.println("your input is invalid. please try again.");
                                                            continue;
                                                        }
                                                        seller.setPostalCode(input);
                                                    }
                                                    default -> System.out.println("your input is invalid. please try again.");
                                                }
                                            }
                                        }
                                        case "2" -> {
                                            if (!seller.isVerified()) {
                                                System.out.println("unfortunately you are unverified and can't add a new item.");
                                                break;
                                            }
                                            while (true) {
                                                System.out.println("""
													please input your items using this template:
													[item name] [price] [tags]
													caution: use [] and don't mind spaces. multi tags are valid: [tag1, tag2, tag3, ...]
													you can return to Seller Menu by entering "back".""");
                                                String inputString = myScanner.nextLine().toLowerCase();
                                                if (inputString.equals("back")) break;
                                                String[] splitString = inputString.split("[]\\[]");
                                                if (splitString.length < 5 || ! splitString[3].matches("\\d+")) {
                                                    System.out.println("your input is invalid.");
                                                    continue;
                                                }
                                                seller.addItem(splitString[1], Integer.parseInt(splitString[3]), splitString[5]);
                                            }
                                        }
                                        case "3" -> {
                                            while (true) {
                                                System.out.println(seller.itemsList());
                                                System.out.println("Items can be removed by their IDs; just input ID." +
                                                        "\nyou can return to Seller Menu by entering \"back\"");
                                                String input = myScanner.nextLine().toLowerCase();
                                                if (input.equals("back")) break;
                                                if (! input.matches("\\d+")) {
                                                    System.out.println("your input is invalid.");
                                                    continue;
                                                }
                                                int id = Integer.parseInt(input);
                                                if (seller.removeItem(id)) {
                                                    System.out.println("item with ID " + id + " removed successfully.");
                                                } else {
                                                    System.out.println("there is no such item between your items.");
                                                }
                                            }
                                        }
                                        case "4" -> {
                                        }
                                        default -> System.out.println("your input is invalid. Here is your options:");
                                    }
                                } while (! sellerMenu.equals("4"));
                            }
                            
                            case "admin" -> {
                                if (! password.equals("admin")) {
                                    System.out.println("password doesn't match to username; it's wrong.");
                                    continue;
                                }
                                System.out.println("you successfully logged in as admin. Here is your available commands:");
                                String adminMenu;
                                do {
                                    System.out.println("""
											Admin Menu:
											\t1. unverified sellers
											\t2. all users
											\t3. all items
											\t4. see logs
											\t5. log out""");
                                    
                                    adminMenu = myScanner.nextLine();
                                    switch (adminMenu) {
                                        case "1" -> {
                                            while (true) {
                                                System.out.println(Admin.unverifiedSellers());
                                                System.out.println("you can verify sellers by their usernames; just input them." +
                                                        "\nyou can return to Admin Menu by entering \"back\".");
                                                username = myScanner.nextLine().toLowerCase();
                                                if (username.equals("back")) break;
                                                Admin.verifySeller(username);
                                            }
                                        }
                                        case "2" -> {
                                            while (true) {
                                                System.out.println(Admin.userList());
                                                System.out.println("you can remove users by their usernames; just input them." +
                                                        "\nyou can return to Admin Menu by entering \"back\"");
                                                username = myScanner.nextLine().toLowerCase();
                                                if (username.equals("back")) break;
                                                if (! Admin.userAvailable(username) || username.equals("admin")) {
                                                    System.out.println("there is no such user with username \"" + username + "\" between signed up users.");
                                                }
                                                String userRole = Admin.userRole(username);
                                                Admin.removeUser(username);
                                                System.out.println(userRole + " with username \"" + username + "\" removed successfully.");
                                            }
                                        }
                                        case "3" -> {
                                            while (true) {
                                                System.out.println(Admin.itemsList());
                                                System.out.println("you can remove items by their IDs; just input them." +
                                                        "\nyou can return to Admin Menu by entering \"back\"");
                                                String input = myScanner.nextLine().toLowerCase();
                                                if (input.equals("back")) break;
                                                if (! input.matches("\\d+")) {
                                                    System.out.println("your input is invalid.");
                                                    continue;
                                                }
                                                int id = Integer.parseInt(input);
                                                if (Admin.itemAvailable(id)) {
                                                    System.out.println("item with ID " + id + " removed successfully.");
                                                    Item removedItem = Admin.getItem(id);
                                                    Data.writeLog("<remove item> username: admin" + " - item: " + removedItem.getName() + " - price: " + removedItem.getPrice() + " - tag: " + removedItem.getTag() + " - ID: " + id);
                                                    Admin.removeItem(id);
                                                } else {
                                                    System.out.println("there is no such item in list.");
                                                }
                                            }
                                        }
                                        case "4" -> {
                                            Data.refreshLog();
                                            Data.readLog();
                                            System.out.println("press \"Enter\" to return to Admin Menu.");
                                            String breakPoint = myScanner.nextLine();
                                        }
                                        case "5" -> {
                                        }
                                        default -> System.out.println("your input is invalid. Here is your options:");
                                    }
                                } while (! adminMenu.equals("5"));
                            }
                        }
                        break;
                    }
                }
                case "3" -> {
                    Data.runDatabaseWriter();
                    Admin.writeSellers();
                    Admin.writeCustomers();
                    Admin.writeItems();
                    Data.closeDatabase();
                    Data.closeLog();
                    System.out.println("have a nice day.");
                }
                default -> System.out.println("your input is invalid. Here is your options:");
            }
        } while (! mainMenu.equals("3"));
    }
}