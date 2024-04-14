import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        long currentTimestamp = System.currentTimeMillis() / 1000;
        System.out.println("EPOCH: " + currentTimestamp);
        final String programTitle = "Horse Simulator";
        WindowFrame wf = new WindowFrame(programTitle);
        wf.setVisible(true);
    }
}