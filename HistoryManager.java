import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages loading and saving user mood history to the file system.
 */
public class HistoryManager {
    private static final String HISTORY_DIR = "userdata";
    private final ObjectMapper objectMapper;

    public HistoryManager() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Create the directory if it doesn't exist
        new File(HISTORY_DIR).mkdirs();
    }

    /**
     * Loads a user's mood history from a file.
     * Filters out entries older than 7 days.
     * @param userName The name of the user to load.
     * @return A User object with their recent history.
     */
    public User loadUser(String userName) {
        File userFile = Paths.get(HISTORY_DIR, userName + ".json").toFile();
        User user = new User(userName);

        if (userFile.exists()) {
            try {
                // Read the list of mood entries from the JSON file
                List<Map<String, String>> history = objectMapper.readValue(userFile, new TypeReference<>() {});
                Instant oneWeekAgo = Instant.now().minus(7, ChronoUnit.DAYS);

                // Filter for moods within the last week and add them to the user object
                for (Map<String, String> entry : history) {
                    Instant timestamp = Instant.parse(entry.get("timestamp"));
                    if (timestamp.isAfter(oneWeekAgo)) {
                        user.addMoodHistory(entry.get("mood"));
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading user history for " + userName);
                e.printStackTrace();
            }
        }
        return user;
    }

    /**
     * Saves the user's current mood history to a file.
     * Each mood is saved with a current timestamp.
     * @param user The User object to save.
     */
    public void saveUser(User user) {
        File userFile = Paths.get(HISTORY_DIR, user.getName() + ".json").toFile();
        List<Map<String, String>> historyToSave = new ArrayList<>();

        // Create a list of map entries, each containing a mood and a timestamp
        for (String mood : user.getMoodHistory()) {
            Map<String, String> entry = new HashMap<>();
            entry.put("timestamp", Instant.now().toString());
            entry.put("mood", mood);
            historyToSave.add(entry);
        }

        try {
            objectMapper.writeValue(userFile, historyToSave);
        } catch (IOException e) {
            System.err.println("Error saving user history for " + user.getName());
            e.printStackTrace();
        }
    }
}