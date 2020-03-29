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
        frame = new JFrame();
      //  frame.getContentPane().setBackground(Color.decode(Constants.BackgroundColor));
        frame.setSize(width, height);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);


        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        frame.setTitle("Add your training stats");

        ImageIcon imageIcon = new ImageIcon("resources/img/logo.png");
        this.frame.setIconImage(imageIcon.getImage());

        JPanel pnlContent = this.trainingStatsPanel.getPnlTrainingStats();

        this.frame.add(pnlContent);

        this.frame.setVisible(true);

        Dimension diff = new Dimension();
        diff.height = this.frame.getSize().height - this.frame.getContentPane().getSize().height;
        diff.width = this.frame.getSize().width - this.frame.getContentPane().getSize().width;
        Dimension size = new Dimension();
        size.width = trainingStatsPanel.getMinSize().width + diff.width;
        size.height = trainingStatsPanel.getMinSize().height + diff.height;
        this.frame.setMinimumSize(size);

        pnlContent.getRootPane().setDefaultButton(trainingStatsPanel.getBtnSubmit());

    }
}