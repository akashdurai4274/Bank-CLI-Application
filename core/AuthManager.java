package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import models.User;
import utils.FileUtils;

public class AuthManager {
    public static final String USERS_FILE = "data/users.txt";

    public static User login(String username, String password) {
        String hash = FileUtils.hashPassword(password);
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromFileString(line);
                if (user != null && user.getUsername().equals(username) && user.getpasswordHash().equals(hash)) {
                    return user;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file...");
        }
        return null;
    }

    public static boolean register(String username, String password, String role) {
        if (userExists(username))
            return false;
        String hash = FileUtils.hashPassword(password);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + "," + hash + "," + role);
            writer.newLine();
            return true;
        } catch (Exception e) {
            System.out.println("Error writing to users file");
            return false;
        }
    }

    public static boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader((USERS_FILE)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromFileString(line);
                if (user != null && user.getUsername().equals(username)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
