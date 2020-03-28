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
        frame.getContentPane().setBackground(Color.decode(Constants.BackgroundColor));
        frame.setSize(width, height);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);


        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLayout(null);
        frame.setTitle("Add your training stats");

        ImageIcon imageIcon = new ImageIcon("resources/img/logo.png");
        this.frame.setIconImage(imageIcon.getImage());

        JPanel pnlContent = this.trainingStatsPanel.getPnlTrainingStats();

        this.frame.add(pnlContent);

        pnlContent.getRootPane().setDefaultButton(trainingStatsPanel.getBtnSubmit()); //if trainingstatsPanel is activve

        this.frame.setVisible(true);
    }
}