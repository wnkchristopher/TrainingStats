package views;

import configuration.Constants;
import layout.Alignment;
import layout.TrainingStatsConstraint;
import layout.TrainingStatsLayout;
import views.extensions.ButtonEditor;
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
    private Dimension minSize;

    private JLabel lblHeadline;
    private JPanel pnlDate;
    private JScrollPane spContent;
    private JPanel pnlTrainingStats;
    private JPanel pnlContent;
    private JButton btnSubmit;
    private JLabel lblInfoIcon;
    private JLabel lblText;

    private JButton btnBack;
    private InfoBox infoBox;

    public TrainingStatsPanel() {
        this.createPanel();
    }

    private JPanel createPanel() {
        this.pnlTrainingStats = new JPanel();
        this.pnlTrainingStats.setBackground(Color.decode(Constants.BackgroundColor));
        TrainingStatsLayout trainingStatsLayout = new TrainingStatsLayout();
        this.pnlTrainingStats.setLayout(trainingStatsLayout);

        this.addComponents();

        this.minSize = trainingStatsLayout.getMinSize();

        return this.pnlTrainingStats;
    }

    private void addComponents() {
        this.btnBack = this.getBackButton();
        this.lblHeadline = this.getHeadline();
        this.pnlDate = this.getInputDate();
        this.spContent = this.getContentScrollPane();
        this.addInfoPanel();
        this.btnSubmit = this.getSubmitButton();

        this.setupLayout();
    }

    private JButton getBackButton() {
        JButton btnBack = new JButton();
        btnBack =
                ButtonEditor.addImageToButton
                        (btnBack, this.getClass().getResource(Constants.PathBackImage), 25, 25);
        return btnBack;
    }

    private JLabel getHeadline() {
        JLabel label = new JLabel();
        label.setText("Add your workout");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("ITALIC", 2, 27));
        label.setVisible(true);

        return label;
    }

    private JPanel getInputDate() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new FlowLayout(FlowLayout.CENTER, 3,3));
        pnl.setOpaque(false);
        this.txtDate = new PlaceholderTextField();
        this.txtDate.setPlaceholder("dd.mm.yyyy");
        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(50, 50));
        lbl.setText("Date: ");
        lbl.setFont(new Font("Helvetica", 3, 16));
        this.txtDate.setPreferredSize(new Dimension(200, 30));
        pnl.add(lbl);
        pnl.add(this.txtDate);

        return pnl;
    }

    private JScrollPane getContentScrollPane() {
        this.pnlContent = new JPanel();
        BoxLayout boxlayout = new BoxLayout(pnlContent, BoxLayout.Y_AXIS);
        pnlContent.setLayout(boxlayout);

        JScrollPane sPExercises = new JScrollPane(pnlContent,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        sPExercises.getVerticalScrollBar().setUnitIncrement(Constants.ScrollSpeed);
        sPExercises.setVisible(true);

        this.pnlContent.setBackground(Color.decode("#F0F8FF"));

        this.pnlContent.add(Box.createVerticalGlue());
        return sPExercises;
    }

    private JButton getSubmitButton() {
        this.btnSubmit = new JButton();
        this.btnSubmit.setText("Add to your training stats");
        this.btnSubmit.setFont(new Font("Helvetica", 3, 16));

        return this.btnSubmit;
    }

    private void addInfoPanel() {
        this.infoBox = new InfoBox(Constants.TrainingStatsInfoText);

        this.lblInfoIcon = this.infoBox.getlblInfoIcon();
        this.lblText = this.infoBox.getLblText();
    }

    public void addExercisePanel(String exercise, ExercisePanel exercisePanel) {
        if (!this.exercisePanels.containsKey(exercise)) {
            Dimension max = exercisePanel.getPnlExercise().getMaximumSize();
            exercisePanel.getPnlExercise().setMaximumSize(new Dimension(max.width, 110));
            this.exercisePanels.put(exercise, exercisePanel);
        }
    }


    private void setupLayout() {
        TrainingStatsConstraint constraint;

        this.btnBack.setPreferredSize(new Dimension(40,30));
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(10,10, 0, 0);
        constraint.alignment = Alignment.NORTHWEST;
        constraint.fixed = true;
        this.pnlTrainingStats.add(this.btnBack, constraint);

        this.lblInfoIcon.setPreferredSize(new Dimension(30, 30));
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(10, 0, 0, 15);
        constraint.alignment = Alignment.NORTHEAST;
        constraint.fixed = true;
        this.pnlTrainingStats.add(this.lblInfoIcon, constraint);

        this.lblText.setPreferredSize(this.lblText.getPreferredSize());
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(10, 0, 0, 45);
        constraint.alignment = Alignment.NORTHEAST;
        constraint.fixed = true;
        this.pnlTrainingStats.add(this.lblText, constraint);

        this.lblHeadline.setPreferredSize(new Dimension(300, 40));
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(10, 0, 5, 0);
        constraint.alignment = Alignment.CENTER;
        this.pnlTrainingStats.add(lblHeadline, constraint);

        this.pnlDate.setPreferredSize(new Dimension(260, 43));
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(0, 15, 5, 0);
        constraint.alignment = Alignment.WEST;
        this.pnlTrainingStats.add(this.pnlDate, constraint);

        this.spContent.setMinimumSize(new Dimension(600, 300));
        this.spContent.setPreferredSize(new Dimension(960, 500));
        constraint = new TrainingStatsConstraint();
        constraint.insets = new Insets(5, 10, 5, 10);
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

    public JButton getBtnBack() {
        return btnBack;
    }

    public PlaceholderTextField getTxtDate() {
        return txtDate;
    }

    public Map<String, ExercisePanel> getExercisePanels() {
        return exercisePanels;
    }
}
