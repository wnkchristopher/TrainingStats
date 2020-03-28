package views.extensions;

import controller.InfoBoxController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoBox {
    private JLabel lblInfoIcon;
    private JLabel lblText;
    private ImageIcon imageHelp;
    private ImageIcon imageHelpHover;

    public InfoBox(String text) {
        this.createInfoBox(text, 300,90);
        InfoBoxController infoBoxController = new InfoBoxController(this);
    }

    private void createInfoBox(String text, int width, int height){
        this.loadImages();

        /*JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(null);
        pnlInfo.setSize(width, height);
        pnlInfo.setOpaque(false);*/

        this.lblText = this.getTextLabel(text);
        this.lblInfoIcon = this.getImageLabel();

      //  pnlInfo.add(this.lblInfoIcon);
       // pnlInfo.add(this.lblText);

      //  return pnlInfo;
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

    private JLabel getImageLabel() {
        JLabel lblInfoIcon = new JLabel();
        lblInfoIcon.setIcon(this.imageHelp);
        lblInfoIcon.setBounds(270, 10, 30, 30);

        return lblInfoIcon;
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

    /*public JPanel getPnlInfo() {
        return pnlInfo;
    }*/

    public JLabel getlblInfoIcon() {
        return lblInfoIcon;
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
