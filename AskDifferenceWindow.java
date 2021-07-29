import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AskDifferenceWindow extends JFrame {
    private String currentSuggest;

    public AskDifferenceWindow(String currentSuggest){
        setCurrentSuggest(currentSuggest);
        getQuestionWindow();
    }

    private void getQuestionWindow(){
        this.setSize(new Dimension(650, 150));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());

        //Text field to input the thing the user thought about and what the difference is
        JLabel thoughtLabel = new JLabel("Which animal did you think about?");
        JTextField thoughtField = new JTextField("", 30);
        JLabel differenceLabel = new JLabel("With which question can you tell the difference between your idea and a/an " + getCurrentSuggest() + "?");
        JTextField differenceField = new JTextField("", 30);

        //button to submit
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            addElement("A:"+thoughtField.getText(), "Q:"+differenceField.getText());
            this.setVisible(false);
        });

        //add elements to frame
        this.add(thoughtLabel);
        this.add(thoughtField);
        this.add(differenceLabel);
        this.add(differenceField);
        this.add(submitButton);
        this.setVisible(true);

    }

    public void addElement(String thoughtItem, String differenceQuestion){
        try {
            Path path = Paths.get("questions.txt");
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(path));
            int indexOfCurrentObject = lines.indexOf("A:"+getCurrentSuggest());
            lines.add(indexOfCurrentObject,differenceQuestion);
            lines.add(indexOfCurrentObject+1, thoughtItem);

            Files.write(path, lines);
        }
        catch (IOException e) {
            // Handle a potential exception
            e.printStackTrace();
        }
    }

    /**
     * Sets new currentSuggest.
     *
     * @param currentSuggest New value of currentSuggest.
     */
    public void setCurrentSuggest(String currentSuggest) {
        this.currentSuggest = currentSuggest;
    }

    /**
     * Gets currentSuggest.
     *
     * @return Value of currentSuggest.
     */
    public String getCurrentSuggest() {
        return currentSuggest;
    }
}
