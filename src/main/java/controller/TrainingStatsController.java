package controller;

import models.Constants;
import models.DataManager;
import models.DateManager;
import models.TrainingSet;
import views.ExercisePanel;
import views.extensions.PlaceholderTextField;
import views.TrainingStatsPanel;

import javax.swing.*;
import java.util.*;

public class TrainingStatsController implements Observer {
    private DataManager dataManager;
    private TrainingStatsPanel trainingStatsPanel;

    public TrainingStatsController(DataManager dataManager, TrainingStatsPanel trainingStatsPanel) {
        this.dataManager = dataManager;
        this.dataManager.addObserver(this);
        this.trainingStatsPanel = trainingStatsPanel;

        this.trainingStatsPanel.getBtnSubmit().addActionListener(e -> {
            Date dateOfTraining = DateManager.convertStringToDate(this.trainingStatsPanel.getTxtDate().getText());
            if (dateOfTraining == null) {
                JOptionPane.showMessageDialog(null, "Format of date is wrong",
                        "Error: Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<String, ExercisePanel> exercisePanels = this.trainingStatsPanel.getExercisePanels();

            for (Map.Entry<String, ExercisePanel> entry : exercisePanels.entrySet()) {
                String exercise = entry.getKey();
                List<TrainingSet> sets = new ArrayList<>();

                Map<Integer, PlaceholderTextField[]> txtFields = entry.getValue().getTxtFields();

                for (int set = 1; set <= txtFields.size(); set++) {
                    String reps = txtFields.get(set)[0].getText();
                    String weight = txtFields.get(set)[1].getText();

                    if (!reps.isEmpty()) {
                        if (weight.isEmpty() || weight.contains("b")) {
                            String digits = weight.replaceAll("\\D+", "");
                            if (weight.contains("+")) {
                                weight = "b+" + digits;
                            } else if (weight.contains("-")) {
                                weight = "b-" + digits;
                            }
                        }
                        sets.add(new TrainingSet(reps, weight));
                    } else {
                        break;
                    }

                    //reset text fields
                    txtFields.get(set)[0].setText("");
                    txtFields.get(set)[1].setText("");
                }
                //checks if text fields are used
                if (!sets.isEmpty()) {
                    this.dataManager.addWorkout(exercise, dateOfTraining, sets);
                }
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == this.dataManager) {
            if (arg.equals(Constants.StartProgram)) {
                for (String exercise : this.dataManager.getExercises()) {
                    ExercisePanel exercisePanel = new ExercisePanel(exercise);
                    ExercisePanelController exercisePanelController =
                            new ExercisePanelController(this.dataManager, exercisePanel);
                    this.trainingStatsPanel.addExercisePanel(exercise, exercisePanel);
                }
            } else if (arg.equals(Constants.AddedExercise) || (arg.equals(Constants.RenamedExercise))) {
                ExercisePanel exercisePanel = new ExercisePanel(this.dataManager.getNewExercise());
                ExercisePanelController exercisePanelController =
                        new ExercisePanelController(this.dataManager, exercisePanel);
                this.trainingStatsPanel.addExercisePanel(this.dataManager.getNewExercise(), exercisePanel);
            }
            if (arg.equals(Constants.DeletedExercise)|| arg.equals(Constants.RenamedExercise)) {
                this.trainingStatsPanel.getExercisePanels().remove(this.dataManager.getDeletedExercise());
            }
            this.trainingStatsPanel.refresh(this.dataManager.getExercises());
        }
    }
}
