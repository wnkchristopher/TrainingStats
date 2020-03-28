package controller;

import views.extensions.InfoBox;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InfoBoxController {
    public InfoBoxController(InfoBox infoBox) {
        infoBox.getlblInfoIcon().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                infoBox.getlblInfoIcon().setIcon(infoBox.getImageHelpHover());
                infoBox.getLblText().setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                infoBox.getlblInfoIcon().setIcon(infoBox.getImageHelp());
                infoBox.getLblText().setVisible(false);
            }
        });
    }
}
