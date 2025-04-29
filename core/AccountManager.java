package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import models.Account;

public class AccountManager {
    private static final String ACCOUNTS_FILE = "data/accounts.txt";

    public static Account getAccountByUserName(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Account acc = Account.fromFileString(line);
                if (acc != null && acc.getUsername().equals(username)) {
                    return acc;
                }
            }
        } catch (Exception e) {
            System.out.println("Error Reading Account File");
        }
        return null;
    }

    public static void createAccount(String username) {
        if (getAccountByUserName(username) != null) {
            System.out.println("******************************");
            System.out.println("Account Already Exist....");
            System.out.println("******************************");

            return;
        }

        String accountNumber = UUID.randomUUID().toString().substring(0, 8);
        Account acc = new Account(accountNumber, username, 0.0);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE, true))) {
            writer.write(acc.getAccountNumber() + "," + acc.getUsername() + "," + acc.getBalance());
            writer.newLine();
            System.out.println("******************************");
            System.out.println("Account Created: " + accountNumber);
            System.out.println("******************************");

        } catch (IOException e) {
            System.out.println("Error creating account");
        }
    }

    public static void showBalance(String username) {
        Account acc = getAccountByUserName(username);
        if (acc != null) {
            System.out.println("Account " + acc.getAccountNumber());
            System.out.println("balance: Rupees " + acc.getBalance());
        } else {
            System.out.println("******************************");
            System.out.println("No Account Found");
            System.out.println("******************************");
        }
    }

    public static boolean updateAccount(Account updatedAccount) {
        List<Account> accounts = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(ACCOUNTS_FILE));
            for (String line : lines) {
                Account acc = Account.fromFileString(line);
                if (acc == null)
                    continue;
                if (acc.getUsername().equals(updatedAccount.getUsername())) {
                    accounts.add(updatedAccount);
                } else {
                    accounts.add(acc);
                }
            }
        } catch (IOException e) {
            System.out.println("Error When Reading Accounts File");
            return false;
        }
        System.out.println(accounts);

        try {
            List<String> updatedLines = new ArrayList<>();
            for (Account acc : accounts) {
                updatedLines.add(acc.toFileString());
            }
            Files.write(Paths.get(ACCOUNTS_FILE), updatedLines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error updating accounts file");
            return false;
        }
        return true;
    }

    public static boolean deposit(String username, double amount) {
        Account acc = getAccountByUserName(username);
        if (acc == null)
            return false;

        acc.deposit(amount);
        boolean updated = updateAccount(acc);
        if (updated)
            TransactionManager.log(username, "DEPOSIT", amount);
        return updated;
    }

    public static boolean withdraw(String username, double amount) {
        Account acc = getAccountByUserName(username);
        if (acc == null || acc.getBalance() < amount)
            return false;

        acc.withdraw(amount);
        boolean updated = updateAccount(acc);
        if (updated)
            TransactionManager.log(username, "WITHDRAW", amount);
        return updated;
    }

    public static boolean transfer(String fromUser, String toUser, double amount) {
        Account senderAccount = getAccountByUserName(fromUser);
        Account recieverAccount = getAccountByUserName(toUser);
        if (senderAccount == null || recieverAccount == null || senderAccount.getBalance() < amount)
            return false;
        senderAccount.withdraw(amount);
        recieverAccount.deposit(amount);
        boolean updatedSender = updateAccount(senderAccount);
        boolean updatedReciever = updateAccount(recieverAccount);
        if (updatedSender && updatedReciever) {
            TransactionManager.log(fromUser, "Transfered_To, " + toUser, amount);
            TransactionManager.log(toUser, "Recieved_From, " + fromUser, amount);
            return true;
        }
        return false;
    }
}
