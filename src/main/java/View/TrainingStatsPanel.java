package View;

import Controller.ExercisePanelController;
import Model.Constants;
import Model.DataManger;
import View.Extensions.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TrainingStatsPanel {
    private PlaceholderTextField txtDate;
    private DataManger dataManger;
    private List<String> exerciseOrder; // = new ArrayList<>()
    private Map<String, ExercisePanel> exercisePanels = new HashMap<>();
    private int width = 0, height = 0;

    private JPanel pnlTrainingStats;
    private JPanel contentPanel;
    private JButton btnSubmit;
    
    private InfoBox infoBox;

    public TrainingStatsPanel(DataManger dataManger) {
        this.dataManger = dataManger;
        this.exerciseOrder = dataManger.getExerciseList();

        this.createPanel(1000, 1000);
    }

    private JPanel createPanel(int width, int height) {
        this.width = width;
        this.height = height;
        this.pnlTrainingStats = new JPanel();
        this.pnlTrainingStats.setBackground(Color.decode(Constants.BackgroundColor));
        this.pnlTrainingStats.setSize(width, height);
        this.pnlTrainingStats.setLayout(null);

        this.addComponents();

        return this.pnlTrainingStats;
    }

    private void addComponents() {
        JLabel lblHeadline = this.getHeadline();
        JPanel pnlDate = this.getInputDate();
        JScrollPane spContent = this.getContentScrollPane();
        JPanel pnlInfo = this.getInfoPanel();
        this.btnSubmit = this.getSubmitButton();

        this.pnlTrainingStats.add(lblHeadline);
        this.pnlTrainingStats.add(pnlDate);
        this.pnlTrainingStats.add(spContent);
        this.pnlTrainingStats.add(pnlInfo);
        this.pnlTrainingStats.add(this.btnSubmit);
    }

    private JLabel getHeadline() {
        JLabel label = new JLabel();
        label.setBounds(350, 10, 300, 40);
        label.setText("Add your workout");
        label.setFont(new Font("ITALIC", 2, 27));
        label.setVisible(true);

        return label;
    }

    private JPanel getInputDate() {
        JPanel pnl = new JPanel();
        this.txtDate = new PlaceholderTextField();
        this.txtDate.setPlaceholder("dd.mm.yyyy");
        JLabel lbl = new JLabel();
        lbl.setBounds(0, 0, 50, 30);
        lbl.setText("Date: ");
        lbl.setFont(new Font("Helvetica", 3, 16));
        this.txtDate.setBounds(50, 0, 200, 30);
        pnl.setLayout(null);
        pnl.add(lbl);
        pnl.add(this.txtDate);
        pnl.setBounds(30, 50, 260, 40);
        pnl.setBackground(Color.decode(Constants.BackgroundColor));

        return pnl;
    }

    private JScrollPane getContentScrollPane() {
        this.contentPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
        contentPanel.setLayout(boxlayout);

        JScrollPane sPExercises = new JScrollPane(contentPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        sPExercises.setLocation(10, 90);
        sPExercises.setSize(960, 700);
        sPExercises.getVerticalScrollBar().setUnitIncrement(Constants.ScrollSpeed);
        sPExercises.setVisible(true);


        for (String s : this.exerciseOrder) {
            this.exercisePanels.put(s, this.getExercisePanel(s));
            this.contentPanel.add(this.exercisePanels.get(s).getPnlExercise());   //need to change
        }

        this.contentPanel.setBackground(Color.decode("#F0F8FF"));

        this.contentPanel.add(Box.createVerticalGlue());
        return sPExercises;
    }

    private ExercisePanel getExercisePanel(String exercise) {
        ExercisePanel exercisePanel = new ExercisePanel(exercise);
        ExercisePanelController exercisePanelController = new ExercisePanelController(this.dataManger, exercisePanel);

        return exercisePanel;
    }

    private JButton getSubmitButton() {
        this.btnSubmit = new JButton();
        this.btnSubmit.setBounds(350, 850, 300, 50);
        this.btnSubmit.setText("Add to your training stats");
        this.btnSubmit.setFont(new Font("Helvetica", 3, 16));

        return this.btnSubmit;
    }

    private JPanel getInfoPanel() {
        int width = 300;
        int top = 50;
        int distanceRight = 40;

        this.infoBox = new InfoBox("<html><body>For body weight exercises " +
                "enter a 'b' or leave it empty<br>" +
                "extra weight: b+extra weight <br>" +
                "support weight: b-support weight");

        JPanel pnlInfo = this.infoBox.getPnlInfo();
        pnlInfo.setLocation(this.width - width-distanceRight, top - 38);

        return pnlInfo;
    }

    public void refresh() {
        this.exerciseOrder = this.dataManger.getExerciseList();
        this.contentPanel.removeAll();
        for (String e : this.exerciseOrder) {
            this.contentPanel.add(this.exercisePanels.get(e).getPnlExercise());
        }
        this.contentPanel.validate();
        this.contentPanel.repaint();
    }

    public JPanel getPnlTrainingStats() {
        return pnlTrainingStats;
    }

    public JButton getBtnSubmit() {
        return btnSubmit;
    }

    public PlaceholderTextField getTxtDate() {
        return txtDate;
    }

    public Map<String, ExercisePanel> getExercisePanels() {
        return exercisePanels;
    }
}
