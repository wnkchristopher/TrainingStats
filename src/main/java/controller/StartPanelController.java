package controller;

import models.Constants;
import models.DataManager;
import views.PdfFrame;
import views.StartPanel;
import views.TrainingStatsFrame;
import enums.ExerciseType;
import views.TrainingStatsPanel;

import javax.swing.*;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class StartPanelController implements Observer {
    private DataManager dataManager;
    private StartPanel startPanel;
    private TrainingStatsFrame trainingStatsFrame;
    private PdfFrame pdfFrame;
    private TrainingStatsPanel trainingStatsPanel;

    public StartPanelController(DataManager dataManager, StartPanel startPanel, TrainingStatsPanel trainingStatsPanel) {  //dataManager = model, startPanel = view
        this.dataManager = dataManager;
        this.startPanel = startPanel;
        this.trainingStatsPanel = trainingStatsPanel;
        this.trainingStatsFrame = new TrainingStatsFrame();
        this.pdfFrame = new PdfFrame();

        this.startPanel.getBtnAddExercise().addActionListener(e -> {
            String inputExercise = JOptionPane.showInputDialog("New exercise:");
            if (inputExercise == null || inputExercise.equals("")) {
                return;
            }
            if (!dataManager.proveExerciseExists(inputExercise)) {
                startPanel.getcBExercises().addItem(inputExercise);
            }
            dataManager.addNewExercise(inputExercise);
        });

        this.startPanel.getBtnEditExercise().addActionListener(e -> {
            String exercise = startPanel.getcBExercises().getSelectedItem().toString();
            String newName = JOptionPane.showInputDialog("Rename " + exercise + " to: ");
            if (newName != null) {
                if (dataManager.changeExerciseName(exercise, newName)) {
                    startPanel.getcBExercises().removeItem(exercise);
                    startPanel.getcBExercises().addItem(newName);
                    startPanel.getcBExercises().setSelectedItem(newName);
                } else {
                    JOptionPane.showMessageDialog(null, newName + " already exists");
                }
            }
        });

        this.startPanel.getBtnDeleteExercise().addActionListener(e -> {
            String exercise = startPanel.getcBExercises().getSelectedItem().toString();
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete " + exercise + "?", "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                dataManager.deleteExercise(exercise);
                startPanel.getcBExercises().removeItem(exercise);
            }
        });

        this.startPanel.getBtnAddTraining().addActionListener(e -> {
            trainingStatsFrame = new TrainingStatsFrame();
            trainingStatsFrame.createFrame(this.trainingStatsPanel, 1000, 1000);
        });

        this.startPanel.getBtnGeneratePdf().addActionListener(e -> {
            pdfFrame = new PdfFrame();
            pdfFrame.createFrame();
        });

        this.startPanel.getBtnSubmitWeight().addActionListener(e -> {
            Date date = dataManager.convertToDate(startPanel.getTxtDate().getText());
            if (date == null) {
                JOptionPane.showMessageDialog(null, "Format of date is wrong",
                        "Error: Date", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String entry = startPanel.getTxtDate().getText() + "|0|" + startPanel.getTxtWeight().getText();
            dataManager.writeExerciseStats(Constants.bodyWeight, date, entry, ExerciseType.BODYWEIGHT);
        });
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
