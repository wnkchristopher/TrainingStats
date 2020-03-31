package views.extensions;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ButtonEditor {
    public static JButton addImageToButton(JButton button, String pathPicture, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(pathPicture);
        Image image = imageIcon.getImage(); // transform it
        Image newImg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newImg);
        button.setIcon(imageIcon);

        return button;
    }

    public static JButton addImageToButton(JButton button, URL urlPicture, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(urlPicture);
        Image image = imageIcon.getImage(); // transform it
        Image newImg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newImg);
        button.setIcon(imageIcon);

        return button;
    }
}
