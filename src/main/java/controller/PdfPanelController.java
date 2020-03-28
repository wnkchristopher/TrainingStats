package controller;

import enums.PdfType;
import models.Constants;
import models.DataManager;
import models.DateManager;
import models.GeneratePdf;
import views.PdfPanel;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.util.*;

public class PdfPanelController implements Observer {
    private DataManager dataManager;
    private PdfPanel pdfPanel;

    public PdfPanelController(DataManager dataManager, PdfPanel pdfPanel) {
        this.dataManager = dataManager;
        this.pdfPanel = pdfPanel;
        this.dataManager.addObserver(this);
        GeneratePdf generatePdf = new GeneratePdf();
        pdfPanel.getBtnSubmitButton().addActionListener(e -> {
            String strFrom = pdfPanel.getTxtFrom().getText();
            String strTo = pdfPanel.getTxtTo().getText();
            ArrayList<String> exercises = new ArrayList<>();
            Date from = DateManager.convertStringToDate(strFrom);
            Date to = DateManager.convertStringToDate(strTo);
            if (from == null || to == null) {
                JOptionPane.showMessageDialog(null, "Format of date is wrong",
                        "Error: Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            pdfPanel.getTxtFrom().setText(DateManager.convertDateToString(from));
            pdfPanel.getTxtTo().setText(DateManager.convertDateToString(to));

            if (from.after(to)) {
                JOptionPane.showMessageDialog(null, "Your last date has to be after " +
                        "your first");
            } else {
                for(Map.Entry<String, JCheckBox> entry : this.pdfPanel.getcBExercises().entrySet()) {
                    if (entry.getValue().isSelected()) {
                        exercises.add(entry.getValue().getText());
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

    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(this.dataManager)) {
            if (arg.equals(Constants.StartProgram)) {
                for (String exercise : this.dataManager.getExercises()) {
                    this.pdfPanel.addExercise(exercise);
                }
            }
            if (arg.equals(Constants.AddedExercise) || (arg.equals(Constants.RenamedExercise))) {
                this.pdfPanel.addExercise(this.dataManager.getNewExercise());
            }
            if (arg.equals(Constants.DeletedExercise) || arg.equals(Constants.RenamedExercise)) {
                 this.pdfPanel.getcBExercises().remove(this.dataManager.getDeletedExercise());
            }

            this.pdfPanel.refresh(this.dataManager.getExercises());
        }
    }
}
