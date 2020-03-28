package views;

import configuration.Constants;

import javax.swing.*;
import java.awt.*;

public class PdfFrame {
    private JFrame frame;


    public PdfFrame(PdfPanel pdfPanel) {
        this.createFrame(pdfPanel);
    }

    private void createFrame(PdfPanel pdfPanel) {
        this.frame = this.createFrame(500, 500);
        this.frame.add(pdfPanel.getPnlPdfGeneration());
        pdfPanel.getPnlPdfGeneration().getRootPane().setDefaultButton(pdfPanel.getBtnSubmitButton());
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

        frame.setVisible(true);

        return frame;
    }
}