import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayWindow extends JFrame implements ActionListener {

    public PlayWindow(Point frameLocation) {
        this.setVisible(true);
        this.setSize(1280,720);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(("Horse Simulator"));
        this.setLocation(frameLocation);

    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
