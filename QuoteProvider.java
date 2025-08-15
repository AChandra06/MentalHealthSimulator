
import java.util.Random;

/**
 * Provides random inspirational quotes.
 * This class uses a predefined list of quotes.
 */
public class QuoteProvider {

    private static final String[] quotes = {
        "You are stronger than you think.",
        "Take it one step at a time.",
        "This too shall pass.",
        "Breathe. You're doing okay.",
        "It's okay to ask for help.",
        "You matter. Your feelings are valid.",
        "Progress, not perfection."
    };

    /**
     * Gets a random quote from the predefined list.
     * @return A random inspirational quote as a String.
     */
    public String getRandomQuote() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(quotes.length);
        return quotes[randomIndex];
    }
}