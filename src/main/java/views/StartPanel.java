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

    public StartPanel() {

    }

    public JPanel createPanel() {
        this.pnlMain.setBackground(Color.decode(Constants.BackgroundColor));


        this.addComponents();

        return this.pnlMain;
    }

    private void addComponents() {
        this.lblHeadline = this.createHeadline();

        this.pnlExercise = this.createExerciseComponent();

        this.btnAddTraining = this.createAddWorkoutButton();

        this.btnGeneratePdf = this.createGeneratePdfButton();

        this.pnlWeight = this.createWeightPanel();

        this.setupLayout();
    }

    private JLabel createHeadline() {
        JLabel lblHeadline = new JLabel();
       // lblHeadline.setBounds(70, 20, 250, 30);
        lblHeadline.setText("Your training manager");
        lblHeadline.setFont(new Font("ITALIC", 2, 25));
        lblHeadline.setHorizontalAlignment(SwingConstants.CENTER);
        lblHeadline.setVisible(true);

        return lblHeadline;
    }

    private JPanel createExerciseComponent() {
        GridBagLayout gridBagLayout = new GridBagLayout();

        JPanel pnlExercises = new JPanel();
      //  pnlExercises.setBounds(30, 80, 310, 110);
        pnlExercises.setLayout(gridBagLayout);
        pnlExercises.setBackground(Color.decode(Constants.BackgroundColor));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, 0, 10, 3);

        this.cBExercises = new JComboBox();
        this.cBExercises.setFont(new Font("Helvetica", 3, 16));
        // this.cBExercises.setBounds(0, 0, 230, 50);
        this.cBExercises.setVisible(true);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;

        Dimension dimension = this.cBExercises.getPreferredSize();
        dimension.height = 50;
        dimension.width = 230;
        this.cBExercises.setPreferredSize(dimension);
        this.cBExercises.setMinimumSize(dimension);

        pnlExercises.add(cBExercises, gridBagConstraints);

        this.btnEditExercise = new JButton();
        //this.btnEditExercise.setBounds(233, 0, 37, 50);
        this.btnEditExercise =
                ButtonEditor.addImageToButton(btnEditExercise, Constants.PathEditImage, 25, 25);
        this.btnEditExercise.setPreferredSize(new Dimension(37, 50));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        pnlExercises.add(btnEditExercise, gridBagConstraints);


        this.btnDeleteExercise = new JButton();
        //this.btnDeleteExercise.setBounds(273, 0, 37, 50);
        this.btnDeleteExercise =
                ButtonEditor.addImageToButton(btnDeleteExercise, Constants.PathDeletionImage, 20, 20);
        this.btnDeleteExercise.setPreferredSize(new Dimension(37, 50));

        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        pnlExercises.add(btnDeleteExercise, gridBagConstraints);

        this.btnAddExercise = new JButton();
        this.btnAddExercise.setText("Add exercise");
        //this.btnAddExercise.setBounds(0, 60, 310, 50);
        this.btnAddExercise.setFont(new Font("Helvetica", 1, 16));
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 1;

        dimension = this.btnAddExercise.getPreferredSize();
        dimension.height = 50;
        //dimension.width = 310;
        this.btnAddExercise.setMinimumSize(dimension);
        this.btnAddExercise.setPreferredSize(dimension);

        pnlExercises.add(btnAddExercise, gridBagConstraints);

        return pnlExercises;
    }

    private JButton createAddWorkoutButton() {
        JButton btnAddTraining = new JButton();
    //    btnAddTraining.setBounds(30, 200, 310, 50);
        btnAddTraining.setText("Add training stats");
        btnAddTraining.setFont(new Font("Helvetica", 1, 16));
        btnAddTraining.setVisible(true);

        return btnAddTraining;
    }

    private JButton createGeneratePdfButton() {
        JButton btnGeneratePdf = new JButton();
      //  btnGeneratePdf.setBounds(30, 260, 310, 50);
        btnGeneratePdf.setFont(new Font("Helvetica", 1, 16));
        btnGeneratePdf.setText("Generate PDFs");
        btnGeneratePdf.setVisible(true);

        return btnGeneratePdf;
    }

    private JPanel createWeightPanel() {
        String dateToday = DateManager.getCurrentDate();

        JPanel pnlWeight = new JPanel();
        pnlWeight.setOpaque(false);
        pnlWeight.setBorder(BorderFactory.createLineBorder(Color.black));
     //   pnlWeight.setBounds(30, 320, 310, 80);

        GroupLayout groupLayout = new GroupLayout(pnlWeight);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        pnlWeight.setLayout(groupLayout);

        JLabel lblWeight = new JLabel();
        lblWeight.setText("Your Weight");

        this.txtWeight = new JTextField();

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
        this.btnSubmitWeight.setPreferredSize(dimension);

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

    private void setupLayout() {
        Dimension dimension;
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.pnlMain.setLayout(gridBagLayout);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(30, 10, 25, 10);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;

        this.pnlMain.add(this.lblHeadline, gridBagConstraints);

        gridBagConstraints.insets = new Insets(5, 10, 5, 10);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        this.pnlMain.add(this.pnlExercise, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        dimension = this.btnAddTraining.getPreferredSize();
        dimension.height = 50;
        this.btnAddTraining.setMinimumSize(dimension);
        this.btnAddTraining.setPreferredSize(dimension);
        this.pnlMain.add(this.btnAddTraining, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        dimension = this.btnGeneratePdf.getPreferredSize();
        dimension.height = 50;
        this.btnGeneratePdf.setMinimumSize(dimension);
        this.btnGeneratePdf.setPreferredSize(dimension);
        this.pnlMain.add(this.btnGeneratePdf, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        this.pnlMain.add(this.pnlWeight, gridBagConstraints);

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
