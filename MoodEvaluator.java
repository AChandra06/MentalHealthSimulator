// MoodEvaluator.java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;

public class MoodEvaluator {
    // 🔑 IMPORTANT: Replace this placeholder with your actual OpenAI API key
    private static final String OPENAI_API_KEY = "Put_API_here";
    private static final String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private final HttpClient client = HttpClient.newHttpClient();

    public String getEvaluationAndSuggestion(String mood) {
        if (OPENAI_API_KEY.equals("Put_API_here")) {
            return "Evaluation: API key not set. Please configure MoodEvaluator.java.\nSuggested Exercise: Perform a simple breathing exercise.";
        }

        try {
            String prompt = String.format(
                "A user says they are feeling '%s'. Based on this mood, provide a brief, supportive evaluation (1-2 sentences) " +
                "and then suggest one specific, simple wellness exercise. " +
                "Format the response exactly like this: 'Evaluation: [Your evaluation here] Suggested Exercise: [Your suggestion here]'",
                mood
            );

            String requestBody = new JSONObject()
                .put("model", "gpt-4o")
                .put("messages", new JSONArray()
                    .put(new JSONObject().put("role", "system").put("content", "You are a caring mental wellness assistant."))
                    .put(new JSONObject().put("role", "user").put("content", prompt)))
                .put("max_tokens", 100)
                .put("temperature", 0.7)
                .toString();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + OPENAI_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            String content = jsonResponse.getJSONArray("choices")
                                       .getJSONObject(0)
                                       .getJSONObject("message")
                                       .getString("content");
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "Evaluation: Could not get a suggestion at this time.\nSuggested Exercise: Take a moment to stretch your arms and legs.";
        }
    }
}