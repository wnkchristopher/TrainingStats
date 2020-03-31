package views;

import configuration.Constants;
import views.extensions.ButtonEditor;

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
    private JButton btnSubmit;
    private JPanel pnlExercises;
    private JLabel lblFrom, lblTo;
    private JPanel pnlToggleButtons;
    private JScrollPane spExercises;
    private JButton btnBack;

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
        this.btnBack = this.getBackButton();
        this.lblHeadline = this.getHeadline();
        this.spExercises = this.getExerciseScrollPane();
        this.setDateTextFields();
        this.pnlToggleButtons = this.getPnlToggleButtons();
        this.btnSubmit = this.getSubmitButton();

        this.setupLayout();

        return pnlPdfGeneration;
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


        return sPExercises;
    }

    private void setDateTextFields() {
        this.lblFrom = new JLabel("From: (dd.mm.yyyy)");
        this.txtFrom = new JTextField();

        this.lblTo = new JLabel("To: (dd.mm.yyyy)");
        this.txtTo = new JTextField();
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
        this.cBExercises.put(exercise, cb);
        this.pnlExercises.add(cb);
    }

    private void setupLayout() {

        this.pnlPdfGeneration.setLayout(new BoxLayout(this.pnlPdfGeneration, BoxLayout.Y_AXIS));

        JPanel pnlTop = new JPanel();
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.X_AXIS));
        pnlTop.setOpaque(false);

        JPanel pnlBack = new JPanel();
        pnlBack.setLayout(new BoxLayout(pnlBack, BoxLayout.Y_AXIS));
        pnlBack.setPreferredSize(new Dimension(40, 100));
        pnlBack.setMaximumSize(new Dimension(40, 100));
        pnlBack.setOpaque(false);

        this.btnBack.setMinimumSize(new Dimension(40,30));
        this.btnBack.setPreferredSize(new Dimension(40,30));
        this.btnBack.setMaximumSize(new Dimension(40,30));

        pnlBack.add(Box.createVerticalStrut(10));
        pnlBack.add(this.btnBack);
        pnlBack.add(Box.createVerticalGlue());
        pnlBack.setAlignmentX(Component.LEFT_ALIGNMENT);


        this.lblHeadline.setPreferredSize(new Dimension(300,100));
        this.lblHeadline.setMaximumSize(new Dimension(300,100));
        this.lblHeadline.setAlignmentX(Component.CENTER_ALIGNMENT);


        pnlTop.add(Box.createHorizontalStrut(10));
        pnlTop.add(pnlBack);
        pnlTop.add(Box.createHorizontalGlue());
        pnlTop.add(this.lblHeadline);
        pnlTop.add(Box.createHorizontalGlue());
        pnlTop.add(Box.createHorizontalStrut(10));

        pnlTop.setMaximumSize(new Dimension(pnlTop.getMaximumSize().width, 100));

        this.pnlPdfGeneration.add(pnlTop);

        JPanel pnlCenter = new JPanel();
        pnlCenter.setBackground(Color.red);
        pnlCenter.setOpaque(false);
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.X_AXIS));

        JPanel pnlSpacer1 = this.getSpacer
                (new Dimension(0,0), new Dimension(50, 0), new Dimension(70,0));
        pnlCenter.add(pnlSpacer1);

        pnlCenter.add(pnlSpacer1);

        this.spExercises.setMinimumSize(new Dimension(150,100));
        this.spExercises.setPreferredSize(new Dimension(250,500));
        this.spExercises.setMaximumSize(new Dimension(600,1000));
        pnlCenter.add(this.spExercises);

        JPanel pnlSpacer2 = this.getSpacer
                (new Dimension(0,0), new Dimension(20, 0), new Dimension(30,0));
        pnlCenter.add(pnlSpacer2);


        JPanel pnlRight = new JPanel();
        pnlRight.setOpaque(false);
        pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));

        this.lblFrom.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlRight.add(this.lblFrom);

        this.txtFrom.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.txtFrom.setMinimumSize(new Dimension(70,30));
        this.txtFrom.setPreferredSize(new Dimension(300,40));
        this.txtFrom.setMaximumSize(new Dimension(300,40));

        pnlRight.add(this.txtFrom);

        JPanel pnlSpacer3 = this.getSpacer
                (new Dimension(0,0), new Dimension(0, 30), new Dimension(0, 50));
        pnlRight.add(pnlSpacer3);

        this.lblTo.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlRight.add(this.lblTo);
        this.txtTo.setMinimumSize(new Dimension(70,30));
        this.txtTo.setPreferredSize(new Dimension(300,40));
        this.txtTo.setMaximumSize(new Dimension(300,40));
        this.txtTo.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlRight.add(this.txtTo);

        JPanel pnlSpacer4 = this.getSpacer
                (new Dimension(0,0), new Dimension(0, 40), new Dimension(0, 40));
        pnlRight.add(pnlSpacer4);

        this.pnlToggleButtons.setMinimumSize(new Dimension(70, 60));
        this.pnlToggleButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlRight.add(this.pnlToggleButtons);

        JPanel pnlSpacer5 = this.getSpacer
                (new Dimension(0,0), new Dimension(0, 15), new Dimension(0, 30));
        pnlRight.add(pnlSpacer5);

        this.btnSubmit.setMinimumSize(new Dimension(60,20));
        this.btnSubmit.setPreferredSize(new Dimension(180, 50));
        this.btnSubmit.setMaximumSize(new Dimension(230, 80));
        this.btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlRight.add(this.btnSubmit);

      //  pnlRight.setMinimumSize(new Dimension(100,300));
        pnlRight.setPreferredSize(new Dimension(400,500));
        pnlRight.setMaximumSize(new Dimension(600,1000));

        pnlRight.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlRight.setOpaque(false);
        pnlCenter.add(pnlRight);

        JPanel pnlSpacer6 = this.getSpacer
                (new Dimension(0,0), new Dimension(50, 0), new Dimension(70,0));
        pnlCenter.add(pnlSpacer6);


        this.pnlPdfGeneration.add(pnlCenter);

        JPanel pnlSpacerBottom = this.getSpacer
                (new Dimension(0,0), new Dimension(0, 30), new Dimension(0, 70));
        this.pnlPdfGeneration.add(pnlSpacerBottom);

    }

    private JPanel getSpacer(Dimension minSize, Dimension prefSize, Dimension maxSize) {
        JPanel pnlSpacer = new JPanel();
        pnlSpacer.setOpaque(false);
        pnlSpacer.setMinimumSize(minSize);
        pnlSpacer.setPreferredSize(prefSize);
        pnlSpacer.setMaximumSize(maxSize);

        return pnlSpacer;
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

    public JButton getBtnSubmit() {
        return btnSubmit;
    }

    public JButton getBtnBack() {
        return btnBack;
    }
}
