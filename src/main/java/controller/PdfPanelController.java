package controller;

import enums.PanelType;
import enums.PdfType;
import configuration.Constants;
import models.DataManager;
import models.DateManager;
import pdfGeneration.GeneratePdf;
import views.MainFrame;
import views.PdfPanel;
import controller.validation.DateValidator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PdfPanelController implements Observer {
    private DataManager dataManager;
    private PdfPanel pdfPanel;
    private MainFrame frame;

    public PdfPanelController(DataManager dataManager, PdfPanel pdfPanel, MainFrame frame) {
        this.dataManager = dataManager;
        this.pdfPanel = pdfPanel;
        this.frame = frame;
        this.dataManager.addObserver(this);
        GeneratePdf generatePdf = new GeneratePdf();
        pdfPanel.getBtnSubmit().addActionListener(e -> {
            String strFrom = pdfPanel.getTxtFrom().getText();
            String strTo = pdfPanel.getTxtTo().getText();

            DateValidator dateValidator = new DateValidator();

            boolean verifyTxtFrom = dateValidator.verify(strFrom);
            boolean verifyTxtTo = dateValidator.verify(strTo);

            if(!verifyTxtFrom || !verifyTxtTo) {
                if(!verifyTxtFrom ) {
                    this.pdfPanel.getTxtFrom().setBorder(new LineBorder(Color.red));
                }
                if(!verifyTxtTo) {
                    this.pdfPanel.getTxtTo().setBorder(new LineBorder(Color.red));
                }
                return;
            }
            Date from = DateManager.convertStringToDate(strFrom);
            Date to = DateManager.convertStringToDate(strTo);
            pdfPanel.getTxtFrom().setText(DateManager.convertDateToString(from));
            pdfPanel.getTxtTo().setText(DateManager.convertDateToString(to));
            if (from.after(to)) {
                JOptionPane.showMessageDialog(null, "Your last date has to be after " +
                        "your first");
                return;
            }

            List<String> exercises = new LinkedList<>();
            List<String> iterationList = this.dataManager.getExercises();
            iterationList.add(0, Constants.bodyWeight);

            for(String element: iterationList) {
                if(this.pdfPanel.getcBExercises().get(element).isSelected()) {
                    exercises.add(element);
                }
            }
            if (pdfPanel.getRbOnePdf().isSelected()) {
                generatePdf.generatePdf(from, to, exercises, PdfType.ONE_PDF_FOR_ALL_EXERCISES);
            } else if (pdfPanel.getRbMorePdfs().isSelected()) {
                generatePdf.generatePdf(from, to, exercises, PdfType.ONE_PDF_FOR_EACH_EXERCISE);
            }

            pdfPanel.getTxtFrom().setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
            pdfPanel.getTxtTo().setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));

        });

        pdfPanel.getBtnBack().addActionListener(e ->
            frame.changePanel(PanelType.START_PANEL)
        );
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
