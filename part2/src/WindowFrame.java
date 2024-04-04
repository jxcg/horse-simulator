import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowFrame extends JFrame implements ActionListener {

    WindowFrame(String programTitle) {
        // frame is visible, 1280x720, program halts when closed
        this.setVisible(true); this.setSize(1280,720); this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(programTitle);





    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
