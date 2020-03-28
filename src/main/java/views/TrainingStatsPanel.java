package views;

import configuration.Constants;
import views.extensions.InfoBox;
import views.extensions.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TrainingStatsPanel {
    private PlaceholderTextField txtDate;
    private List<String> exerciseOrder;
    private Map<String, ExercisePanel> exercisePanels = new HashMap<>();
    private int width = 0, height = 0;

    private JPanel pnlTrainingStats;
    private JPanel pnlContent;
    private JButton btnSubmit;

    private InfoBox infoBox;

    public TrainingStatsPanel() {
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
        this.addInfoPanel();
        this.btnSubmit = this.getSubmitButton();

        this.pnlTrainingStats.add(lblHeadline);
        this.pnlTrainingStats.add(pnlDate);
        this.pnlTrainingStats.add(spContent);
        //this.pnlTrainingStats.add(pnlInfo);
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
        this.pnlContent = new JPanel();
        BoxLayout boxlayout = new BoxLayout(pnlContent, BoxLayout.Y_AXIS);
        pnlContent.setLayout(boxlayout);

        JScrollPane sPExercises = new JScrollPane(pnlContent,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        sPExercises.setLocation(10, 90);
        sPExercises.setSize(960, 700);
        sPExercises.getVerticalScrollBar().setUnitIncrement(Constants.ScrollSpeed);
        sPExercises.setVisible(true);

        this.pnlContent.setBackground(Color.decode("#F0F8FF"));

        this.pnlContent.add(Box.createVerticalGlue());
        return sPExercises;
    }

    private JButton getSubmitButton() {
        this.btnSubmit = new JButton();
        this.btnSubmit.setBounds(350, 850, 300, 50);
        this.btnSubmit.setText("Add to your training stats");
        this.btnSubmit.setFont(new Font("Helvetica", 3, 16));

        return this.btnSubmit;
    }

    private void addInfoPanel() {
        int top = 50;
        int distanceRight = 40;

        this.infoBox = new InfoBox("<html><body>For body weight exercises " +
                "enter a 'b' or leave it empty<br>" +
                "extra weight: b+extra weight <br>" +
                "support weight: b-support weight");

        JLabel lblInfoIcon = this.infoBox.getlblInfoIcon();
        lblInfoIcon.setLocation(this.width - distanceRight - lblInfoIcon.getWidth(), top - 38);
        JLabel lblText = this.infoBox.getLblText();
        lblText.setLocation(this.width - distanceRight - lblInfoIcon.getWidth() - lblText.getWidth(), top - 38);

        this.pnlTrainingStats.add(lblInfoIcon);
        this.pnlTrainingStats.add(lblText);
    }

    public void addExercisePanel(String exercise, ExercisePanel exercisePanel) {
        if(!this.exercisePanels.containsKey(exercise)){
            this.exercisePanels.put(exercise, exercisePanel);
        }
    }

    public void refresh(List<String> exercises) {
        this.pnlContent.removeAll();
        this.exerciseOrder = exercises;
        for (String exercise : this.exerciseOrder) {
            this.pnlContent.add(this.exercisePanels.get(exercise).getPnlExercise());
        }
        this.pnlContent.validate();
        this.pnlContent.repaint();
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
