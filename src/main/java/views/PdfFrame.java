package views;

import configuration.Constants;

import javax.swing.*;
import java.awt.*;

public class PdfFrame {
    private JFrame frame;
    private PdfPanel pdfPanel;


    public PdfFrame(PdfPanel pdfPanel) {
        this.pdfPanel = pdfPanel;
        this.createFrame(pdfPanel);
    }

    public void createFrame(PdfPanel pdfPanel) {
        this.frame = new JFrame();
        this.frame.setTitle("Generate PDF");
        ImageIcon imageIcon = new ImageIcon("resources/img/logo.png");
        this.frame.setIconImage(imageIcon.getImage());
        this.frame.getContentPane().setBackground(Color.decode(Constants.BackgroundColor));
        this.frame.setLayout(new GridBagLayout());
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setVisible(true);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1;

        this.frame.add(pdfPanel.getPnlPdfGeneration(), gridBagConstraints);


        Dimension minSize = pdfPanel.getPnlPdfGeneration().getMinimumSize();
        minSize.width += this.frame.getInsets().left + this.frame.getInsets().right;
        minSize.height += this.frame.getInsets().top + this.frame.getInsets().bottom;
        this.frame.setMinimumSize(minSize);
        this.frame.pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        pdfPanel.getPnlPdfGeneration().getRootPane().setDefaultButton(pdfPanel.getBtnSubmitButton());
    }
}