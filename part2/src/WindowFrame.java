import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class WindowFrame extends JFrame implements ActionListener {

    public JButton startButton = new JButton("Play");
    public JButton customiseButton = new JButton("Customise");
    public JButton historyButton = new JButton("History");
    public JButton loadButton = new JButton("Load from Save");
    public JButton betButton = new JButton("Bet");
    public JButton customiseTrackLengthButton = new JButton("Track Length");
    public JButton exitButton = new JButton("Exit");
    public BufferedImage backgroundImage;
    public Horse horse1;
    public Horse horse2;
    public Horse horse3;
    char codePointChar;
    String symbol;
    String horseName;
    double confidenceRating;
    String horseCoat;
    private Horse[] customHorses;
    int raceLengthDistance = 15;
    int linesLength = 0;

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
        buttonPanel.add(loadButton);
        buttonPanel.add(betButton);
        buttonPanel.add(customiseTrackLengthButton);
        buttonPanel.add(exitButton);

        startButton.setFocusable(false);
        customiseButton.setFocusable(false);
        historyButton.setFocusable(false);
        betButton.setFocusable(false);
        loadButton.setFocusable(false);
        exitButton.setFocusable(false);
        // action listener
        startButton.addActionListener(this);
        customiseButton.addActionListener(this);
        historyButton.addActionListener(this);
        loadButton.addActionListener(this);
        betButton.addActionListener(this);
        customiseTrackLengthButton.addActionListener(this);
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

        if (e.getSource() == customiseTrackLengthButton) {
            String raceLengthAsString = JOptionPane.showInputDialog("Enter race track length: (Default is 15)");
            if (raceLengthAsString == null) {
                System.out.println("Defaulting to length: " + raceLengthDistance);
                JOptionPane.showMessageDialog(this, "Operation cancelled, defaulting to 15");
            }
            else {
                try {
                    raceLengthDistance = Integer.parseInt(raceLengthAsString);
                    JOptionPane.showMessageDialog(this, "Choice Confirmed - Race Length is set to " + raceLengthDistance);
                }
                catch (NumberFormatException numberFormatException) {
                    System.out.println("Not a valid number!");
                }
            }
        }

        if (e.getSource() == loadButton) {
            try {
                BufferedReader lengthCounter = new BufferedReader(new FileReader("horseData.txt"));
                while ((lengthCounter.readLine()) != null) {
                    linesLength++;
                    System.out.println(linesLength + " entries");
                }

            } catch (IOException lackOfFileToReadFrom) {
                System.out.println("There's no file to read from! Running from default settings.");
            }
            // amount of lines in file is the size of the array
            customHorses = new Horse[linesLength];
            for (int i = 0; i<customHorses.length; i++) {
                customHorses[i] = new Horse();
            }
            System.out.println(linesLength);

            try {
                BufferedReader horseStateReader = new BufferedReader(new FileReader("horseData.txt"));
                String currLine;
                int currentIndex = 0;
                while ((currLine = horseStateReader.readLine()) != null) {
                    System.out.println(currLine);
                    String[] currentLineComponents = currLine.split(",");
                    for (int i = 0; i<currentLineComponents.length; i++) {
                        System.out.println(currentLineComponents[i]);
                    }

                    customHorses[currentIndex].setHorseStringSymbol(currentLineComponents[0]);
                    customHorses[currentIndex].setHorseName(currentLineComponents[1]);
                    customHorses[currentIndex].setConfidence(Double.parseDouble(currentLineComponents[2]));
                    customHorses[currentIndex].setHorseCoat(currentLineComponents[3]);
                    currentIndex++;
                }
            } catch (IOException lackOfFileToReadFrom) {
                System.out.println("There's no file to read from! Running from default settings.");
            }
        }

        if (e.getSource() == startButton) {
            if (customHorses != null) {
                this.dispose();
                Race r = new Race(raceLengthDistance, customHorses.length);
                for (int i = 0; i<customHorses.length; i++) {
                    r.addHorse(customHorses[i], (i+1));
                }
                r.startRace();
            }
            else {
                int dialogResult = JOptionPane.showConfirmDialog(this, "You didn't make any horses! Can I start the game with preset one?");
                if (dialogResult == JOptionPane.YES_OPTION) {
                    this.dispose();
                    // Default Case is 3 horses and 3 tracks
                    horse1 = new Horse(codePointChar, "Zappy-Horse", 0.46, "Palomino");
                    horse2 = new Horse(codePointChar,"Data-Horse", 0.5, "Bay");
                    horse3 = new Horse(codePointChar,"Server-Horse", 0.5, "Chestnut");

                    horse1.setSymbol('♕');
                    horse2.setSymbol('♞');
                    horse3.setSymbol('♘');

                    Race r = new Race(raceLengthDistance, 3);
                    r.addHorse(horse1, 1);
                    r.addHorse(horse2, 2);
                    r.addHorse(horse3, 3);
                    r.startRace();
                    System.out.println("race ended.. writing to file!");
                }
                else {
                    JOptionPane.showMessageDialog(this, "Click on Customise to create some!");
                }
            }
        }

        else if (e.getSource() == customiseButton) {
            System.out.println("clicked");
            try {
                String tracksAsString = JOptionPane.showInputDialog("Enter how many tracks you want: ");
                if (tracksAsString != null) {
                    int tracks = Integer.parseInt(tracksAsString);
                    customHorses = new Horse[tracks];
                    //System.out.println(raceLengthDistance);
                    for (int i = 0; i<customHorses.length; i++) {
                        horseName = JOptionPane.showInputDialog("Enter the name of your horse " + "(" + (i+1) +")");
                        if (horseName == null || horseName.isEmpty()) {
                            // user cancelled operation
                            JOptionPane.showMessageDialog(this, "Aborting Customisation");
                            break;
                        }
                        String confidenceRatingInput = JOptionPane.showInputDialog("Enter " + horseName + "'s confidence rating");
                        if (confidenceRatingInput == null || confidenceRatingInput.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Aborting Customisation");
                            break;
                        }
                        try {
                            confidenceRating = Double.parseDouble(confidenceRatingInput);
                            symbol = JOptionPane.showInputDialog("What should " + horseName + " be represented by? " + "(" + (i+1) +")");
                            if (symbol == null || symbol.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Aborting Customisation");
                                break;
                            }
                            codePointChar = symbol.charAt(0);
                            horseCoat = JOptionPane.showInputDialog("Enter the Horse's Coat");
                            if (horseCoat == null || horseCoat.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Aborting Customisation");
                                break;
                            }


                            customHorses[i] = new Horse(codePointChar, horseName, confidenceRating, horseCoat);
                        }
                        catch (NumberFormatException error) {
                            JOptionPane.showMessageDialog(this, confidenceRatingInput + " is not a valid number");
                        }
                    }
                }

            }
            catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(this, "Not a valid number");
            }
        }
    }
}
