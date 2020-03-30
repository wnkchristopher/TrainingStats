package views;

import configuration.Constants;

import javax.swing.*;
import java.awt.*;


public class StartFrame {
    private JFrame frame;

    public StartFrame() {
    }

    public JFrame createFrame(StartPanel startPanel, String title) {

        this.frame = new JFrame();
        this.frame.setLayout(new BorderLayout());
        this.frame.setTitle(title);

        ImageIcon imageIcon = new ImageIcon("resources/img/logo.png");
        this.frame.setIconImage(imageIcon.getImage());

        JPanel pnlMainContent = startPanel.createPanel();

        this.frame.add(pnlMainContent, BorderLayout.CENTER);

        this.frame.setVisible(true);

        Dimension minSize = pnlMainContent.getMinimumSize();
        minSize.width += this.frame.getInsets().left + this.frame.getInsets().right;
        minSize.height += this.frame.getInsets().top + this.frame.getInsets().bottom;
        this.frame.setMinimumSize(minSize);

        this.frame.pack();
        Dimension dimension = startPanel.getPnlMain().getMaximumSize();
        dimension.width = dimension.width + 100;

        this.frame.setSize(dimension);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation
                (dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return this.frame;
    }

    public JFrame getFrame() {
        return this.frame;
    }
}