package View;

import Model.Constants;
import Model.DataManger;
import Enum.ExerciseType;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Iterator;

public class StartFrame {
    private JFrame frame;
    private StartPanel startPanel;
    DataManger dataManger;
    TrainingStatsFrame trainingStatsFrame;
    PdfFrame pdfFrame;


    public StartFrame() {
        this.dataManger = new DataManger();
        this.startPanel = new StartPanel(dataManger);
    }

    public JFrame createFrame(String title, int width, int height) {

        this.frame = new JFrame();
        this.frame.setLayout(null);
        this.frame.setTitle(title);
        this.frame.getContentPane().setBackground(Color.decode("#ffddc1"));
        this.frame.setSize(width, height);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon("resources/img/logo.png");
        this.frame.setIconImage(imageIcon.getImage());

        JPanel pnlMainContent = this.startPanel.createPanel(width, height);

        this.frame.add(pnlMainContent);
        this.frame.setVisible(true);
        return this.frame;
    }

    public JFrame getFrame() {
        return this.frame;
    }
}