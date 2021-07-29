import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
public class Game {

    private Tree tree;
    private TreeNode currentNode;
    private JButton yesButton, noButton, restartButton;
    private JTextPane textPane; // for displaying the question
    private boolean started = false;
    private String currentSuggestion = "";
    // btns listener
    private ActionListener btnsListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == yesButton)
                yes();
            else if (source == noButton) {
                no();
            }
            else if (source == restartButton)
                restart();

        }
    };

    public static void main(String[] args) {
        Game game = new Game();
        game.tree = new Tree();
        game.tree.loadTree("questions.txt");

        SwingUtilities.invokeLater(game::buildUI);
    }

    private void buildUI() {
        //build the UI
        JFrame frame = new JFrame("Knowledge tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        //add buttons
        JPanel buttonsPanel = new JPanel();

        yesButton = new JButton("Yes");
        yesButton.addActionListener(btnsListener);
        buttonsPanel.add(yesButton, BorderLayout.LINE_START);

        noButton = new JButton("No");
        noButton.addActionListener(btnsListener);
        buttonsPanel.add(noButton, BorderLayout.LINE_START);

        restartButton = new JButton("Start");
        restartButton.addActionListener(btnsListener);
        buttonsPanel.add(restartButton, BorderLayout.LINE_END);

        contentPane.add(buttonsPanel, BorderLayout.PAGE_END);

        //add text area
        textPane = new JTextPane();
        textPane.setEditable(false);
        updateText("Think of an animal and press start");

        //define some style for the text pane
        SimpleAttributeSet bSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(bSet, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(bSet, 22);
        StyledDocument doc = textPane.getStyledDocument();
        doc.setParagraphAttributes(0,  doc.getLength(), bSet, false);

        contentPane.add(textPane, BorderLayout.CENTER);

        frame.setMinimumSize(new Dimension(500, 250));

        //center the JFrame
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int coordX = (objDimension.width - frame.getWidth()) / 2;
        int coordY = (objDimension.height - frame.getHeight()) / 2;
        frame.setLocation(coordX, coordY);

        //display the window
        frame.pack();
        frame.setVisible(true);
    }

    //code for callbacks methods yes, no and restart
    private void yes() {
        //navigate in the tree by moving the current node to yes
        if (started && currentNode != null) {
            currentNode = currentNode.yes;

            if (currentNode != null) {
                if (currentNode.isQuestion()) {
                    updateText(currentNode.data);
                } else {
                    currentSuggestion = currentNode.data;
                    updateText("Did you think about a/an " + currentNode.data + "?");
                }
            } else {
                updateText("Found it :)");
            }
        }
    }

    private void no(){
        //navigate in the tree by moving the current node to no
        if (started && currentNode != null) {
            currentNode = currentNode.no;

            if (currentNode != null) {
                if (currentNode.isQuestion()) {
                    updateText(currentNode.data);
                } else {
                    currentSuggestion = currentNode.data;
                    updateText("Did you think about a/an " + currentNode.data + "?");
                }
            } else {
                updateText("I can not think about it");
                AskDifferenceWindow askDifferenceWindow = new AskDifferenceWindow(currentSuggestion);

            }
        }
    }

    private void restart() {
        if (started) {
            started = false;
            updateText("Think about an animal and press start");
        } else {
            started = true;
            updateText("Think about an animal and press start");
            currentNode = tree.root;
            updateText(currentNode.data);
        }
    }

    private void updateText(String txt) {
        textPane.setText("\n" + txt);
    }

}