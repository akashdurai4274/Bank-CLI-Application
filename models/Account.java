package models;

public class Account {
    private static String accountNumber;
    private static String username;
    private static double balance;

    public Account(String accountNumber, String username, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.username = username;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

    public static Account fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3)
            return null;
        return new Account(parts[0], parts[1], Double.parseDouble(parts[2]));
    }

    public static String toFileString() {
        return accountNumber + "," + username + "," + balance;
    }
}
