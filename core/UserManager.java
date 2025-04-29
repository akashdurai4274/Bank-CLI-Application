package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import models.User;

public class UserManager {
    private static final String USER_FILE = "data/users.txt";

    public static void listUsers() {
        try {
            System.out.println("\n-------------Registered Users-------------");
            Path path = Paths.get(USER_FILE);
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                User user = User.fromFileString(line);
                System.out.println("Usernmae: " + user.getUsername() + " | Role: " + user.getRole());
            }
        } catch (Exception e) {
            System.out.println("Unable to read user file");
        }
    }

    public static boolean deleteUser(String username) {
        List<String> updatedUserList = new ArrayList<>();
        boolean found = false;
        Path path = Paths.get(USER_FILE);

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                User user = User.fromFileString(line);
                if (!user.getUsername().equals(username)) {
                    updatedUserList.add(line);
                } else {
                    found = true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error Reading Users");
            return false;
        }
        if (!found)
            return false;
        try {
            Files.write(path, updatedUserList, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error writing users.");
            return false;
        }
        /* AccountManager.deleteAccount(username);
        AccountManager.deleteUserTransaction(username); */
        return true;
    }
}
