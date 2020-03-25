package View;

import View.Extensions.ButtonEditor;
import View.Extensions.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ExercisePanel {

    private String exercise;
    private JPanel pnlExercise;
    private Map<Integer, PlaceholderTextField[]> txtFields;
    private JButton btnPlus;
    private JPanel pnlBtnUpDown;
    private JButton btnUp;
    private JButton btnDown;
    private JPanel pnlSpacer;
    private int set = 1;


    public ExercisePanel(String exercise) {
        txtFields = new HashMap<>();
        this.pnlExercise = this.createExercisePanel(exercise);
        this.exercise = exercise;
        this.addNewSet();
        this.addNewSet();
    }

    private JPanel createExercisePanel(String exercise) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(boxLayout);
        panel.setPreferredSize(new Dimension(930, 100));
        panel.setMinimumSize(new Dimension(930, 100));
        panel.setMaximumSize(new Dimension(930, 100));

        JLabel lblExerciseTitle = new JLabel();
        lblExerciseTitle.setText(exercise);
        lblExerciseTitle.setFont(new Font("Helvetica", 1, 16));
        lblExerciseTitle.setPreferredSize(new Dimension(200, 30));
        lblExerciseTitle.setAlignmentX(Component.LEFT_ALIGNMENT);


        this.pnlBtnUpDown = this.getUpDownButton(exercise);
        this.pnlBtnUpDown.setAlignmentX(Component.RIGHT_ALIGNMENT);

        this.pnlSpacer = new JPanel();

        JButton btnPlus = new JButton("+");
        btnPlus.setFont(new Font("Helvetica", 1, 60));
        this.btnPlus = btnPlus;

        //out commented because functionality is not implemented and without a function it is senseless
        //panel.add(pnlMove);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(lblExerciseTitle);
        panel.add(this.btnPlus);
        panel.add(this.pnlSpacer);
        panel.add(this.pnlBtnUpDown);

        this.pnlSpacer.setBackground(Color.decode("#F0F8FF"));
        panel.setBackground(Color.decode("#F0F8FF"));

        btnPlus.setFocusable(false);

        return panel;
    }

    public void addNewSet() {
        this.pnlExercise.remove(btnPlus);
        this.pnlExercise.remove(pnlSpacer);
        this.pnlExercise.remove(this.pnlBtnUpDown);

        this.pnlExercise.add(this.getSetPanel(exercise, this.set));
        this.set++;

        this.pnlExercise.add(btnPlus);
        this.pnlExercise.add(pnlSpacer);
        this.pnlExercise.add(pnlBtnUpDown);

        this.pnlExercise.updateUI();
    }

    private JPanel getUpDownButton(String exercise) {
        int img_width, img_height;
        img_width = 30;
        img_height = 30;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.setPreferredSize(new Dimension(30, 80));

        JButton btnUp = new JButton();
        btnUp = ButtonEditor.addImageToButton(btnUp, "resources/img/angle_up.png", img_width, img_height);
        this.btnUp = btnUp;

        JButton btnDown = new JButton();
        btnDown = ButtonEditor.addImageToButton(btnDown, "resources/img/angle_down.png", img_width, img_height);
        this.btnDown = btnDown;

        panel.setPreferredSize(new Dimension(40, 80));
        panel.setMaximumSize(new Dimension(40, 80));

        panel.add(btnUp);
        panel.add(btnDown);
        panel.validate();

        btnUp.setFocusable(false);
        btnDown.setFocusable(false);

        return panel;
    }


    private JPanel getSetPanel(String exercise, int set) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.setPreferredSize(new Dimension(90, 80));
        panel.setMaximumSize(new Dimension(90, 80));

        JLabel lblSet = new JLabel("set " + set);
        lblSet.setPreferredSize(new Dimension(70, 30));
        PlaceholderTextField txtReps = new PlaceholderTextField();
        txtReps.setPreferredSize(new Dimension(70, 20));
        txtReps.setPlaceholder("Reps");
        PlaceholderTextField txtWeight = new PlaceholderTextField();
        txtWeight.setPreferredSize(new Dimension(70, 20));
        txtWeight.setPlaceholder("Weight in kg");

        panel.add(lblSet);

        this.txtFields.put(set, new PlaceholderTextField[]{txtReps, txtWeight});
        panel.add(txtReps);
        panel.add(txtWeight);

        return panel;
    }

    public JPanel getPnlExercise() {
        return pnlExercise;
    }

    public String getExercise() {
        return exercise;
    }

    public JButton getBtnPlus() {
        return btnPlus;
    }

    public JButton getBtnUp() {
        return btnUp;
    }

    public JButton getBtnDown() {
        return btnDown;
    }

    public JPanel getPnlSpacer() {
        return pnlSpacer;
    }

    public JPanel getPnlBtnUpDown() {
        return pnlBtnUpDown;
    }

    public Map<Integer, PlaceholderTextField[]> getTxtFields() {
        return txtFields;
    }
}
