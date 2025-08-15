import java.util.Scanner;

/**
 * Handles tracking the user's mood by prompting them for input.
 */
public class MoodTracker {

    /**
     * Asks the user how they are feeling and returns their input.
     * @param sc The Scanner object to read user input.
     * @param user The User object to personalize the greeting.
     * @return A string representing the user's stated mood.
     */
    public String askMood(Scanner sc, User user) {
        System.out.println("\nHi " + user.getName() + ", how are you feeling today?");
        System.out.println("Options: Happy, Sad, Anxious, Angry, Tired, Excited, Okay");
        System.out.print("Your mood: ");
        String mood = sc.nextLine();
        return mood;
    }
}