package views;

import configuration.Constants;
import models.DateManager;
import views.extensions.ButtonEditor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StartPanel {
    private JPanel pnlMain = new JPanel();
    private JLabel lblHeadline;
    private JPanel pnlExercise;
    private JButton btnEditExercise;
    private JButton btnDeleteExercise;
    private JButton btnAddExercise;
    private JButton btnAddTraining;
    private JButton btnGeneratePdf;
    private JButton btnSubmitWeight;
    private JComboBox cBExercises;
    private JTextField txtWeight;
    private JTextField txtDate;
    private JPanel pnlWeight;

    //size of button;
    int maxWidth, maxHeight;
    int prefWidth, prefHeight;

    public StartPanel() {
        this.prefWidth = 500;
        this.prefHeight = 75;
        this.maxWidth = 800;
        this.maxHeight = 110;
    }

    public JPanel createPanel() {
        this.pnlMain.setBackground(Color.decode(Constants.BackgroundColor));


        this.addComponents();

        return this.pnlMain;
    }

    private void addComponents() {
        this.lblHeadline = this.createHeadline();

        this.pnlExercise = this.createExerciseComponent();

        this.btnAddExercise = this.createAddExerciseButton();

        this.btnAddTraining = this.createAddWorkoutButton();

        this.btnGeneratePdf = this.createGeneratePdfButton();

        this.pnlWeight = this.createWeightPanel();

        this.setupLayout();
    }

    private JLabel createHeadline() {
        JLabel lblHeadline = new JLabel();
        lblHeadline.setText("Your training manager");
        lblHeadline.setFont(new Font("ITALIC", 2, 25));
        lblHeadline.setHorizontalAlignment(SwingConstants.CENTER);
        lblHeadline.setVisible(true);

        return lblHeadline;
    }

    private JPanel createExerciseComponent() {

        JPanel pnlExercises = new JPanel();
        pnlExercises.setLayout(new BoxLayout(pnlExercises, BoxLayout.X_AXIS));

        this.cBExercises = new JComboBox();
        this.cBExercises.setFont(new Font("Helvetica", 3, 16));
        this.cBExercises.setVisible(true);


        this.cBExercises.setPreferredSize(new Dimension(this.prefWidth - 50,this.prefHeight));
        this.cBExercises.setMaximumSize(new Dimension(this.maxWidth - 50, this.maxHeight));

        pnlExercises.add(this.cBExercises);

        this.btnEditExercise = new JButton();
        this.btnEditExercise =
                ButtonEditor.addImageToButton(btnEditExercise, Constants.PathEditImage, 25, 25);
        this.btnEditExercise.setPreferredSize(new Dimension(37, this.prefHeight));
        this.btnEditExercise.setMaximumSize(new Dimension(50, this.maxHeight));
        pnlExercises.add(btnEditExercise);


        this.btnDeleteExercise = new JButton();
        this.btnDeleteExercise =
                ButtonEditor.addImageToButton(btnDeleteExercise, Constants.PathDeletionImage, 20, 20);
        this.btnDeleteExercise.setPreferredSize(new Dimension(37, this.prefHeight));
        this.btnDeleteExercise.setMaximumSize(new Dimension(50, this.maxHeight));

        pnlExercises.add(btnDeleteExercise);


        return pnlExercises;
    }

    private JButton createAddWorkoutButton() {
        JButton btnAddTraining = new JButton();
        btnAddTraining.setText("Add training stats");
        btnAddTraining.setFont(new Font("Helvetica", 1, 16));
        btnAddTraining.setVisible(true);

        return btnAddTraining;
    }

    private JButton createAddExerciseButton() {
        JButton btnAddExercise = new JButton();
        btnAddExercise.setText("Add exercise");
        btnAddExercise.setFont(new Font("Helvetica", 1, 16));

        return btnAddExercise;
    }

    private JButton createGeneratePdfButton() {
        JButton btnGeneratePdf = new JButton();
        btnGeneratePdf.setFont(new Font("Helvetica", 1, 16));
        btnGeneratePdf.setText("Generate PDFs");
        btnGeneratePdf.setVisible(true);

        return btnGeneratePdf;
    }

    private JPanel createWeightPanel() {
        String dateToday = DateManager.getCurrentDate();

        JPanel pnlWeight = new JPanel();
        pnlWeight.setOpaque(false);

        GroupLayout groupLayout = new GroupLayout(pnlWeight);
        groupLayout.setAutoCreateGaps(true);
        pnlWeight.setLayout(groupLayout);

        JLabel lblWeight = new JLabel();
        lblWeight.setText("Your Weight");

        this.txtWeight = new JTextField();
        this.txtWeight.setMaximumSize(new Dimension(300,50));

        JLabel lblKilo = new JLabel();
        lblKilo.setText("kg");

        this.txtDate = new JTextField();
        this.txtDate.setText(dateToday);
        this.txtDate.setMaximumSize(new Dimension(300, 50));

        this.btnSubmitWeight = new JButton();
        this.btnSubmitWeight.setText("update");
        this.btnSubmitWeight.setMaximumSize(new Dimension(130, 65));

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
        pnlWeight.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel pnlBorder = new JPanel();
        pnlBorder.setOpaque(false);
        pnlBorder.setLayout(new BoxLayout(pnlBorder, BoxLayout.PAGE_AXIS));
        pnlBorder.setBorder(BorderFactory.createLineBorder(Color.black));
        pnlBorder.add(pnlWeight, BorderLayout.CENTER);

        return pnlBorder;
    }

    private void setupLayout() {
        this.pnlMain.setLayout(new BoxLayout(this.pnlMain, BoxLayout.PAGE_AXIS));

        this.lblHeadline.setPreferredSize(new Dimension(640,90));
        this.lblHeadline.setMaximumSize(new Dimension(500,120));
        this.lblHeadline.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pnlMain.add(this.lblHeadline);


        this.pnlExercise.setPreferredSize(new Dimension(this.prefWidth, this.prefHeight));
        this.pnlExercise.setMaximumSize(new Dimension(this.maxWidth,this.maxHeight));
        this.pnlExercise.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pnlMain.add(this.pnlExercise);

        JPanel pnlSpacer1 = new JPanel();
        pnlSpacer1.setMinimumSize(new Dimension(0, 0));
        pnlSpacer1.setPreferredSize(new Dimension(0, 10));
        pnlSpacer1.setMaximumSize(new Dimension(0, 13));
        this.pnlMain.add(pnlSpacer1);

        this.btnAddExercise.setPreferredSize(new Dimension(this.prefWidth, this.prefHeight));
        this.btnAddExercise.setMaximumSize(new Dimension(this.maxWidth,this.maxHeight));
        this.btnAddExercise.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pnlMain.add(this.btnAddExercise);

        JPanel pnlSpacer2 = new JPanel();
        pnlSpacer2.setMinimumSize(new Dimension(0, 0));
        pnlSpacer2.setPreferredSize(new Dimension(0, 10));
        pnlSpacer2.setMaximumSize(new Dimension(0, 13));
        this.pnlMain.add(pnlSpacer2);


        this.btnAddTraining.setPreferredSize(new Dimension(this.prefWidth, this.prefHeight));
        this.btnAddTraining.setMaximumSize(new Dimension(this.maxWidth,this.maxHeight));
        this.btnAddTraining.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pnlMain.add(this.btnAddTraining);

        JPanel pnlSpacer3 = new JPanel();
        pnlSpacer3.setMinimumSize(new Dimension(0, 0));
        pnlSpacer3.setPreferredSize(new Dimension(0, 10));
        pnlSpacer3.setMaximumSize(new Dimension(0, 13));
        this.pnlMain.add(pnlSpacer3);

        this.btnGeneratePdf.setPreferredSize(new Dimension(this.prefWidth, this.prefHeight));
        this.btnGeneratePdf.setMaximumSize(new Dimension(this.maxWidth,this.maxHeight));
        this.btnGeneratePdf.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pnlMain.add(this.btnGeneratePdf);

        JPanel pnlSpacer4 = new JPanel();
        pnlSpacer4.setMinimumSize(new Dimension(0, 0));
        pnlSpacer4.setPreferredSize(new Dimension(0, 10));
        pnlSpacer4.setMaximumSize(new Dimension(0, 13));
        this.pnlMain.add(pnlSpacer4);


        this.pnlWeight.setPreferredSize(new Dimension(this.prefWidth, this.prefHeight));
        this.pnlWeight.setMaximumSize(new Dimension(this.maxWidth, this.pnlWeight.getMaximumSize().height));
        this.pnlWeight.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pnlMain.add(this.pnlWeight);

        JPanel pnlSpacer5 = new JPanel();
        pnlSpacer5.setMinimumSize(new Dimension(0, 0));
        pnlSpacer5.setPreferredSize(new Dimension(0, 50));
        pnlSpacer5.setMaximumSize(new Dimension(0, 75));
        this.pnlMain.add(pnlSpacer5);

    }


    public void refresh(List<String> exercises) {
        int index = this.cBExercises.getSelectedIndex();
        this.cBExercises.removeAllItems();
        for (String exercise : exercises) {
            this.cBExercises.addItem(exercise);
        }
        if (index < 0) {
            index = 0;
        }
        if (index < this.cBExercises.getItemCount()) {
            this.cBExercises.setSelectedIndex(index);
        }
    }

    public JPanel getPnlMain() {
        return pnlMain;
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
