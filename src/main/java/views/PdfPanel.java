package views;

import enums.PdfType;
import models.Constants;
import models.DataManager;
import models.GeneratePdf;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PdfPanel {
    private JPanel pnlPdfGeneration;
    private JTextField txtFrom;
    private JTextField txtTo;
    private JRadioButton rbMorePdfs;
    private JRadioButton rbOnePdf;
    private GeneratePdf generatePdf;
    private DataManager dataManager;
    private List<JCheckBox> cBExercises;
    private JButton btnSubmitButton;


    public PdfPanel() {
        generatePdf = new GeneratePdf();
        dataManager = new DataManager();
        this.cBExercises = new ArrayList<>();
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

        JPanel pnlExercises = new JPanel();
        BoxLayout boxlayout = new BoxLayout(pnlExercises, BoxLayout.Y_AXIS);
        pnlExercises.setLayout(boxlayout);

        JScrollPane sPExercises = new JScrollPane(pnlExercises,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        sPExercises.setLocation(50, 50);
        sPExercises.setSize(150, 300);
        sPExercises.setVisible(true);

        sPExercises.getViewport().revalidate();

        List<String> exercises;
        exercises = this.dataManager.getExerciseList();
        exercises.add(0, Constants.bodyWeight);
        pnlExercises.removeAll();
        for (String exercise : exercises) {
            JCheckBox cb = new JCheckBox();
            cb.setText(exercise);
            cb.setBackground(Color.white);
            cb.setSize(150, 30);
            this.cBExercises.add(cb);
            pnlExercises.add(cb);
        }
        pnlExercises.validate();
        pnlExercises.repaint();

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

        btnSubmit.addActionListener(e -> {
            ArrayList<String> exercises = new ArrayList<>();
            Date from = dataManager.convertToDate(txtFrom.getText());
            Date to = dataManager.convertToDate(txtTo.getText());
            if (from == null || to == null) {
                JOptionPane.showMessageDialog(null, "Format of date is wrong",
                        "Error: Date", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(from);
            txtFrom.setText(calendar.get(Calendar.DAY_OF_MONTH) + "." +
                    (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR));
            calendar.setTime(to);
            txtTo.setText(calendar.get(Calendar.DAY_OF_MONTH) + "." +
                    (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR));

            if (from.after(to)) {
                JOptionPane.showMessageDialog(null, "Your last date has to be after " +
                        "your first");
            } else {
                for (JCheckBox cb : cBExercises) {
                    if (cb.isSelected()) {
                        exercises.add(cb.getText());
                    }
                }
                if (rbOnePdf.isSelected()) {
                    generatePdf.generatePdf(from, to, exercises, PdfType.ONE_PDF_FOR_ALL_EXERCISES);
                } else if (rbMorePdfs.isSelected()) {
                    generatePdf.generatePdf(from, to, exercises, PdfType.ONE_PDF_FOR_EACH_EXERCISE);
                }
            }
        });

        btnSubmit.setVisible(true);

        return btnSubmit;
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

    public List<JCheckBox> getcBExercises() {
        return cBExercises;
    }

    public JButton getBtnSubmitButton() {
        return btnSubmitButton;
    }
}
