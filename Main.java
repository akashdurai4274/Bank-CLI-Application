
import core.AuthManager;
import java.util.Scanner;
import models.User;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static User LoggedInUser = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n CLI Bank Menus");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    public static void login() {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        User user = AuthManager.login(username, password);
        if (user != null) {
            LoggedInUser = user;
            System.out.println("LoggedIn Successfully...");
        } else {
            System.out.println("Invalid Credentials...");
        }
    }

    public static void register() {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        if (AuthManager.register(username, password, "CUSTOMER")) {
            System.out.println("Registration Successfull...");
        } else {
            System.out.println("Username already Exist...");
        }
    }

    public static void showUserMenu() {
        System.out.println("\n LoggedIn as " + LoggedInUser.getUsername() + "[" + LoggedInUser.getRole() + "]");
        System.out.println("1. Logout");
        System.out.println("> ");
        String input = scanner.nextLine();

        if ("1".equals(input)) {
            LoggedInUser = null;
        }
    }
}