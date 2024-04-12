import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class WindowFrame extends JFrame implements ActionListener {

    public JButton startButton = new JButton("Play");
    public JButton customiseButton = new JButton("Customise");
    public JButton historyButton = new JButton("History");
    public JButton exitButton = new JButton("Exit");
    public BufferedImage backgroundImage;
    public Horse horse1;
    public Horse horse2;
    public Horse horse3;
    char codePointChar;
    String symbol;
    String horseName;
    double confidenceRating;
    private Horse[] customHorses;


    public WindowFrame(String programTitle)  {
        // sets program to be visible, 1280x720, halts on close
        this.setVisible(true);
        this.setLayout(null);
        this.setSize(1280,720);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(programTitle);


        JPanel textPanel = new JPanel();

        JPanel authorPanel = new JPanel();

        this.add(textPanel);
        textPanel.setOpaque(false);
        this.add(authorPanel);
        authorPanel.setOpaque(false);


        authorPanel.setBounds(0,300,this.getWidth(), 25);

        // authorPanel.setBackground(Color.CYAN);

        textPanel.setBounds(0,250,this.getWidth(),50);

        // textPanel.setBackground(Color.GREEN);


        JLabel title = new JLabel(programTitle);
        title.setFont(new Font("Helvetica", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        JLabel author = new JLabel("Author: Joshua Cameron Ng - SID: 230309485 ");
        author.setForeground(Color.LIGHT_GRAY);
        textPanel.add(title);
        authorPanel.add(author);
        JPanel buttonPanel = new JPanel();
        this.add(buttonPanel);
        buttonPanel.setOpaque(false);

        buttonPanel.setBounds(0,350,this.getWidth(),150);

        //buttonPanel.setBackground(Color.RED);
        buttonPanel.add(startButton);
        buttonPanel.add(customiseButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(exitButton);

        startButton.setFocusable(false);
        customiseButton.setFocusable(false);
        historyButton.setFocusable(false);
        exitButton.setFocusable(false);
        // action listener
        startButton.addActionListener(this);
        customiseButton.addActionListener(this);
        historyButton.addActionListener(this);
        exitButton.addActionListener(this);


        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/background.jpeg")));
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            System.out.println("Unable to load background image from source :( ");
        }



        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0,0, getWidth(), getHeight(), null);
            }
        };
        backgroundPanel.setBounds(0,0,this.getWidth(),this.getHeight());
        this.add(backgroundPanel);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // exit button closes
        if (e.getSource() == exitButton) {
            System.exit(0);
        }
        if (e.getSource() == startButton) {
            this.dispose();
            if (customHorses != null) {
                Race r = new Race(15, customHorses.length);
                for (int i = 0; i<customHorses.length; i++) {
                    r.addHorse(customHorses[i], (i+1));
                }
                r.startRace();
            }
            else {
                horse1 = new Horse(codePointChar, "Zappy-Horse", 0.46);
                horse2 = new Horse(codePointChar,"Data-Horse", 0.5);
                horse3 = new Horse(codePointChar,"Server-Horse", 0.5);

                horse1.setSymbol('♕');
                horse2.setSymbol('♞');
                horse3.setSymbol('♘');

                Race r = new Race(15, 3);
                r.addHorse(horse1, 1);
                r.addHorse(horse2, 2);
                r.addHorse(horse3, 3);
                r.startRace();
            }
        }
        else if (e.getSource() == customiseButton) {
            System.out.println("clicked");

            int tracks = Integer.parseInt(JOptionPane.showInputDialog("Enter how many tracks you want: "));
            customHorses = new Horse[tracks];
            for (int i = 0; i<customHorses.length; i++) {
                horseName = JOptionPane.showInputDialog("Enter name of horse");
                confidenceRating = Double.parseDouble(JOptionPane.showInputDialog("Enter " + horseName + "'s confidence rating"));
                symbol = JOptionPane.showInputDialog("What should " + horseName + " be represented by?");
                codePointChar = symbol.charAt(0);
                customHorses[i] = new Horse(codePointChar, horseName, confidenceRating);

            }
        }
    }
}
