package View;

import Model.Constants;
import Model.DataManger;

import Enum.ExerciseType;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Iterator;

public class StartPanel {
    private JPanel pnlMain = new JPanel();
    private DataManger dataManger;

    TrainingStatsFrame trainingStatsFrame; //Panels. should be generated already before using
    PdfFrame pdfFrame;

    public StartPanel(DataManger dataManger) {
        this.dataManger = dataManger;
    }

    public JPanel createPanel(int width, int height) {
        this.pnlMain.setLayout(null);
        this.pnlMain.setBackground(Color.decode("#ffddc1"));
        this.pnlMain.setSize(width, height);

        this.addComponents();

        return this.pnlMain;
    }


    private void addComponents(){
        JLabel lblHeadline = new JLabel();
        lblHeadline.setBounds(70, 20, 250, 30);
        lblHeadline.setText("Your training manager");
        lblHeadline.setFont(new Font("ITALIC", 2, 25));
        lblHeadline.setVisible(true);

        JPanel pnlExercise = this.getExerciseComponent();

        JButton btnAddTraining = new JButton();
        btnAddTraining.setBounds(30, 200, 310, 50);
        btnAddTraining.setText("Add training stats");
        btnAddTraining.setFont(new Font("Helvetica", 1, 16));
        btnAddTraining.setVisible(true);
        btnAddTraining.addActionListener(e -> {
            trainingStatsFrame = new TrainingStatsFrame();
            trainingStatsFrame.createFrame();
        });

        JButton btnGeneratePdf = new JButton();
        btnGeneratePdf.setBounds(30, 260, 310, 50);
        btnGeneratePdf.setFont(new Font("Helvetica", 1, 16));
        btnGeneratePdf.setText("Generate PDFs");
        btnGeneratePdf.setVisible(true);
        btnGeneratePdf.addActionListener(e -> {
            pdfFrame = new PdfFrame();
            pdfFrame.createFrame();

        });

        JPanel pnlWeight = this.getWeightPanel();


        this.pnlMain.add(lblHeadline);
        this.pnlMain.add(pnlExercise);
        this.pnlMain.add(btnAddTraining);
        this.pnlMain.add(btnGeneratePdf);
        this.pnlMain.add(pnlWeight);
    }

    private JPanel getExerciseComponent(){
        JPanel pnlExercises = new JPanel();
        pnlExercises.setLayout(null);
        pnlExercises.setBounds(30, 80, 350, 110);
        pnlExercises.setBackground(Color.decode("#ffddc1"));

        JComboBox cBExercises = new JComboBox();
        cBExercises.setFont(new Font("Helvetica", 3, 16));
        cBExercises.setBounds(0, 0, 230, 50);
        cBExercises.setVisible(true);
        Iterator iterator = dataManger.getExerciseList().iterator();
        while (iterator.hasNext()) {
            cBExercises.addItem(iterator.next());
        }

        JButton btnEditExercise = new JButton();
        btnEditExercise.setBounds(233, 0, 37, 50);
        btnEditExercise =
                ButtonEditor.addImageToButton(btnEditExercise, Constants.PathEditImage,25,25);

        btnEditExercise.addActionListener(e -> {    //Controller
            String exercise = cBExercises.getSelectedItem().toString();
            String newName = JOptionPane.showInputDialog("Rename " + exercise + " to: ");
            if (newName != null) {
                if (dataManger.changeExerciseName(exercise, newName)) {
                    cBExercises.removeItem(exercise);
                    cBExercises.addItem(newName);
                    cBExercises.setSelectedItem(newName);
                } else {
                    JOptionPane.showMessageDialog(null, newName + " already exists");
                }
            }
        });

        JButton btnDeleteExercise = new JButton();
        btnDeleteExercise.setBounds(273, 0, 37, 50);
        btnDeleteExercise =
                ButtonEditor.addImageToButton(btnDeleteExercise, Constants.PathDeletionImage, 20, 20);

        btnDeleteExercise.addActionListener(e -> {  //Controller
            String exercise = cBExercises.getSelectedItem().toString();
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete " + exercise + "?", "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                dataManger.deleteExercise(exercise);
                cBExercises.removeItem(exercise);
            }
        });


        JButton btnAddExercise = new JButton();
        btnAddExercise.setText("Add exercise");
        btnAddExercise.setBounds(0, 60, 310, 50);
        btnAddExercise.setFont(new Font("Helvetica", 1, 16));
        btnAddExercise.addActionListener(e -> {     //Controller
            String inputExercise = JOptionPane.showInputDialog("New exercise:");
            if (inputExercise == null || inputExercise.equals("")) {
                return;
            }
            if (!dataManger.proveExerciseExists(inputExercise)) {
                cBExercises.addItem(inputExercise);
            }
            dataManger.addNewExercise(inputExercise);
        });

        pnlExercises.add(cBExercises);
        pnlExercises.add(btnEditExercise);
        pnlExercises.add(btnDeleteExercise);
        pnlExercises.add(btnAddExercise);

        return pnlExercises;
    }

    private JPanel getWeightPanel(){
        String dateToday = dataManger.getCurrentDate();

        JPanel pnlWeight = new JPanel();
        pnlWeight.setOpaque(false);
        pnlWeight.setBorder(BorderFactory.createLineBorder(Color.black));
        pnlWeight.setBounds(30, 320, 310, 80);

        GroupLayout groupLayout = new GroupLayout(pnlWeight);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        pnlWeight.setLayout(groupLayout);

        JLabel lblWeight = new JLabel();
        lblWeight.setText("Your Weight");

        JTextField txtWeight = new JTextField();
        txtWeight.setText(String.valueOf(dataManger.getWeight(dataManger.convertToDate(dateToday))));

        JLabel lblKilo = new JLabel();
        lblKilo.setText("kg");

        JTextField txtDate = new JTextField();
        txtDate.setText(dateToday);

        JButton btnSubmitWeight = new JButton();
        btnSubmitWeight.setText("update");
        Dimension dimension = btnSubmitWeight.getPreferredSize();
        dimension.height = 50;
        btnSubmitWeight.setPreferredSize(dimension);
        btnSubmitWeight.setMinimumSize(dimension);
        btnSubmitWeight.addActionListener(e -> {  //Controller
            Date date = dataManger.convertToDate(txtDate.getText());
            if (date == null) {
                JOptionPane.showMessageDialog(null, "Format of date is wrong",
                        "Error: Date", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String entry = txtDate.getText() + "|0|" + txtWeight.getText();
            dataManger.writeExerciseStats(Constants.bodyWeight, entry, date, ExerciseType.BODYWEIGHT);
        });

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(lblWeight)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(txtWeight)
                                        .addComponent(lblKilo)
                                )
                                .addComponent(txtDate)
                        )
                        .addComponent(btnSubmitWeight)

        );

        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblWeight)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(txtWeight)
                                        .addComponent(lblKilo)
                                )
                                .addComponent(txtDate)
                        )
                        .addComponent(btnSubmitWeight)
        );


        pnlWeight.setVisible(true);

        return pnlWeight;
    }

}
