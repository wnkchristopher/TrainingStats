package views;

import controller.InfoBoxController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoBox {
    private JPanel pnlInfo;
    private JLabel lblInfo;
    private JLabel lblText;
    private ImageIcon imageHelp;
    private ImageIcon imageHelpHover;

    public InfoBox(String text) {
        this.pnlInfo = this.createInfoBox(text, 300,90);
        InfoBoxController infoBoxController = new InfoBoxController(this);
    }

    private JPanel createInfoBox(String text, int width, int height){
        this.loadImages();

        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(null);
        pnlInfo.setSize(width, height);
        pnlInfo.setOpaque(false);

        this.lblText = this.getTextLabel(text);
        this.lblInfo = this.getImageLabel(width, lblText);

        pnlInfo.add(this.lblInfo);
        pnlInfo.add(this.lblText);

        return pnlInfo;
    }

    private void loadImages() {
        ImageIcon tmpImageHelp = new ImageIcon("resources/img/help_small.png");
        Image image = tmpImageHelp.getImage(); // transform it
        Image newImg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        this.imageHelp = new ImageIcon(newImg);

        ImageIcon tmpImageHelpHover = new ImageIcon("resources/img/help_hover_small.png");
        image = tmpImageHelpHover.getImage(); // transform it
        newImg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        this.imageHelpHover = new ImageIcon(newImg);
    }

    private JLabel getImageLabel(int width, JLabel lblText){
        JLabel lblInfo = new JLabel();
        lblInfo.setIcon(this.imageHelp);
        lblInfo.setBounds(270, 10, width, 30);

        return lblInfo;
    }

    private JLabel getTextLabel(String text){
        JLabel tmpLblText = new JLabel();
        tmpLblText.setOpaque(true);
        tmpLblText.setBackground(Color.white);
        tmpLblText.setFont(new Font("Serif", Font.BOLD, 16));
        tmpLblText.setText(text);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        Border margin = new EmptyBorder(10, 10, 10, 10);
        tmpLblText.setBorder(new CompoundBorder(border, margin));
        tmpLblText.setBounds(0, 0, 270, 90);
        tmpLblText.setVisible(false);

        return tmpLblText;
    }

    public JPanel getPnlInfo() {
        return pnlInfo;
    }

    public JLabel getLblInfo() {
        return lblInfo;
    }

    public JLabel getLblText() {
        return lblText;
    }

    public ImageIcon getImageHelp() {
        return imageHelp;
    }

    public ImageIcon getImageHelpHover() {
        return imageHelpHover;
    }
}
