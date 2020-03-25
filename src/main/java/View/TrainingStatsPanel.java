package View;

import Model.Constants;
import Model.DataManger;
import Model.ExerciseSet;
import com.sun.javafx.scene.traversal.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TrainingStatsPanel {
    private PlaceholderTextField txtDate;
    private DataManger dataManger;
    private Map<ExerciseSet, PlaceholderTextField[]> txtFields = new HashMap<>();//1 for Reps, 2 for Weight
    private List<String> exerciseOrder; // = new ArrayList<>()
    private Map<String, JPanel> exercisePanels = new HashMap<>();
    private int width = 0, height = 0;

    private JPanel pnlTrainingStats;
    private JPanel contentPanel;
    private JButton btnSubmit;
    
    private InfoBox infoBox;

    public TrainingStatsPanel(DataManger dataManger) {
        this.dataManger = new DataManger();
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
        this.btnSubmit = this.getAddButton();

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
        sPExercises.setVisible(true);


        for (String s : this.exerciseOrder) {
            //add textFields to maps
            ExerciseSet e1 = new ExerciseSet(s, 1);
            this.txtFields.put(e1, new PlaceholderTextField[2]);
            ExerciseSet e2 = new ExerciseSet(s, 2);
            this.txtFields.put(e2, new PlaceholderTextField[2]);

            this.exercisePanels.put(s, this.getExercisePanel(s));
            this.contentPanel.add(this.exercisePanels.get(s));
        }

        this.contentPanel.setBackground(Color.decode("#F0F8FF"));

        this.contentPanel.add(Box.createVerticalGlue());
        return sPExercises;
    }

    private JPanel getExercisePanel(String exercise) {
        JPanel panel = new JPanel();
        JTextField txtSaveSetNumber = new JTextField();
        txtSaveSetNumber.setText("2");
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

        JPanel pnlMove = new JPanel();
        pnlMove.setPreferredSize(new Dimension(30, 30));
        pnlMove.setMaximumSize(new Dimension(30, 30));
        pnlMove.setBackground(Color.decode("#A4A4A4"));

        JPanel pnlSpacer = new JPanel();

        JPanel btnUpDown = this.getUpDownButton(exercise);
        btnUpDown.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JButton btnPlus = new JButton("+");
        btnPlus.setFont(new Font("Helvetica", 1, 60));
        btnPlus.addActionListener(e -> {
            panel.remove(btnPlus);
            panel.remove(pnlSpacer);
            panel.remove(btnUpDown);
            int set = Integer.valueOf(txtSaveSetNumber.getText());
            set++;
            ExerciseSet e1 = new ExerciseSet(exercise, set);
            txtFields.put(e1, new PlaceholderTextField[2]);
            panel.add(getSetPanel(exercise, set));
            txtSaveSetNumber.setText(String.valueOf(set));
            panel.add(btnPlus);
            panel.add(pnlSpacer);
            panel.add(btnUpDown);
            panel.updateUI();
        });

        //out commented because functionality is not implemented and without a function it is senseless
        //panel.add(pnlMove);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(lblExerciseTitle);
        panel.add(this.getSetPanel(exercise, 1));
        panel.add(this.getSetPanel(exercise, 2));
        panel.add(btnPlus);
        panel.add(pnlSpacer);
        panel.add(btnUpDown);

        pnlSpacer.setBackground(Color.decode("#F0F8FF"));
        panel.setBackground(Color.decode("#F0F8FF"));

        btnPlus.setFocusable(false);

        return panel;
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

        JButton btnDown = new JButton();
        btnDown = ButtonEditor.addImageToButton(btnDown, "resources/img/angle_down.png", img_width, img_height);

        btnDown.addActionListener(e -> {
            swapExerciseOrder(exercise, Direction.DOWN);
        });

        btnUp.addActionListener(e -> {
            swapExerciseOrder(exercise, Direction.UP);
        });

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


        PlaceholderTextField[] pTF = new PlaceholderTextField[2];
        pTF[0] = txtReps;
        pTF[1] = txtWeight;

        panel.add(lblSet);
        for (ExerciseSet exerciseSet : this.txtFields.keySet()) {
            if (exerciseSet.getExercise().equals(exercise) && exerciseSet.getSet() == set) {
                this.txtFields.put(exerciseSet, pTF);
                panel.add(this.txtFields.get(exerciseSet)[0]);
                panel.add(this.txtFields.get(exerciseSet)[1]);
                break;
            }
        }
        return panel;
    }

    private void swapExerciseOrder(String exercise, Direction direction) {
        int position = this.exerciseOrder.indexOf(exercise);
        int max = this.exerciseOrder.size() - 1;
        if (direction == Direction.UP) {
            if (position > 0) {
                Collections.swap(this.exerciseOrder, position, position - 1);
            }
        } else if (direction == Direction.DOWN) {
            if (position < max) {
                Collections.swap(this.exerciseOrder, position, position + 1);
            }
        }
        dataManger.changeExerciseOrder(this.exerciseOrder);
        this.refresh();
    }

    private JButton getAddButton() {
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

    private void refresh() {
        this.contentPanel.removeAll();
        for (String e : this.exerciseOrder) {
            this.contentPanel.add(this.exercisePanels.get(e));
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

    public Map<ExerciseSet, PlaceholderTextField[]> getTxtFields() {
        return txtFields;
    }

    public PlaceholderTextField getTxtDate() {
        return txtDate;
    }
}
