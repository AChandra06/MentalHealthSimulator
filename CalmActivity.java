/**
 * Provides calming activities to the user.
 * Currently includes a guided breathing exercise.
 */
public class CalmActivity {

    /**
     * Guides the user through a simple breathing exercise with timed pauses.
     */
    public void breathingExercise() {
        System.out.println("\nWe will start with a breathing exercise.");
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("\nBreathe in...");
                Thread.sleep(3000); // Pause for 3 seconds

                System.out.println("Hold...");
                Thread.sleep(2000); // Pause for 2 seconds

                System.out.println("Breathe out...");
                Thread.sleep(3000); // Pause for 3 seconds
            }
            System.out.println("\nI hope it is making you feel better.");
        } catch (InterruptedException e) {
            System.out.println("Exercise interrupted.");
            e.printStackTrace();
        }
    }
}
