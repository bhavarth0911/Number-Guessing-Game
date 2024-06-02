import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumberGUI extends JFrame {
    private static final int MAX_ATTEMPTS = 3;
    private static final int MAX_ROUNDS = 4;

    private int numberToGuess;
    private int attempts;
    private int round;
    private int totalScore;

    private JLabel instructionLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JTextArea resultArea;

    public GuessTheNumberGUI() {
        setTitle("Guess the Number Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        instructionLabel = new JLabel("Enter a number between 1 and 100:");
        guessField = new JTextField(10);
        guessButton = new JButton("Submit");
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(instructionLabel);
        inputPanel.add(guessField);
        inputPanel.add(guessButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        guessButton.addActionListener(new GuessButtonListener());

        initializeGame();
    }

    private void initializeGame() {
        round = 1;
        totalScore = 0;
        startNewRound();
    }

    private void startNewRound() {
        if (round <= MAX_ROUNDS) {
            numberToGuess = new Random().nextInt(100) + 1;
            attempts = 0;
            resultArea.append("Round " + round + " of " + MAX_ROUNDS + "\n");
            resultArea.append("I have selected a number between 1 and 100. Can you guess it?\n");

            // Reset input field and button
            guessField.setText("");
            guessField.setEnabled(true);
            guessButton.setEnabled(true);
        } else {
            resultArea.append("Game over! Your total score is: " + totalScore + "\n");
            guessButton.setEnabled(false);
            guessField.setEnabled(false);
        }
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int userGuess = Integer.parseInt(guessField.getText());
                attempts++;

                if (userGuess == numberToGuess) {
                    int pointsEarned = MAX_ATTEMPTS - attempts + 1;
                    totalScore += pointsEarned;
                    resultArea.append("Congratulations! You guessed the number in " + attempts + " attempts. You earned " + pointsEarned + " points.\n");
                    round++;
                    startNewRound();
                } else if (userGuess < numberToGuess) {
                    resultArea.append("Your guess is too low.\n");
                } else {
                    resultArea.append("Your guess is too high.\n");
                }

                if (attempts >= MAX_ATTEMPTS) {
                    resultArea.append("Sorry, you've used all attempts. The number was " + numberToGuess + "\n");
                    round++;
                    startNewRound();
                }

                resultArea.append("Your score after round " + (round - 1) + " is: " + totalScore + "\n");

            } catch (NumberFormatException ex) {
                resultArea.append("Please enter a valid number.\n");
            } finally {
                guessField.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuessTheNumberGUI game = new GuessTheNumberGUI();
            game.setVisible(true);
        });
    }
}
