// Assistant.java
import java.util.Scanner;

public class Assistant {
    private User user;
    private CalmActivity ca;
    private QuoteProvider qp;
    private MoodTracker mt;
    private MoodEvaluator evaluator;
    private HistoryManager historyManager;

    public Assistant(User user) {
        this.user = user;
        this.ca = new CalmActivity();
        this.qp = new QuoteProvider();
        this.mt = new MoodTracker();
        this.evaluator = new MoodEvaluator();
        this.historyManager = new HistoryManager();
    }

    public String startSession(Scanner sc) {
        StringBuilder sessionLog = new StringBuilder();
        String mood = mt.askMood(sc, user);
        user.addMoodHistory(mood);
        historyManager.saveUser(user);
        sessionLog.append("Mood logged: ").append(mood).append("\n\n");
        String quote = qp.getRandomQuote();
        sessionLog.append("Here's a quote for you: \"").append(quote).append("\"\n\n");
        String evaluation = evaluator.getEvaluationAndSuggestion(mood);
        sessionLog.append(evaluation).append("\n\n");
        return sessionLog.toString();
    }

    public String getFormattedMoodHistory() {
        StringBuilder history = new StringBuilder("--- Your Mood History ---\n");
        if (user.getMoodHistory().isEmpty()) {
            history.append("No moods have been logged in the last week.\n");
        } else {
            for (String mood : user.getMoodHistory()) {
                history.append(" - ").append(mood).append("\n");
            }
        }
        history.append("------------------------");
        return history.toString();
    }
    
    public CalmActivity getCalmActivity() {
        return ca;
    }
}