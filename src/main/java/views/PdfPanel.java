package views;

import configuration.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PdfPanel {
    private JPanel pnlPdfGeneration;
    private JLabel lblHeadline;
    private JTextField txtFrom;
    private JTextField txtTo;
    private JRadioButton rbMorePdfs;
    private JRadioButton rbOnePdf;
    private Map<String, JCheckBox> cBExercises;
    private JButton btnSubmitButton;
    private JPanel pnlExercises;
    private JPanel pnlDateTextFields;
    private JPanel pnlToggleButtons;
    private JScrollPane spExercises;

    public PdfPanel() {
        this.cBExercises = new TreeMap<>();
        this.pnlPdfGeneration = this.createPanel();
    }

    private JPanel createPanel() {
        this.pnlPdfGeneration = new JPanel();
        this.pnlPdfGeneration.setBackground(Color.decode(Constants.BackgroundColor));

        this.pnlPdfGeneration = this.addComponents(pnlPdfGeneration);

        return pnlPdfGeneration;
    }

    private JPanel addComponents(JPanel pnlPdfGeneration) {
        this.lblHeadline = this.getHeadline();
        this.spExercises = this.getExerciseScrollPane();
        this.pnlDateTextFields = this.getPnlDateTextFields();
        this.pnlToggleButtons = this.getPnlToggleButtons();
        this.btnSubmitButton = this.getSubmitButton();

        this.setupLayout();

        return pnlPdfGeneration;
    }

    private JLabel getHeadline() {
        JLabel label = new JLabel();
        label.setText("Generate pdf");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("ITALIC", 2, 27));
        label.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        label.setVisible(true);

        return label;
    }

    private JScrollPane getExerciseScrollPane() {

        this.pnlExercises = new JPanel();
        BoxLayout boxlayout = new BoxLayout(this.pnlExercises, BoxLayout.Y_AXIS);
        this.pnlExercises.setLayout(boxlayout);

        JScrollPane sPExercises = new JScrollPane(this.pnlExercises,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        sPExercises.setVisible(true);

        sPExercises.getViewport().revalidate();

        this.addExercise(Constants.bodyWeight);

        pnlExercises.setBackground(Color.white);
        pnlExercises.setVisible(true);

        pnlExercises.setLocation(50, 50);
        pnlExercises.setSize(150, 300);
        pnlExercises.setVisible(true);


        return sPExercises;
    }

    private JPanel getPnlDateTextFields() {
        JPanel pnlGroupFromTo = new JPanel();
        pnlGroupFromTo.setLayout(new GridBagLayout());
        pnlGroupFromTo.setOpaque(false);

        GridBagConstraints constraints;


        JLabel lblFrom = new JLabel("From: (dd.mm.yyyy)");
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(0, 20, 10, 0);
        pnlGroupFromTo.add(lblFrom, constraints);

        this.txtFrom = new JTextField();
        this.txtFrom.setPreferredSize(new Dimension(200,30));
        this.txtFrom.setMinimumSize(this.txtFrom.getPreferredSize());
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 20, 20, 0);
        pnlGroupFromTo.add(this.txtFrom, constraints);


        JLabel lblTo = new JLabel("To: (dd.mm.yyyy)");
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(20, 20, 10, 0);
        constraints.anchor = GridBagConstraints.WEST;
        pnlGroupFromTo.add(lblTo, constraints);


        this.txtTo = new JTextField();
        this.txtTo.setPreferredSize(new Dimension(200,30));
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(10, 20, 10, 0);
        this.txtTo.setMinimumSize(this.txtTo.getPreferredSize());
        pnlGroupFromTo.add(this.txtTo, constraints);


        pnlGroupFromTo.setVisible(true);
        return pnlGroupFromTo;
    }

    private JButton getSubmitButton() {
        JButton btnSubmit = new JButton("Generate PDF");
        btnSubmit.setVisible(true);

        return btnSubmit;
    }


    private JPanel getPnlToggleButtons() {
        JPanel pnlToggleButtons = new JPanel();
        pnlToggleButtons.setLayout(new BoxLayout(pnlToggleButtons, BoxLayout.Y_AXIS));
        pnlToggleButtons.setOpaque(false);

        this.rbMorePdfs = new JRadioButton("One PDF for each selected exercise");
        this.rbMorePdfs.setOpaque(false);
        this.rbMorePdfs.setVisible(true);
        this.rbMorePdfs.setSelected(true);

        this.rbOnePdf = new JRadioButton("One PDF for all selected exercises");
        this.rbOnePdf.setOpaque(false);
        this.rbOnePdf.setVisible(true);

        ButtonGroup bgPdf = new ButtonGroup();
        bgPdf.add(this.rbMorePdfs);
        bgPdf.add(this.rbOnePdf);

        pnlToggleButtons.add(rbMorePdfs);
        pnlToggleButtons.add(rbOnePdf);
        return pnlToggleButtons;
    }


    public void addExercise(String exercise) {
        JCheckBox cb = new JCheckBox();
        cb.setText(exercise);
        cb.setBackground(Color.white);
        cb.setSize(150, 30);
        this.cBExercises.put(exercise, cb);
        this.pnlExercises.add(cb);
    }

    private void setupLayout() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.pnlPdfGeneration.setLayout(gridBagLayout);
        GridBagConstraints constraints;

        constraints = new GridBagConstraints();
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(10, 0, 20, 0);
        this.pnlPdfGeneration.add(this.lblHeadline, constraints);

        constraints = new GridBagConstraints();
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,60,0,10);
        this.spExercises.setMinimumSize(new Dimension(200, 200));
        this.spExercises.setPreferredSize(new Dimension(200, 400));
        this.pnlPdfGeneration.add(this.spExercises, constraints);

        constraints = new GridBagConstraints();
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,0,0,60);
        this.pnlPdfGeneration.add(this.pnlDateTextFields, constraints);

        constraints = new GridBagConstraints();
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0,10,5,20);
        this.pnlPdfGeneration.add(this.pnlToggleButtons, constraints);

        constraints = new GridBagConstraints();
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(20,0,20,60);
        this.btnSubmitButton.setPreferredSize(new Dimension(140,40));
        this.pnlPdfGeneration.add(this.btnSubmitButton, constraints);
    }

    public void refresh(List<String> exercises) {
        this.pnlExercises.removeAll();
        this.addExercise(Constants.bodyWeight);
        for (String exercise : exercises) {
            this.pnlExercises.add(this.cBExercises.get(exercise));
        }
        pnlExercises.validate();
        pnlExercises.repaint();
    }

    public JPanel getPnlPdfGeneration() {
        return pnlPdfGeneration;
    }

    public JTextField getTxtFrom() {
        return txtFrom;
    }

    public JTextField getTxtTo() {
        return txtTo;
    }

    public JRadioButton getRbMorePdfs() {
        return rbMorePdfs;
    }

    public JRadioButton getRbOnePdf() {
        return rbOnePdf;
    }

    public Map<String, JCheckBox> getcBExercises() {
        return cBExercises;
    }

    public JButton getBtnSubmitButton() {
        return btnSubmitButton;
    }

}
