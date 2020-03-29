package views;

import configuration.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PdfPanel {
    private JPanel pnlPdfGeneration;
    private JTextField txtFrom;
    private JTextField txtTo;
    private JRadioButton rbMorePdfs;
    private JRadioButton rbOnePdf;
    private Map<String, JCheckBox> cBExercises;
    private JButton btnSubmitButton;
    private JPanel pnlExercises;


    public PdfPanel() {
        this.cBExercises = new TreeMap<>();
        this.pnlPdfGeneration = this.createPanel(500, 500);
    }

    private JPanel createPanel(int width, int height) {
        JPanel pnlPdfGeneration = new JPanel();
        pnlPdfGeneration.setLayout(null);
        pnlPdfGeneration.setBackground(Color.decode(Constants.BackgroundColor));
        pnlPdfGeneration.setSize(width, height);

        pnlPdfGeneration = this.addComponents(pnlPdfGeneration);

        return pnlPdfGeneration;
    }

    private JPanel addComponents(JPanel pnlPdfGeneration) {
        JScrollPane sPExercises = this.getExerciseScrollPane();
        JPanel pnlToggleButtons = this.getPnlToggleButtons();
        JPanel pnlDateTextFields = this.getPnlDateTextFields();
        this.btnSubmitButton = this.getSubmitButton();

        pnlPdfGeneration.add(sPExercises);
        pnlPdfGeneration.add(pnlToggleButtons);
        pnlPdfGeneration.add(pnlDateTextFields);
        pnlPdfGeneration.add(this.btnSubmitButton);

        return pnlPdfGeneration;
    }

    private JScrollPane getExerciseScrollPane() {

        this.pnlExercises = new JPanel();
        BoxLayout boxlayout = new BoxLayout(this.pnlExercises, BoxLayout.Y_AXIS);
        this.pnlExercises.setLayout(boxlayout);

        JScrollPane sPExercises = new JScrollPane(this.pnlExercises,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        sPExercises.setLocation(50, 50);
        sPExercises.setSize(150, 300);
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

    private JPanel getPnlToggleButtons() {
        JPanel pnlToggleButtons = new JPanel();
        pnlToggleButtons.setLayout(null);
        pnlToggleButtons.setBounds(50, 370, 240, 40);
        pnlToggleButtons.setOpaque(false);

        this.rbMorePdfs = new JRadioButton("One PDF for each selected exercise");
        this.rbOnePdf = new JRadioButton("One PDF for all selected exercises");
        ButtonGroup bgPdf = new ButtonGroup();
        this.rbMorePdfs.setBounds(0, 0, 240, 20);
        this.rbOnePdf.setBounds(0, 20, 240, 20);
        bgPdf.add(this.rbMorePdfs);
        bgPdf.add(this.rbOnePdf);
        this.rbMorePdfs.setSelected(true);

        this.rbMorePdfs.setOpaque(false);
        rbMorePdfs.setVisible(true);
        this.rbOnePdf.setOpaque(false);
        rbOnePdf.setVisible(true);
        pnlToggleButtons.add(rbMorePdfs);
        pnlToggleButtons.add(rbOnePdf);
        return pnlToggleButtons;
    }

    private JPanel getPnlDateTextFields() {
        JPanel pnlGroupFromTo = new JPanel();

        JLabel lblFrom = new JLabel("From: (dd.mm.yyyy)");
        txtFrom = new JTextField();

        JLabel lblTo = new JLabel("To: (dd.mm.yyyy)");
        txtTo = new JTextField();

        pnlGroupFromTo.setLayout(null);

        lblFrom.setBounds(0, 0, 200, 30);
        txtFrom.setBounds(0, 50, 200, 30);

        lblTo.setBounds(0, 90, 200, 30);
        txtTo.setBounds(0, 130, 200, 30);

        pnlGroupFromTo.setBounds(250, 50, 200, 200);

        pnlGroupFromTo.setBackground(Color.decode(Constants.BackgroundColor));

        pnlGroupFromTo.add(lblFrom);
        pnlGroupFromTo.add(txtFrom);
        pnlGroupFromTo.add(lblTo);
        pnlGroupFromTo.add(txtTo);
        pnlGroupFromTo.setVisible(true);

        return pnlGroupFromTo;
    }

    private JButton getSubmitButton() {
        JButton btnSubmit = new JButton("Generate PDF");

        btnSubmit.setBounds(275, 250, 150, 40);

        btnSubmit.setVisible(true);

        return btnSubmit;
    }

    public void addExercise(String exercise) {
        JCheckBox cb = new JCheckBox();
        cb.setText(exercise);
        cb.setBackground(Color.white);
        cb.setSize(150, 30);
        this.cBExercises.put(exercise, cb);
        this.pnlExercises.add(cb);
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
