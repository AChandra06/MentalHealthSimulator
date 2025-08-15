import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

/**
 * The main GUI for the Mental Health Check-in Simulator.
 * Provides a user-friendly interface instead of a console menu.
 */
public class MentalHealthGUI extends JFrame {

    private Assistant assistant;
    private HistoryManager historyManager;
    private User currentUser;

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextArea outputArea;
    private JTextField inputField;

    public MentalHealthGUI() {
        // --- Frame Setup ---
        setTitle("Mental Health Check-in Assistant");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        historyManager = new HistoryManager();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- Create Panels ---
        createLoginPanel();
        createMenuPanel();

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
    }

    private void createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Hi, what is your name?");
        JTextField nameField = new JTextField(20);
        JButton loginButton = new JButton("Start");

        // Action listener for the login button
        loginButton.addActionListener(e -> {
            String userName = nameField.getText().trim();
            if (!userName.isEmpty()) {
                currentUser = historyManager.loadUser(userName);
                assistant = new Assistant(currentUser);
                cardLayout.show(mainPanel, "menu");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter your name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = 0;
        loginPanel.add(nameLabel, gbc);
        gbc.gridy = 1;
        loginPanel.add(nameField, gbc);
        gbc.gridy = 2;
        loginPanel.add(loginButton, gbc);

        mainPanel.add(loginPanel, "login");
    }

    private void createMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout(10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Output Area ---
        outputArea = new JTextArea("Welcome! What would you like to do?");
        outputArea.setEditable(false);
        outputArea.setWrapStyleWord(true);
        outputArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton startSessionButton = new JButton("Start a Session");
        JButton showHistoryButton = new JButton("Show Mood History");
        JButton exitButton = new JButton("Exit");
        buttonPanel.add(startSessionButton);
        buttonPanel.add(showHistoryButton);
        buttonPanel.add(exitButton);

        // --- Action Listeners ---
        startSessionButton.addActionListener(e -> startNewSession());
        showHistoryButton.addActionListener(e -> outputArea.setText(assistant.getFormattedMoodHistory()));
        exitButton.addActionListener(e -> {
            historyManager.saveUser(currentUser); // Final save on exit
            System.exit(0);
        });

        menuPanel.add(scrollPane, BorderLayout.CENTER);
        menuPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(menuPanel, "menu");
    }

    private void startNewSession() {
        String mood = JOptionPane.showInputDialog(this,
            "How are you feeling today?\n(e.g., Happy, Sad, Anxious, Tired)",
            "Mood Check-in",
            JOptionPane.QUESTION_MESSAGE);

        if (mood != null && !mood.trim().isEmpty()) {
            // Create a fake scanner input for the existing assistant method
            Scanner fakeScanner = new Scanner(mood);
            String sessionResult = assistant.startSession(fakeScanner);
            
            // Display results
            outputArea.setText(sessionResult);

            // Ask if user wants to do the breathing exercise
            int choice = JOptionPane.showConfirmDialog(this,
                "The AI suggested an exercise. Would you like to do a guided breathing exercise now?",
                "Exercise Suggestion",
                JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                // Run exercise in a new thread to avoid freezing the GUI
                new Thread(() -> assistant.getCalmActivity().breathingExercise()).start();
                outputArea.append("\nGuided breathing exercise started... Check your console for instructions.");
            }
        }
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new MentalHealthGUI().setVisible(true);
        });
    }
}
