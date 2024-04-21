import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * The Horse class is in charge of instantiating the Horse object,
 * represented by a Unicode character, name,
 * with details about the Horse object, such as the distance travelled of the individual Horse
 * plus its confidence rating.
 *
 * @author: Joshua Cameron Ng
 * GUI version 2.0 - 22/03/24
 */
public class Horse {
    // Fields of class
    private String horseName; /* e.g., Pippi Longstocking */
    private char horseSymbol; /* represents horse char unicodeChar = '\u0041'; */
    private int horseTravelled; /* whole number */
    private boolean horseFell;
    private double horseConfidence; /* 0-1 */
    private String horseCoat;
    private String horseBoots;
    // Constructor of class Horse

    public Horse(char horseSymbol, String horseName, double horseConfidence, String horseCoat, String horseBoots) {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
        this.horseCoat = horseCoat;
        this.horseBoots = horseBoots;
    }


    public Horse() {
        // blank constructor
    }
    // Other methods of class Horse

    // Accessor Methods
    public double getConfidence() {
        return this.horseConfidence;
    }

    public String getHorseCoat() {
        return this.horseCoat;
    }

    public void setHorseCoat(String horseCoat) {
        this.horseCoat = horseCoat;
    }

    public int getDistanceTravelled() {
        return this.horseTravelled;
    }

    public String getName() {
        return this.horseName;
    }

    public char getSymbol() {
        return this.horseSymbol;
    }

    public void setHorseStringSymbol(String horseSymbol) {
        this.horseSymbol = horseSymbol.charAt(0);
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public boolean hasFallen() {
        return this.horseFell;
    }

    // Mutator Methods

    public void fall() {
        this.horseFell = true;
    }

    public void goBackToStart() {
        this.horseTravelled = 0; /* resets horse back to start of race */
    }

    public void moveForward() {
        this.horseTravelled = this.horseTravelled + 1;
    }

    public void setConfidence(double newConfidence) {
        this.horseConfidence = newConfidence;
    }

    public void setSymbol(char newSymbol) {
        this.horseSymbol = newSymbol;
    }

    public String getHorseBoots() {
        return this.horseBoots;
    }

    public void setHorseBoots(String input) {
        if (input != null && !input.isEmpty()) {
            this.horseBoots = input;
        }
    }
}