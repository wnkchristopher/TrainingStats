package views;

import configuration.Constants;
import models.DateManager;
import views.extensions.ButtonEditor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StartPanel {
    private JPanel pnlMain = new JPanel();
    private JButton btnEditExercise;
    private JButton btnDeleteExercise;
    private JButton btnAddExercise;
    private JButton btnAddTraining;
    private JButton btnGeneratePdf;
    private JButton btnSubmitWeight;
    private JComboBox cBExercises;
    private JTextField txtWeight;
    private JTextField txtDate;

    public StartPanel() {

    }

    public JPanel createPanel(int width, int height) {
        this.pnlMain.setLayout(null);
        this.pnlMain.setBackground(Color.decode(Constants.BackgroundColor));
        this.pnlMain.setSize(width, height);

        this.addComponents();

        return this.pnlMain;
    }

    private void addComponents() {
        JLabel lblHeadline = new JLabel();
        lblHeadline.setBounds(70, 20, 250, 30);
        lblHeadline.setText("Your training manager");
        lblHeadline.setFont(new Font("ITALIC", 2, 25));
        lblHeadline.setVisible(true);

        JPanel pnlExercise = this.getExerciseComponent();

        this.btnAddTraining = new JButton();
        this.btnAddTraining.setBounds(30, 200, 310, 50);
        this.btnAddTraining.setText("Add training stats");
        this.btnAddTraining.setFont(new Font("Helvetica", 1, 16));
        this.btnAddTraining.setVisible(true);


        this.btnGeneratePdf = new JButton();
        this.btnGeneratePdf.setBounds(30, 260, 310, 50);
        this.btnGeneratePdf.setFont(new Font("Helvetica", 1, 16));
        this.btnGeneratePdf.setText("Generate PDFs");
        this.btnGeneratePdf.setVisible(true);

        JPanel pnlWeight = this.getWeightPanel();


        this.pnlMain.add(lblHeadline);
        this.pnlMain.add(pnlExercise);
        this.pnlMain.add(btnAddTraining);
        this.pnlMain.add(btnGeneratePdf);
        this.pnlMain.add(pnlWeight);
    }

    private JPanel getExerciseComponent() {
        JPanel pnlExercises = new JPanel();
        pnlExercises.setLayout(null);
        pnlExercises.setBounds(30, 80, 350, 110);
        pnlExercises.setBackground(Color.decode(Constants.BackgroundColor));

        this.cBExercises = new JComboBox();
        this.cBExercises.setFont(new Font("Helvetica", 3, 16));
        this.cBExercises.setBounds(0, 0, 230, 50);
        this.cBExercises.setVisible(true);

        this.btnEditExercise = new JButton();
        this.btnEditExercise.setBounds(233, 0, 37, 50);
        this.btnEditExercise =
                ButtonEditor.addImageToButton(btnEditExercise, Constants.PathEditImage, 25, 25);

        this.btnDeleteExercise = new JButton();
        this.btnDeleteExercise.setBounds(273, 0, 37, 50);
        this.btnDeleteExercise =
                ButtonEditor.addImageToButton(btnDeleteExercise, Constants.PathDeletionImage, 20, 20);


        this.btnAddExercise = new JButton();
        this.btnAddExercise.setText("Add exercise");
        this.btnAddExercise.setBounds(0, 60, 310, 50);
        this.btnAddExercise.setFont(new Font("Helvetica", 1, 16));

        pnlExercises.add(cBExercises);
        pnlExercises.add(btnEditExercise);
        pnlExercises.add(btnDeleteExercise);
        pnlExercises.add(btnAddExercise);

        return pnlExercises;
    }

    private JPanel getWeightPanel() {
        String dateToday = DateManager.getCurrentDate();

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

        this.txtWeight = new JTextField();
        //this.txtWeight.setText(String.valueOf(dataManager.getWeight(DateManager.convertStringToDate(dateToday))));

        JLabel lblKilo = new JLabel();
        lblKilo.setText("kg");

        this.txtDate = new JTextField();
        this.txtDate.setText(dateToday);

        this.btnSubmitWeight = new JButton();
        this.btnSubmitWeight.setText("update");
        Dimension dimension = btnSubmitWeight.getPreferredSize();
        dimension.height = 50;
        this.btnSubmitWeight.setPreferredSize(dimension);
        this.btnSubmitWeight.setMinimumSize(dimension);

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

    public void refresh(List<String> exercises) {
        int index = this.cBExercises.getSelectedIndex();
        this.cBExercises.removeAllItems();
        for(String exercise :  exercises){
            this.cBExercises.addItem(exercise);
        }
        if(index < 0){
            index = 0;
        }
        if(index < this.cBExercises.getItemCount()){
            this.cBExercises.setSelectedIndex(index);
        }
    }

    public JButton getBtnEditExercise() {
        return btnEditExercise;
    }

    public JButton getBtnDeleteExercise() {
        return btnDeleteExercise;
    }

    public JButton getBtnAddExercise() {
        return btnAddExercise;
    }

    public JButton getBtnAddTraining() {
        return btnAddTraining;
    }

    public JButton getBtnGeneratePdf() {
        return btnGeneratePdf;
    }

    public JButton getBtnSubmitWeight() {
        return btnSubmitWeight;
    }

    public JComboBox getcBExercises() {
        return cBExercises;
    }

    public JTextField getTxtWeight() {
        return txtWeight;
    }

    public JTextField getTxtDate() {
        return txtDate;
    }
}
