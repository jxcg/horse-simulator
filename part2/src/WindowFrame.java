import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowFrame extends JFrame implements ActionListener {

    WindowFrame(String programTitle) {
        // sets program to be visible, 1280x720, halts on close
        this.setVisible(true);
        this.setSize(1280,720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(programTitle);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.TRAILING);
        this.setLayout(flowLayout);
        JButton startButton = new JButton("Start"); this.add(startButton);
        startButton.setPreferredSize(new Dimension(65,35));
        JButton optionsButton = new JButton("Options"); this.add(optionsButton);
        optionsButton.setPreferredSize(new Dimension(65,35));
        JButton exitButton = new JButton("Exit"); this.add(exitButton);
        exitButton.setPreferredSize(new Dimension(65,35));






        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
