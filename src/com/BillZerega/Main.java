package com.BillZerega;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main {

    private JTextArea question;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private JFrame frame;


    public static void main(String[] args) {
	Main builder = new Main();
	builder.go();
    }


    //QuizCard Class had to download from books website, looks like constructor with getters and setters
    public class QuizCard implements Serializable {

        private String uniqueID;
        private String category;
        private String question;
        private String answer;
        private String hint;

        public QuizCard(String q, String a) {
            question = q;
            answer = a;
        }


        public void setUniqueID(String id) {
            uniqueID = id;
        }

        public String getUniqueID() {
            return uniqueID;
        }

        public void setCategory(String c) {
            category = c;
        }

        public String getCategory() {
            return category;
        }

        public void setQuestion(String q) {
            question = q;
        }

        public String getQuestion() {
            return question;
        }

        public void setAnswer(String a) {
            answer = a;
        }

        public String getAnswer() {
            return answer;
        }

        public void setHint(String h) {
            hint = h;
        }

        public String getHint() {
            return hint;
        }

    }


    public void go(){
        //build gui

        frame = new JFrame("Quiz Card Builder");
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);
        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);


        //Set up a vertical scrollbar and no horizontal scroll bar
        JScrollPane qScroller = new JScrollPane(question);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigFont);

        JScrollPane aScroller = new JScrollPane(answer);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Next Card");

        cardList = new ArrayList<QuizCard>();

        JLabel qLabel = new JLabel("Question:");
        JLabel aLabel = new JLabel("Answer:");

        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        newMenuItem.addActionListener(new NewMenuListener());

        saveMenuItem.addActionListener(new SaveMenuListener());
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500,600);
        frame.setVisible(true);
    }//close go method

    public class NextCardListener implements ActionListener {
        public void actionPerformed(ActionEvent ev){

            QuizCard card = new QuizCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        }
    }

    public class SaveMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent ev){
            QuizCard card = new QuizCard(question.getText(), answer.getText());
            cardList.add(card);


            //file dialog box waits for user to save, all navigation handled by JFileChooser
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }

    public class NewMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            cardList.clear();
            clearCard();
        }
    }

    private void clearCard(){
        question.setText("");
        answer.setText("");
        question.requestFocus();

    }

    //Method does the actual file writing
    private void saveFile(File file){
        try{

            //bufferedWriter chained to fileWriter to make writing more efficient
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for(QuizCard card:cardList){
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");

            }
            writer.close();
        } catch(IOException ex){
            System.out.println("couldn't write the cardList out");
            ex.printStackTrace();
        }
    }

}
