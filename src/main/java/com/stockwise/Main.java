import dao.DatabaseConnection;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        // Initialize the database tables before starting the app
        DatabaseConnection.initializeDatabase();

        // Launch the console user interface
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.start();
    }
}