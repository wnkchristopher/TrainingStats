package views;

import models.Constants;
import models.DataManager;
import models.GeneratePdf;
import enums.PdfType;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PdfFrame {
    private JFrame frame;
    private JTextField txtFrom;
    private JTextField txtTo;
    private JRadioButton rbMorePdfs;
    private JRadioButton rbOnePdf;
    private JScrollPane sPExercises;
    private GeneratePdf generatePdf;
    private DataManager dataManager;
    private List<JCheckBox> cBExercises;
    private JButton btnGeneratePdf;
    private JPanel pnlExercises;


    public PdfFrame() {
        generatePdf = new GeneratePdf();
        dataManager = new DataManager();
        this.createFrame();
    }

    public void createFrame() {
        this.frame = this.createFrame(500, 500);
        PdfPanel pdfPanel = new PdfPanel();
        this.frame.add(pdfPanel.getPnlPdfGeneration());
    }

    private JFrame createFrame(int width, int height) {
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.getContentPane().setBackground(Color.decode(Constants.BackgroundColor));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Generate PDF");

        ImageIcon imageIcon = new ImageIcon("resources/img/logo.png");
        frame.setIconImage(imageIcon.getImage());

       // frame.getRootPane().setDefaultButton(this.btnGeneratePdf);

       // frame.repaint();
        frame.setVisible(true);

        return frame;
    }
}