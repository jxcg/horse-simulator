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
        exitButton.addActionListener(this);
        startButton.addActionListener(this);


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
            startButton.setForeground(Color.GREEN);
            Point frameLocation = this.getLocation();
            PlayWindow playInstance = new PlayWindow(frameLocation);
            this.dispose();


        }
    }
}
