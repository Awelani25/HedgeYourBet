import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HedgeYourBet extends JFrame implements ActionListener
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

    private JLabel questionLabel;
    private JCheckBox[] checkBoxes;
    private JButton submitButton;
    private JLabel resultLabel;

    public HedgeYourBet()
    {
        setTitle("HedgeYourBet - Univen Transport Quiz");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        questionLabel = new JLabel();
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
        new HedgeYourBet();
    }
}
