import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 *
 * @author McFarewell, Joshua Cameron Ng
 * @version 2.0 - 22/03/24
 */
public class Race {
    private int raceLength;
    private Horse[] horses;
    long startTime;
    long endTime;
    long finishingTime;
    private JTextArea raceOutput;
    JButton openMainMenu;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, int amountOfHorses) {
        // initialise instance variables
        raceLength = distance;
        horses = new Horse[amountOfHorses];
        raceOutput = new JTextArea();
        raceOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        raceOutput.setEditable(false);
    }

    public void addHorse(Horse theHorse, int laneNumber) {
        if (laneNumber >= 1 && laneNumber <= horses.length) {
            horses[laneNumber - 1] = theHorse;
        } else {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    private BufferedWriter getBufferedWriter(boolean allHorseFallen) throws IOException {
        BufferedWriter resultsWriter = new BufferedWriter(new FileWriter("results.txt"));
        if (allHorseFallen) {
            int currentLine = 0;
            for (Horse horse : horses) {
                resultsWriter.write(horse.getName() + "," + horse.getDistanceTravelled() + "," + horse.getSymbol() + "," + horse.getHorseCoat() + "," + horse.getHorseBoots());
                currentLine++;
                if (currentLine < horses.length) {
                    resultsWriter.newLine();
                }
            }
            return resultsWriter;
        }
        int currentLine = 0;

        for (Horse horse : horses) {
            resultsWriter.write(horse.getName() + "," + horse.getDistanceTravelled() + "," + horse.getSymbol() + "," + horse.getHorseCoat() + "," + horse.getHorseBoots());
            currentLine++;
            if (currentLine < horses.length) {
                resultsWriter.newLine();
            }
        }
        return resultsWriter;
    }


    private Horse getWinningHorse() {
        for (Horse horse : horses) {
            if (horse.getDistanceTravelled() == raceLength) {
                return horse;
            }
        }
        return null;
    }

    private boolean allHorsesFallen() {
        for (Horse horse : horses) {
            if (!horse.hasFallen()) {
                return false;
            }
        }
        return true;
    }

    private void moveHorse(Horse theHorse) {
        //if the horse has fallen it cannot move,
        //so only run if it has not fallen

        if (!theHorse.hasFallen()) {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();
            }

            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence())) {
                theHorse.fall();
                theHorse.setConfidence(theHorse.getConfidence() - 0.1);
                // calls the decrement on the current Horse's confidence level
            }
        }
    }
    /**
     * Determines if a horse has won the race
     *
     * @param 'theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy() {
        for (Horse horse : horses) {
            if (horse.getDistanceTravelled() == raceLength) {
                horse.setConfidence(horse.getConfidence() + 0.1);
                return true;
            }
        }
        return false;
    }

    public void furthestHorse() {
        int furthestDistance = 0;
        Horse furthestHorse = null;

        for (Horse horse : horses) {
            if (horse != null && horse.getDistanceTravelled() > furthestDistance) {
                furthestDistance = horse.getDistanceTravelled();
                furthestHorse = horse;
            }
        }

        if (furthestHorse != null) {
            System.out.println(furthestHorse.getName() + " has won the race as it has travelled the furthest (" +  furthestHorse.getDistanceTravelled() + " blocks)");
        }
    }

    public boolean isHorseFallen(Horse horses) {
        if (horses.hasFallen()) {
            return true;
        }
        return false;
    }

    public Horse getFurthestHorse() {
        int furthestDistance = 0;
        Horse furthestHorse = null;
        for (Horse horse : horses) {
            if (horse != null && horse.getDistanceTravelled() > furthestDistance) {
                furthestDistance = horse.getDistanceTravelled();
                furthestHorse = horse;
            }
        }
        return furthestHorse;
    }
    ///////
    //////
    ///

    private void updateRaceText() {
        raceOutput.setText(""); // Clear previous text

        raceOutput.append(String.format("%" + (raceLength + 3) + "s\n", "=".repeat(raceLength + 3)));

        for (Horse horse : horses) {
            if (horse != null) {
                buildAndAppendLane(horse);
                raceOutput.append(" " + horse.getName() + " (Current confidence: " + horse.getConfidence() + ")" + " Coat: " + horse.getHorseCoat() + " | [BOOTS] " + horse.getHorseBoots() + "\n");
            }
        }
        raceOutput.append(String.format("%" + (raceLength + 3) + "s\n", "=".repeat(raceLength + 3)));
    }

    private void buildAndAppendLane(Horse horse) {
        StringBuilder laneBuilder = new StringBuilder("|");

        int spacesBefore = horse.getDistanceTravelled();
        int spacesAfter = raceLength - horse.getDistanceTravelled();

        for (int i = 0; i < spacesBefore; i++) {
            laneBuilder.append(" ");
        }

        if (horse.hasFallen()) {
            laneBuilder.append('❌');
        } else {
            laneBuilder.append(horse.getSymbol());
        }

        for (int i = 0; i < spacesAfter; i++) {
            laneBuilder.append(" ");
        }

        laneBuilder.append("|");
        raceOutput.append(laneBuilder.toString());
    }


    public void startRaceGUI(Point windowLocation) {
        startTime = getCurrentTime();
        boolean finished = false;

        for (Horse horse : horses) {
            if (horse != null) {
                horse.goBackToStart();
            }
        }

        // Create a new JFrame for the race window
        JFrame raceWindow = new JFrame("Horse Race");
        raceWindow.setLocation(windowLocation);
        raceWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the JTextArea containing race output to a JScrollPane for scrolling
        JScrollPane scrollPane = new JScrollPane(raceOutput);
        scrollPane.setPreferredSize(new Dimension(1280, 720));

        // Create a JPanel to hold the scroll pane
        JPanel racePanel = new JPanel();
        racePanel.setBackground(Color.LIGHT_GRAY);
        racePanel.setLayout(new BorderLayout());
        racePanel.add(scrollPane, BorderLayout.CENTER);
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> {
                    // Reset the race state
                    WindowFrame wf = new WindowFrame("Horse Simulator");
                    Point raceWindowLocation = raceWindow.getLocation();

                    raceWindow.dispose();
                    wf.setLocation(raceWindowLocation);
                    wf.setVisible(true);
                });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(mainMenuButton);
        racePanel.add(buttonPanel, BorderLayout.SOUTH);


        // Add the race panel to the JFrame
        raceWindow.getContentPane().add(racePanel);

        // Pack and set the window visible
        raceWindow.pack();
        raceWindow.setVisible(true);

        while (!finished) {
            // Move each horse
            for (Horse horse : horses) {
                if (horse != null) {
                    moveHorse(horse);
                }
            }
            // Update the JTextArea with the current race state
            updateRaceText();
            raceOutput.update(raceOutput.getGraphics());

            try {
                Thread.sleep(100);
            } catch(Exception e) {
                System.out.println(e);
            }

            // If any of the three horses has won the race is finished
            if (raceWonBy()) {
                endTime = getCurrentTime();
                finishingTime = endTime - startTime;

                updateRaceText();
                System.out.println(Objects.requireNonNull(getWinningHorse()).getName() + " has won the race!");
                finished = true;
                raceOutput.append(Objects.requireNonNull(getWinningHorse()).getName() + " has won the race! (" + finishingTime + " milliseconds)");
                raceOutput.append("\nGame ended - writing results to file ...");
                openMainMenu = new JButton();
                racePanel.add(openMainMenu);
                openMainMenu.setVisible(true);

                try {
                    BufferedWriter resultsWriter = getBufferedWriter(allHorsesFallen());
                    resultsWriter.close();
                    try {
                        BufferedWriter horseData = new BufferedWriter(new FileWriter("horseData.txt"));
                        int currentLine = 0;
                        for (Horse individualHorse : horses) {
                            horseData.write(individualHorse.getSymbol() + "," + individualHorse.getName() + "," + individualHorse.getConfidence() +"," + individualHorse.getHorseCoat() + "," + individualHorse.getHorseBoots());
                            currentLine++;
                            if (currentLine < horses.length) {
                                horseData.newLine();
                            }
                        }
                        horseData.close();
                    }
                    catch (IOException error) {
                        System.err.println("Error writing to state file.");
                        System.out.println("Unable to write to file!");
                    }
                    System.out.println("The race ended in " + finishingTime + " milliseconds");
                } catch (IOException e) {
                    System.err.println("Error writing to results file. See error logs for more");
                    System.out.println();
                }
            }

            if (allHorsesFallen()) {
                endTime = getCurrentTime();
                finishingTime = endTime-startTime;
                furthestHorse();
                raceOutput.append(Objects.requireNonNull(getFurthestHorse()).getName() + " has won the race! (" + finishingTime + " milliseconds)");
                raceOutput.append("\nGame ended - writing results to file ...");
                finished = true;
                try {
                    BufferedWriter resultsWriter = getBufferedWriter(allHorsesFallen());
                    resultsWriter.close();
                }
                catch (IOException e) {
                    System.out.println(e);
                }

                try {
                    BufferedWriter horseData = new BufferedWriter(new FileWriter("horseData.txt"));
                    int currentLine = 0;
                    for (Horse individualHorse : horses) {
                        horseData.write(individualHorse.getSymbol() + "," + individualHorse.getName() + "," + individualHorse.getConfidence() +"," + individualHorse.getHorseCoat() + "," + individualHorse.getHorseBoots());
                        currentLine++;
                        if (currentLine < horses.length) {
                            horseData.newLine();
                        }
                    }
                    horseData.close();
                }
                catch (IOException error) {
                    System.err.println("Error writing to state file.");
                    System.out.println("Unable to write to file!");
                }
            }
        }
    }
}