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
    public JButton loadButton = new JButton("Load Save");
    public JButton betButton = new JButton("Bet");
    public JButton customiseTrackLengthButton = new JButton("Track Length");
    public JButton exitButton = new JButton("Exit");
    public BufferedImage backgroundImage;
    char codePointChar;
    String symbol;
    String horseName;
    String horseBoots;
    String horseCoat;
    private Horse[] customHorses;
    int raceLengthDistance = 15; // default race length distance
    int linesLength = 0;
    double confidenceRating;
    int loadCounter = 0;
    JPanel authorPanel;
    JPanel textPanel;
    JPanel historyPanel;
    JPanel buttonPanel;
    JButton historyBackButton;
    JPanel backgroundPanel;
    String selectedHorseName;
    Horse selectedHorse;
    double betAmount;


    public WindowFrame(String programTitle)  {
        System.out.println(VirtualCurrency.getCurrency());
        // sets program to be visible, 1280x720, halts on close
        this.setVisible(true);
        this.setLayout(null);
        this.setSize(1280,720);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(programTitle);

        textPanel = new JPanel();
        authorPanel = new JPanel();

        this.add(textPanel);
        textPanel.setOpaque(false);
        this.add(authorPanel);
        authorPanel.setOpaque(false);


        authorPanel.setBounds(0,300,this.getWidth(), 25);

        textPanel.setBounds(0,250,this.getWidth(),50);

        JLabel title = new JLabel(programTitle);
        title.setFont(new Font("Helvetica", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        JLabel author = new JLabel("Author: Joshua Cameron Ng - SID: 230309485");
        author.setForeground(Color.LIGHT_GRAY);
        textPanel.add(title);
        authorPanel.add(author);
        buttonPanel = new JPanel();
        this.add(buttonPanel);
        buttonPanel.setOpaque(false);

        buttonPanel.setBounds(0,350,this.getWidth(),150);

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
        } catch (NullPointerException | IOException error) {
            error.printStackTrace();
            System.out.println("Unable to load background image from source :( ");
        }

        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0,0, getWidth(), getHeight(), null);
            }
        };
        backgroundPanel.setBounds(0,0,this.getWidth(),this.getHeight());
        this.add(backgroundPanel);

    }

    public void placeBet() {
        // opens dialog for the user to place a bet
        System.out.println(betAmount);
        if (betAmount <= 0) {
            String betAmountString = JOptionPane.showInputDialog("Enter bet amount (you have " + VirtualCurrency.getCurrency() + " coins):");
            if (betAmountString != null && this.customHorses != null) {
                try {
                    double betAmountConfirmation = Double.parseDouble(betAmountString);
                    if (betAmountConfirmation <= VirtualCurrency.getCurrencyNumber() && betAmountConfirmation > 0) {
                        // allow the user to select a horse
                        String[] horseNames = getHorseNames();
                        selectedHorseName = (String) JOptionPane.showInputDialog(this, "Select a horse to bet on:",
                                "Select Horse", JOptionPane.PLAIN_MESSAGE, null, horseNames, horseNames[0]);
                        for (int i = 0; i<customHorses.length; i++) {
                            if (customHorses[i].getName().equals(selectedHorseName)) {
                                selectedHorse = customHorses[i];
                            }
                        }

                        if (selectedHorseName != null) {
                            betAmount = betAmountConfirmation;
                            System.out.println(betAmount);
                            VirtualCurrency.setCurrency(VirtualCurrency.getCurrencyNumber() - betAmount);
                            JOptionPane.showMessageDialog(this, "You bet: " + betAmount + " coins on " + selectedHorseName + "\nYour new balance is " + VirtualCurrency.getCurrency() + " coins\nIf your horse falls & loses, you will lose " + (betAmount+(betAmount*0.2)) + " extra coins\nIf your horse loses but doesn't fall, you will lose " + (betAmount+(betAmount*0.1)) + " extra coins\nIf you win the race, you will gain " + (betAmount*1.6) + " coins from your current balance of " + VirtualCurrency.getCurrency() + " coins");
                        }
                    }
                    else {
                        if (VirtualCurrency.getCurrencyNumber() < 0) {
                            JOptionPane.showMessageDialog(this, "You completely lost all your money, you need to restart!");
                        }
                        else {
                            JOptionPane.showMessageDialog(this, "Invalid bet amount!");
                        }
                    }
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number.");
                }
            }
            else {
                JOptionPane.showMessageDialog(this,"Operation cancelled - No horses loaded in or invalid bet amount");
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Ongoing bet: " + betAmount + " coins on " + selectedHorseName + "\nYour new balance is " + VirtualCurrency.getCurrency() + " coins\nIf your horse falls & loses, you will lose " + (betAmount+(betAmount*0.2)) + " extra coins\nIf your horse loses but doesn't fall, you will lose " + (betAmount+(betAmount*0.1)) + " extra coins\nIf you win the race, you will gain " + (betAmount*1.6) + " coins from your current balance of " + VirtualCurrency.getCurrency() + " coins");
        }
    }

    private String[] getHorseNames() {
        // gets horse names from customHorses array
        String[] horseNames = new String[customHorses.length];
        for (int i = 0; i < customHorses.length; i++) {
            horseNames[i] = customHorses[i].getName();
        }
        return horseNames;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // exit button closes
        if (e.getSource() == exitButton) {
            System.exit(0);
        }
        if (e.getSource() == betButton) {
            placeBet();
        }

        if (e.getSource() == historyButton) {
            textPanel.setVisible(false);
            authorPanel.setVisible(false);
            buttonPanel.setVisible(false);
            historyPanel = new JPanel();
            backgroundPanel.setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;
            constraints.fill = GridBagConstraints.BOTH;

            backgroundPanel.add(historyPanel, constraints);
            historyBackButton = new JButton("Back");
            historyBackButton.setContentAreaFilled(false);
            historyBackButton.setFocusable(false);
            historyPanel.add(historyBackButton);
            historyPanel.setVisible(true);
            historyPanel.setOpaque(false); // makes background transparent [FALSE]


            historyBackButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textPanel.setVisible(true);
                    authorPanel.setVisible(true);
                    buttonPanel.setVisible(true);
                    historyPanel.setVisible(false);
                    historyBackButton.setVisible(false);
                }
            });

            try (BufferedReader reader = new BufferedReader(new FileReader("results.txt"))) {
                String line;
                JLabel lastGame = new JLabel("Results of Last Game:      ");
                lastGame.setForeground(Color.white);
                Font results = new Font("Helvetica", Font.BOLD, 24);  // make a new font object

                lastGame.setFont(results);
                historyPanel.add(lastGame);
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    JLabel horseLabel = new JLabel("<html><b>Name:</b> " + parts[0] + "<br><b>Distance Travelled:</b> " + parts[1] + "<br><b>Symbol:</b> " + parts[2] + "<br><b>Horse Coat:</b> " + parts[3] + "<br><b>Boots:</b> " + parts[4] + "<br><br></html>");
                    JLabel breaks = new JLabel("<html><br><br></html>");

                    horseLabel.setForeground(Color.white);
                    historyPanel.add(breaks);
                    historyPanel.add(horseLabel);
                    historyPanel.setBackground(Color.BLACK);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "No results for last game available");
            }
            getContentPane().add(historyPanel);
            backgroundPanel.add(historyPanel);
            historyPanel.setOpaque(false);

            textPanel.setVisible(false);
            authorPanel.setVisible(false);
            buttonPanel.setVisible(false);
            historyPanel.setVisible(true);
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
            loadCounter++;
            if (loadCounter > 1) {
                System.out.println("Last session loaded already");
            }
            else {
                try {
                    BufferedReader lengthCounter = new BufferedReader(new FileReader("horseData.txt"));
                    while ((lengthCounter.readLine()) != null) {
                        linesLength++;
                        System.out.println(linesLength + " entries");
                    }
                    JOptionPane.showMessageDialog(this,"Last session loaded successfully");


                } catch (IOException lackOfFileToReadFrom) {
                    System.out.println("There's no file to read from! Running from default settings.");
                }

                // amount of lines in file is the size of the array
                customHorses = new Horse[linesLength];
                for (int i = 0; i < customHorses.length; i++) {
                    customHorses[i] = new Horse();
                }
            }

            try {
                JTextArea textArea = new JTextArea();
                BufferedReader horseStateReader = new BufferedReader(new FileReader("horseData.txt"));
                String currLine;
                int currentIndex = 0;
                while ((currLine = horseStateReader.readLine()) != null) {
                    String[] currentLineComponents = currLine.split(",");
                    for (int i = 0; i<currentLineComponents.length; i++) {
                        System.out.println(currentLineComponents[i]);
                    }

                    customHorses[currentIndex].setHorseStringSymbol(currentLineComponents[0]);
                    customHorses[currentIndex].setHorseName(currentLineComponents[1]);
                    customHorses[currentIndex].setConfidence(Double.parseDouble(currentLineComponents[2]));
                    customHorses[currentIndex].setHorseCoat(currentLineComponents[3]);
                    customHorses[currentIndex].setHorseBoots(currentLineComponents[4]);
                    currentIndex++;
                }
                for (int i = 0; i<customHorses.length; i++) {
                    textArea.append("Name: " + customHorses[i].getName() + " - Rating: " + customHorses[i].getConfidence() + " - Boots: " + customHorses[i].getHorseBoots() + " - Coat: " + customHorses[i].getHorseCoat());
                    textArea.append("\n");
                    textArea.setEditable(false);
                }
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                JOptionPane.showMessageDialog(this, scrollPane, "Entries Loaded", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException lackOfFileToReadFrom) {
                System.out.println("There's no file to read from! Running from default settings.");
            }
        }

        if (e.getSource() == startButton) {
            JTextArea afterGameText = new JTextArea();
            JScrollPane newScrollPane = new JScrollPane(afterGameText);
            if (customHorses != null) {
                Race r = new Race(raceLengthDistance, customHorses.length);
                for (int i = 0; i<customHorses.length; i++) {
                    r.addHorse(customHorses[i], (i+1));
                }
                this.dispose();
                r.startRaceGUI(this.getLocation());
                // if my horse wins

                // this statement only occurs if horse has been selected for a bet
                if (selectedHorse != null) {
                    if (selectedHorse.equals(r.getFurthestHorse())) {
                        System.out.println(VirtualCurrency.getCurrencyNumber());
                        double reward = betAmount * 1.6;
                    /* I bet 15 coins from 100 coins balance
                       85 coins remaining
                       i win -> 15x1.6 = 24
                       85+24 = 109 (new balance)
                    */
                        VirtualCurrency.addCurrency(reward);
                        double prevVal = VirtualCurrency.getCurrencyNumber() - reward;
                        afterGameText.append("Coins Available: " + VirtualCurrency.getCurrency() + " coins \n");
                        afterGameText.append("Congratulations! You won " + reward + " coins! (Up from " + prevVal + " coins)\n");
                        afterGameText.append("Coins won from the bet: " + reward + " (" + reward + ")\n");
                        afterGameText.append("You bet: " + betAmount + " coins\n");
                        afterGameText.setEditable(false);
                    }
                    // if my Horse Falls

                    else if (r.isHorseFallen(selectedHorse)) {

                        // if selected horse has just fell because of poor performance (and is not the winner)
                        // (20% coins extra on top of bet)
                        double valBeforeBet = VirtualCurrency.getCurrencyNumber();
                        double extraLoss = betAmount * 0.2; // Calculating the extra 20% loss on top of bet cost
                        double totalLoss = betAmount + extraLoss; // Total loss including the initial bet
                        double userTotalLoss = betAmount + (betAmount+extraLoss);
                        VirtualCurrency.forceSubtract(totalLoss);
                        System.out.println(VirtualCurrency.getCurrencyNumber());
                        afterGameText.append("Coins Available: " + VirtualCurrency.getCurrency() + " coins \n");
                        double prevVal = VirtualCurrency.getCurrencyNumber() + totalLoss;
                        afterGameText.append("\nYou lost " + (totalLoss) + " extra coins! (Down from " + prevVal + " coins) " + "\nInitial balance before deduction: " + Math.round(prevVal+betAmount) + " coins");
                        afterGameText.append("\nYou lost " + (userTotalLoss) + " coins TOTAL from betting cost and the multiplier");
                        afterGameText.append("\n\n("+betAmount + " + " + totalLoss + ") coins deducted");
                        afterGameText.append("\n\nValue solely after betting cost: " + valBeforeBet + " coins");
                        afterGameText.append("\nExtra Coins lost from the bet: " + totalLoss + "\n");
                        afterGameText.append("\nYou bet " + betAmount + " coins");
                        afterGameText.setEditable(false);
                    }
                    else {
                        // if selected horse has not fell but not won, user loses betAmount * 1.1 (10%)
                        // user loses 10% of coins on top of their bet (bet 10 coins, lose 11 coins extra (so they lost 21 coins))
                        double valBeforeBet = VirtualCurrency.getCurrencyNumber();
                        double extraLoss = betAmount * 0.1; // Calculating the extra 20% loss on top of bet cost
                        double totalLoss = betAmount + extraLoss; // Total loss including the initial bet
                        double userTotalLoss = betAmount + (betAmount+extraLoss);
                        VirtualCurrency.forceSubtract(totalLoss);
                        System.out.println(VirtualCurrency.getCurrencyNumber());
                        afterGameText.append("Coins Available: " + VirtualCurrency.getCurrency() + " coins \n");
                        double prevVal = VirtualCurrency.getCurrencyNumber() + totalLoss;
                        afterGameText.append("\nYou lost " + (totalLoss) + " extra coins! (Down from " + prevVal + " coins) " + "\nInitial balance before deduction: " + Math.round(prevVal+betAmount) + " coins");
                        afterGameText.append("\nYou lost " + (userTotalLoss) + " coins TOTAL from betting cost and the multiplier");
                        afterGameText.append("\n\n("+betAmount + " + " + totalLoss + ") coins deducted");
                        afterGameText.append("\n\nValue solely after betting cost: " + valBeforeBet + " coins");
                        afterGameText.append("\nExtra Coins lost from the bet: " + totalLoss + "\n");
                        afterGameText.append("\nYou bet " + betAmount + " coins");
                        afterGameText.setEditable(false);
                    }
                }

                else {
                    afterGameText.append(r.getFurthestHorse().getName() + " has won the race!");
                }

                newScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                JOptionPane.showMessageDialog(this, newScrollPane, "Race Results", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Race ended... writing to file!");


            }
            else {
                int dialogResult = JOptionPane.showConfirmDialog(this, "You didn't make any horses! Can I start the game with preset one?");
                if (dialogResult == JOptionPane.YES_OPTION) {
                    this.dispose();
                    // Default Case is 3 horses and 3 tracks
                    Horse horse1 = new Horse(codePointChar, "Zappy-Horse", 0.46, "Palomino", "Greygreen");
                    Horse horse2 = new Horse(codePointChar,"Data-Horse", 0.5, "Bay", "Black");
                    Horse horse3 = new Horse(codePointChar,"Server-Horse", 0.5, "Chestnut", "White");

                    horse1.setSymbol('♕');
                    horse2.setSymbol('♞');
                    horse3.setSymbol('♘');

                    Race r = new Race(raceLengthDistance, 3);
                    r.addHorse(horse1, 1);
                    r.addHorse(horse2, 2);
                    r.addHorse(horse3, 3);

                    r.startRaceGUI(this.getLocation());
                    afterGameText.append(r.getFurthestHorse().getName() + " has won the race!");
                    System.out.println("Race ended... writing to file!");
                    newScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    JOptionPane.showMessageDialog(this, newScrollPane, "Race Results", JOptionPane.INFORMATION_MESSAGE);
                    afterGameText.setEditable(false);
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
                    for (int i = 0; i<customHorses.length; i++) {

                        horseName = JOptionPane.showInputDialog("Enter the name of your horse " + "(" + (i+1) +")");
                        if (horseName == null || horseName.isEmpty()) {
                            // user cancelled operation
                            JOptionPane.showMessageDialog(this, "Aborting Customisation");
                            customHorses = null;
                            break;
                        }
                        String confidenceRatingInput = JOptionPane.showInputDialog("Enter " + horseName + "'s confidence rating");
                        if (confidenceRatingInput == null || confidenceRatingInput.isEmpty() || !isValidNumber(confidenceRatingInput)) {
                            JOptionPane.showMessageDialog(this, "Aborting Customisation");
                            customHorses = null;

                            break;
                        }
                        try {
                            confidenceRating = Double.parseDouble(confidenceRatingInput);
                            symbol = JOptionPane.showInputDialog("What should " + horseName + " be represented by? " + "(" + (i+1) +")");
                            if (symbol == null || symbol.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Aborting Customisation");
                                customHorses = null;

                                break;
                            }
                            codePointChar = symbol.charAt(0);
                            horseCoat = JOptionPane.showInputDialog("Enter horse coat for horse: " + horseName);
                            if (horseCoat == null || horseCoat.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Aborting Customisation");
                                customHorses = null;

                                break;
                            }

                            horseBoots = JOptionPane.showInputDialog("Enter horse boots for horse: " + horseName);
                            if (horseBoots == null || horseBoots.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Aborting Customisation");
                                customHorses = null;

                                break;
                            }
                            customHorses[i] = new Horse(codePointChar, horseName, confidenceRating, horseCoat, horseBoots);
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

    public static boolean isValidNumber(String input) {
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
