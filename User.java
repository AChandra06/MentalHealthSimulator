import java.util.ArrayList;

/**
 * Models a user of the Mental Health Assistant.
 * Stores user's name and a history of their recorded moods.
 */
public class User {
    private String name;
    private ArrayList<String> moodHistory;

    /**
     * Constructor to create a new User.
     * @param name The name of the user.
     */
    public User(String name) {
        this.name = name;
        this.moodHistory = new ArrayList<>();
    }

    /**
     * Gets the user's name.
     * @return The name of the user.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the user's mood history.
     * @return An ArrayList of strings representing the mood history.
     */
    public ArrayList<String> getMoodHistory() {
        return this.moodHistory;
    }

    /**
     * Adds a new mood entry to the user's history.
     * @param currentMood The mood to add to the history.
     */
    public void addMoodHistory(String currentMood) {
        this.moodHistory.add(currentMood);
    }
}
