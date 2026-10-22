package ui;

import dao.ProductDao;
import dao.UserDao;
import model.Product;
import model.User;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserDao userDao = new UserDao();
    private static final ProductDao productDao = new ProductDao();

    public void start() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   🏪 StockWise Inventory System 🏪     ");
            System.out.println("========================================");
            System.out.println("[1] Login");
            System.out.println("[2] Register Account");
            System.out.println("[3] Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleLogin();
                    break;
                case "2":
                    handleRegistration();
                    break;
                case "3":
                    System.out.println("👋 Thank you for using StockWise. Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    private void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userDao.login(username, password);
        if (user != null) {
            if ("PENDING".equals(user.getAccountStatus())) {
                System.out.println("⚠️ Your account is still PENDING approval by an Administrator.");
                return;
            }
            System.out.println("✅ Welcome back, " + user.getFullName() + " (" + user.getRole() + ")!");
            showDashboard(user);
        } else {
            System.out.println("❌ Invalid username or password.");
        }
    }

    private void handleRegistration() {
        System.out.print("Enter desired username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.println("Select Role:");
        System.out.println("1. System Administrator");
        System.out.println("2. Inventory Staff");
        System.out.println("3. Store Manager");
        System.out.print("Choice (1-3): ");
        String roleChoice = scanner.nextLine();

        String role = "Inventory Staff";
        if (roleChoice.equals("1")) role = "System Administrator";
        else if (roleChoice.equals("3")) role = "Store Manager";

        boolean success = userDao.registerUser(username, password, fullName, role);
        if (success) {
            System.out.println("🎉 Registration successful! Status is set to PENDING.");
        }
    }

    private void showDashboard(User user) {
        while (true) {
            System.out.println("\n--- Dashboard [" + user.getRole() + "] ---");
            System.out.println("[1] View All Products");
            System.out.println("[2] Add New Product");
            System.out.println("[3] Logout");
            System.out.print("Select action: ");
            String action = scanner.nextLine();

            if (action.equals("1")) {
                List<Product> products = productDao.getAllProducts();
                System.out.println("\n--- Product List ---");
                for (Product p : products) {
                    System.out.println("ID: " + p.getProductId() + " | Name: " + p.getProductName() +
                            " | Category: " + p.getCategory() + " | Price: $" + p.getPrice() +
                            " | Qty: " + p.getQuantity() + " | Reorder: " + p.getReorderLevel());
                }
            } else if (action.equals("2")) {
                System.out.print("Enter product name: ");
                String name = scanner.nextLine();
                System.out.print("Enter category: ");
                String category = scanner.nextLine();
                System.out.print("Enter price: ");
                double price = Double.parseDouble(scanner.nextLine());
                System.out.print("Enter initial quantity: ");
                int qty = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter reorder level: ");
                int reorder = Integer.parseInt(scanner.nextLine());

                boolean added = productDao.addProduct(name, category, price, qty, reorder);
                if (added) {
                    System.out.println("✅ Product added successfully!");
                }
            } else if (action.equals("3")) {
                System.out.println("🔒 Logged out successfully.");
                break;
            } else {
                System.out.println("❌ Invalid option.");
            }
        }
    }
}