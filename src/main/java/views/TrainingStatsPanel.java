package views;

import configuration.Constants;
import layout.Alignment;
import layout.TrainingStatsConstraint;
import layout.TrainingStatsLayout;
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
    private Dimension minSize;

    private JLabel lblHeadline;
    private JPanel pnlDate;
    private JScrollPane spContent;
    private JPanel pnlTrainingStats;
    private JPanel pnlContent;
    private JButton btnSubmit;
    private JLabel lblInfoIcon;
    private JLabel lblText;

    private InfoBox infoBox;

    public TrainingStatsPanel() {
        this.createPanel(1000, 1000);
    }

    private JPanel createPanel(int width, int height) {
        this.width = width;
        this.height = height;
        this.pnlTrainingStats = new JPanel();
        this.pnlTrainingStats.setBackground(Color.decode(Constants.BackgroundColor));
        //this.pnlTrainingStats.setSize(width, height);
        TrainingStatsLayout trainingStatsLayout = new TrainingStatsLayout();
        this.pnlTrainingStats.setLayout(trainingStatsLayout);

        this.addComponents();

        this.minSize = trainingStatsLayout.getMinSize();

        return this.pnlTrainingStats;
    }

    private void addComponents() {
        this.lblHeadline = this.getHeadline();
        this.pnlDate = this.getInputDate();
        this.spContent = this.getContentScrollPane();
        this.addInfoPanel();
        this.btnSubmit = this.getSubmitButton();

        this.setupLayout();

      //  this.pnlTrainingStats.add(this.lblHeadline);
      //  this.pnlTrainingStats.add(this.pnlDate);
      //  this.pnlTrainingStats.add(this.spContent);
        //this.pnlTrainingStats.add(pnlInfo);
       // this.pnlTrainingStats.add(this.btnSubmit);
    }

    private JLabel getHeadline() {
        JLabel label = new JLabel();
       // label.setBounds(350, 10, 300, 40);
        label.setText("Add your workout");
        label.setHorizontalAlignment(JLabel.CENTER);
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
        //pnl.setBounds(30, 50, 260, 40);
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

       // sPExercises.setLocation(10, 90);
       // sPExercises.setSize(960, 700);
        sPExercises.getVerticalScrollBar().setUnitIncrement(Constants.ScrollSpeed);
        sPExercises.setVisible(true);

        this.pnlContent.setBackground(Color.decode("#F0F8FF"));

        this.pnlContent.add(Box.createVerticalGlue());
        return sPExercises;
    }

    private JButton getSubmitButton() {
        this.btnSubmit = new JButton();
       // this.btnSubmit.setBounds(350, 850, 300, 50);
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

        this.lblInfoIcon = this.infoBox.getlblInfoIcon();
     //   lblInfoIcon.setLocation(this.width - distanceRight - lblInfoIcon.getWidth(), top - 38);
        this.lblText = this.infoBox.getLblText();
     //   lblText.setLocation(this.width - distanceRight - lblInfoIcon.getWidth() - lblText.getWidth(), top - 38);

       // this.pnlTrainingStats.add(lblInfoIcon);
       // this.pnlTrainingStats.add(lblText);
    }

    public void addExercisePanel(String exercise, ExercisePanel exercisePanel) {
        if(!this.exercisePanels.containsKey(exercise)){
            this.exercisePanels.put(exercise, exercisePanel);
        }
    }


    private void setupLayout() {
        TrainingStatsConstraint constraint;

        this.lblInfoIcon.setPreferredSize(new Dimension(30,30));
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(10,0,0, 15);
        constraint.alignment = Alignment.NORTHEAST;
        constraint.fixed = true;
        this.pnlTrainingStats.add(this.lblInfoIcon, constraint);

        this.lblText.setPreferredSize(new Dimension(270,90));
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(10,0, 0, 45);
        constraint.alignment = Alignment.NORTHEAST;
        constraint.fixed = true;
        this.pnlTrainingStats.add(this.lblText, constraint);

        this.lblHeadline.setPreferredSize(new Dimension(300, 40));
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(10, 0, 5, 0);
        constraint.alignment = Alignment.CENTER;
        this.pnlTrainingStats.add(lblHeadline, constraint);

        this.pnlDate.setPreferredSize(new Dimension(260,40));
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(0, 15, 5, 0);
        constraint.alignment = Alignment.WEST;
        this.pnlTrainingStats.add(this.pnlDate, constraint);

        this.spContent.setMinimumSize(new Dimension(600,300));
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(0, 10, 5, 10);
        constraint.alignment = Alignment.WEST;
        constraint.fillWidth = true;
        constraint.fillHeight = true;
        this.pnlTrainingStats.add(this.spContent, constraint);

        this.btnSubmit.setPreferredSize(new Dimension(300, 50));
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(30, 0, 30, 0);
        constraint.alignment = Alignment.CENTER;
        this.pnlTrainingStats.add(this.btnSubmit, constraint);
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

    public Dimension getMinSize() {
        return minSize;
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
