package View;

import Model.DataManger;
import Model.Constants;

import javax.swing.*;
import java.awt.*;

public class TrainingStatsFrame {
    JFrame frame;
    DataManger dataManger;
    private TrainingStatsPanel trainingStatsPanel;

    public TrainingStatsFrame() {
        dataManger = new DataManger();
    }

    public void createFrame() {
        this.createFrame(1000, 1000);
    }

    public void createFrame(int width, int height) {
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

        this.trainingStatsPanel = new TrainingStatsPanel();
        JPanel pnlContent = trainingStatsPanel.createPanel(width, height);

        this.frame.add(pnlContent);
        this.frame.setVisible(true);
    }
}