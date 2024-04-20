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

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
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

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     */

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
            //resultsWriter.write("WINNER HORSE: " + Objects.requireNonNull(getFurthestHorse()).getName() + "," + finishingTime);
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
        //resultsWriter.write("WINNER HORSE: " + Objects.requireNonNull(getWinningHorse()).getName() + "," + finishingTime);
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



    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     *
     * @param theHorse the horse to be moved
     */
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

    /***
     * Print the race on the terminal
     */
    private void printRace() {
        System.out.print("\u000C");
        clearScreen();
        multiplePrint('=', raceLength + 3);
        System.out.println();

        for (Horse horse : horses) {
            if (horse != null) {
                printLane(horse);
                System.out.println(" " + horse.getName() + " (Current confidence: " + horse.getConfidence() + ")" + " Coat: " + horse.getHorseCoat() + " | [BOOTS] " + horse.getHorseBoots());
            }
        }

        multiplePrint('=', raceLength + 3);
        System.out.println();
    }

    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse) {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();

        //print a | for the beginning of the lane
        System.out.print('|');

        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);

        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen()) {
            System.out.print('❌');
        } else {
            System.out.print(theHorse.getSymbol());
        }

        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);

        //print the | for the end of the track
        System.out.print("|");
    }
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     *
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times) {
        int i = 0;
        while (i < times) {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    // Clears the screen properly so it can keep running
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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


    public void startRaceGUI() {
        startTime = getCurrentTime();
        // Declare a local variable to tell us when the race is finished
        boolean finished = false;
        // Reset all the lanes (all horses not fallen and back to 0).

        for (Horse horse : horses) {
            if (horse != null) {
                horse.goBackToStart();
            }
        }

        // Create a new JFrame for the race window
        JFrame raceWindow = new JFrame("Horse Race");
        raceWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the JTextArea containing race output to a JScrollPane for scrolling
        JScrollPane scrollPane = new JScrollPane(raceOutput);
        scrollPane.setPreferredSize(new Dimension(1280, 720));

        // Create a JPanel to hold the scroll pane
        JPanel racePanel = new JPanel();
        racePanel.setBackground(Color.LIGHT_GRAY);
        racePanel.setLayout(new BorderLayout());
        racePanel.add(scrollPane, BorderLayout.CENTER);


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
                raceOutput.append(Objects.requireNonNull(getWinningHorse()).getName() + " has won the race!");

                try {
                    BufferedWriter resultsWriter = getBufferedWriter(allHorsesFallen());
                    resultsWriter.close();
                    System.out.println("The race ended in " + finishingTime + " milliseconds");
                } catch (IOException e) {
                    System.err.println("Error writing to results file. See error logs for more");
                    System.out.println();
                    System.out.println(e);
                }
            }

            if (allHorsesFallen()) {
                endTime = getCurrentTime();
                finishingTime = endTime-startTime;
                furthestHorse();
                finished = true;
                try {
                    BufferedWriter resultsWriter = getBufferedWriter(allHorsesFallen());
                    resultsWriter.close();
                    System.out.println("The race ended in " + finishingTime + " milliseconds");
                } catch (IOException e) {
                    System.err.println("Error writing to results file. See error logs for more" );
                    System.out.println();
                    System.out.println(e);
                }

            }



        }
    }
}