package core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class TransactionManager {
    private static final String TRANSACTION_FILE = "data/transactions.txt";

    public static void log(String username, String type, double amount) {
        String entry = LocalDateTime.now() + "," + username + "," + type + "," + amount;
        Path path = Paths.get(TRANSACTION_FILE);
        try {
            Files.write(path, Collections.singleton(entry),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Failed to log Transaction");
        }
    }

    public static void ViewTransactionDetails(String username) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(TRANSACTION_FILE));
            boolean found = false;
            for (String line : lines) {
                if (line.contains("," + username + ",")) {
                    System.out.println(line);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No Transaction happened For this Account!");
            }
        } catch (IOException e) {
            System.out.println("Unable to Read Transactions");
        }

    }
}
