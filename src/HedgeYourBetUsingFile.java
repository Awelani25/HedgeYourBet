import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class HedgeYourBetUsingFile extends JFrame implements ActionListener
{
    private String[] questions =
    {
        "Where should the transport pick up students staying at Vhugalahawe Res?",
        "Where should the transport pick up students staying at Montana?",
        "Where should the transport pick up students staying at Sandton?",
        "Where should the transport pick up students staying at Beverly?",
        "Where should the transport pick up students staying at West Gate?"
    };

    private String[] options = { "Sandton", "Beverly", "West Gate" };

    private int[] correctIndex = { 2, 1, 0, 1, 2 };

    private int currentQuestion = 0;
    private int score = 0;
    private int previousScore = 0;

    private static final String SCORE_FILE = "score.txt";

    private JLabel questionLabel;
    private JCheckBox[] checkBoxes;
    private JButton submitButton;
    private JLabel resultLabel;
    private JLabel previousScoreLabel;

    public HedgeYourBetUsingFile()
    {
        setTitle("HedgeYourBetUsingFile - Univen Transport Quiz");
        setSize(500, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loadPreviousScore();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));
        previousScoreLabel = new JLabel("Previous score: " + previousScore, SwingConstants.CENTER);
        questionLabel = new JLabel();
        topPanel.add(previousScoreLabel);
        topPanel.add(questionLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1));
        checkBoxes = new JCheckBox[3];
        for (int i = 0; i < 3; i++)
        {
            checkBoxes[i] = new JCheckBox(options[i]);
            centerPanel.add(checkBoxes[i]);
        }
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        resultLabel = new JLabel(" ");
        bottomPanel.add(submitButton);
        bottomPanel.add(resultLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        displayQuestion();

        setVisible(true);
    }

    private void loadPreviousScore()
    {
        File file = new File(SCORE_FILE);

        if (file.exists())
        {
            try (Scanner fileScanner = new Scanner(file))
            {
                if (fileScanner.hasNextInt())
                {
                    previousScore = fileScanner.nextInt();
                }
            }
            catch (IOException e)
            {
                previousScore = 0;
            }
        }
        else
        {
            previousScore = 0;
        }
    }

    private void saveScore()
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORE_FILE)))
        {
            writer.println(score);
        }
        catch (IOException e)
        {
            resultLabel.setText("Error saving score");
        }
    }

    private void displayQuestion()
    {
        questionLabel.setText(questions[currentQuestion]);
        for (int i = 0; i < 3; i++)
        {
            checkBoxes[i].setSelected(false);
        }
        resultLabel.setText(" ");
    }

    public void actionPerformed(ActionEvent e)
    {
        int selectedCount = 0;
        boolean correctSelected = false;

        for (int i = 0; i < 3; i++)
        {
            if (checkBoxes[i].isSelected())
            {
                selectedCount++;
                if (i == correctIndex[currentQuestion])
                {
                    correctSelected = true;
                }
            }
        }

        int pointsEarned = 0;

        if (correctSelected)
        {
            if (selectedCount == 1)
            {
                pointsEarned = 5;
            }
            else if (selectedCount == 2)
            {
                pointsEarned = 2;
            }
            else if (selectedCount == 3)
            {
                pointsEarned = 1;
            }
        }

        score += pointsEarned;

        currentQuestion++;

        if (currentQuestion < questions.length)
        {
            displayQuestion();
        }
        else
        {
            String message;

            if (score > 21)
            {
                message = "Fantastic!";
            }
            else if (score > 15)
            {
                message = "Very good";
            }
            else
            {
                message = "OK";
            }

            saveScore();

            questionLabel.setText("Quiz complete! Your score: " + score + " / 25");
            resultLabel.setText(message);

            for (int i = 0; i < 3; i++)
            {
                checkBoxes[i].setVisible(false);
            }
            submitButton.setVisible(false);
        }
    }

    public static void main(String[] args)
    {
        new HedgeYourBetUsingFile();
    }
}
