package controller;

import enums.PdfType;
import models.DataManager;
import models.GeneratePdf;
import views.PdfPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class PdfPanelController {
    public PdfPanelController(DataManager dataManager, PdfPanel pdfPanel) {
        GeneratePdf generatePdf = new GeneratePdf();
        pdfPanel.getBtnSubmitButton().addActionListener(e -> {
            String strFrom = pdfPanel.getTxtFrom().getText();
            String strTo = pdfPanel.getTxtTo().getText();
            ArrayList<String> exercises = new ArrayList<>();
            Date from = dataManager.convertToDate(strFrom);
            Date to = dataManager.convertToDate(strTo);
            if (from == null || to == null) {
                JOptionPane.showMessageDialog(null, "Format of date is wrong",
                        "Error: Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            pdfPanel.getTxtFrom().setText(dataManager.convertDateToString(from));
            pdfPanel.getTxtTo().setText(dataManager.convertDateToString(to));

            if (from.after(to)) {
                JOptionPane.showMessageDialog(null, "Your last date has to be after " +
                        "your first");
            } else {
                for (JCheckBox cb : pdfPanel.getcBExercises()) {
                    if (cb.isSelected()) {
                        exercises.add(cb.getText());
                    }
                }
                if (pdfPanel.getRbOnePdf().isSelected()) {
                    generatePdf.generatePdf(from, to, exercises, PdfType.ONE_PDF_FOR_ALL_EXERCISES);
                } else if (pdfPanel.getRbMorePdfs().isSelected()) {
                    generatePdf.generatePdf(from, to, exercises, PdfType.ONE_PDF_FOR_EACH_EXERCISE);
                }
            }
        });
    }
}
