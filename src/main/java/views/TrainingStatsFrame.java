package views;


import configuration.Constants;

import javax.swing.*;
import java.awt.*;

public class TrainingStatsFrame {
    JFrame frame;
    TrainingStatsPanel trainingStatsPanel;

    public TrainingStatsFrame() {
    }


    public void createFrame(TrainingStatsPanel trainingStatsPanel, int width, int height) {
        this.trainingStatsPanel = trainingStatsPanel;

        this.frame = new JFrame();
        this.frame.setTitle(Constants.TrainingStatsTitle);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon("resources/img/logo.png");
        this.frame.setIconImage(imageIcon.getImage());

        JPanel pnlContent = this.trainingStatsPanel.getPnlTrainingStats();

        this.frame.add(pnlContent);

        this.frame.setVisible(true);


        Dimension minSize = pnlContent.getMinimumSize();
        minSize.width += this.frame.getInsets().left + this.frame.getInsets().right;
        minSize.height += this.frame.getInsets().top + this.frame.getInsets().bottom;
        this.frame.setMinimumSize(minSize);
        this.frame.pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation
                (dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        pnlContent.getRootPane().setDefaultButton(trainingStatsPanel.getBtnSubmit());

    }
}