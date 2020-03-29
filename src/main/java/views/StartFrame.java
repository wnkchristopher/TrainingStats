package views;

import configuration.Constants;

import javax.swing.*;
import java.awt.*;


public class StartFrame {
    private JFrame frame;

    public StartFrame() {
    }

    public JFrame createFrame(StartPanel startPanel, String title, int width, int height) {

        this.frame = new JFrame();
        this.frame.setLayout(new BorderLayout());
        this.frame.setTitle(title);
        this.frame.setMinimumSize(new Dimension(width, height));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation
                (dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon("resources/img/logo.png");
        this.frame.setIconImage(imageIcon.getImage());

        JPanel pnlMainContent = startPanel.createPanel();

        this.frame.getContentPane().setBackground(Color.decode(Constants.BackgroundColor));

        this.frame.add(pnlMainContent, BorderLayout.NORTH);

        this.frame.setVisible(true);


        return this.frame;
    }

    public JFrame getFrame() {
        return this.frame;
    }
}