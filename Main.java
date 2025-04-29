
import core.AccountManager;
import core.AuthManager;
import core.TransactionManager;
import java.util.Scanner;
import models.User;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static User LoggedInUser = null;

    public static void main(String[] args) {
        System.out.println(LoggedInUser);
        while (true) {
            if (LoggedInUser != null)
                showUserMenu();
            else {

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
    }

    public static void login() {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        User user = AuthManager.login(username, password);
        if (user != null) {
            LoggedInUser = user;
            System.out.println("******************************");
            System.out.println("LoggedIn Successfully...");
            System.out.println("******************************");
            showUserMenu();
        } else {
            System.out.println("******************************");
            System.out.println("Invalid Credentials...");
            System.out.println("******************************");

        }
    }

    public static void register() {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        if (AuthManager.register(username, password, "CUSTOMER")) {
            System.out.println("******************************");
            System.out.println("Registration Successfull...");
            System.out.println("******************************");

        } else {
            System.out.println("******************************");
            System.out.println("Username already Exist...");
            System.out.println("******************************");

        }
    }

    public static void showUserMenu() {
        System.out.println("\n LoggedIn as " + LoggedInUser.getUsername() + "[" + LoggedInUser.getRole() + "]");
        System.out.println("-------------------------------------------");
        if ("CUSTOMER".equalsIgnoreCase(LoggedInUser.getRole())) {
            System.out.println("1. View balance");
            System.out.println("2. Create Account(For any user)");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. View Transaction");
            System.out.println("7. Logout");
            System.out.println("> ");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    AccountManager.showBalance(LoggedInUser.getUsername());
                    break;
                case "2":
                    AccountManager.createAccount(LoggedInUser.getUsername());
                    break;
                case "3":
                    System.out.println("Amount to Deposit: ");
                    double dAmt = Double.parseDouble(scanner.nextLine());
                    System.out.println(dAmt);
                    if (AccountManager.deposit(LoggedInUser.getUsername(), dAmt)) {
                        System.out.println("Deposit Successfull");
                        System.out.println("Balance: ");
                        AccountManager.showBalance(LoggedInUser.getUsername());
                    }
                    break;
                case "4":
                    System.out.println("Amount to withdraw: ");
                    double wAmt = Double.parseDouble(scanner.nextLine());
                    if (AccountManager.withdraw(LoggedInUser.getUsername(), wAmt)) {
                        System.out.println("Withdraw Sucessfull");
                    } else {
                        System.out.println("Insufficient balance or error");
                    }
                    break;
                case "5":
                    System.out.println("Reciptant User Name: ");
                    String reciever = scanner.nextLine();
                    System.out.println("Amount to Transfer: ");
                    double Amt = Double.parseDouble(scanner.nextLine());
                    if (AccountManager.transfer(LoggedInUser.getUsername(), reciever, Amt)) {
                        System.out.println("Transfer Successful");
                    } else {
                        System.out.println("Transfer Fails");
                    }
                    break;
                case "6":
                    TransactionManager.ViewTransactionDetails(LoggedInUser.getUsername());
                    break;
                case "7":
                    LoggedInUser = null;
                    break;
                default:
                    System.out.println("Invalid Choices");
            }
        }
    }
}