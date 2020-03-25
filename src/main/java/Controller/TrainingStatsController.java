package Controller;

import Model.Constants;
import Model.DataManger;
import View.ExercisePanel;
import View.Extensions.PlaceholderTextField;
import View.TrainingStatsPanel;

import javax.swing.*;
import java.util.*;

public class TrainingStatsController implements Observer {
    private DataManger dataManger;
    private TrainingStatsPanel trainingStatsPanel;

    public TrainingStatsController(DataManger dataManger, TrainingStatsPanel trainingStatsPanel) {
        this.dataManger = dataManger;
        this.dataManger.addObserver(this);
        this.trainingStatsPanel = trainingStatsPanel;

        this.trainingStatsPanel.getBtnSubmit().addActionListener(e -> {
            Date dateOfTraining = dataManger.convertToDate(this.trainingStatsPanel.getTxtDate().getText());
            if (dateOfTraining == null) {
                JOptionPane.showMessageDialog(null, "Format of date is wrong",
                        "Error: Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<String, ExercisePanel> exercisePanels = this.trainingStatsPanel.getExercisePanels();

            for (Map.Entry<String, ExercisePanel> entry : exercisePanels.entrySet()) {

                String exercise = entry.getKey();
                String newExerciseLine = this.dataManger.convertDateToString(dateOfTraining);

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
                        newExerciseLine += "|" + reps + "|" + weight;
                    }else{
                        break;
                    }

                    //reset text fields
                    txtFields.get(set)[0].setText("");
                    txtFields.get(set)[1].setText("");
                }
                //checks if text fields are used
                if(newExerciseLine.contains("|")){
                    this.dataManger.addWorkout(exercise, dateOfTraining, newExerciseLine);
                }
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == this.dataManger && arg.equals(Constants.changedExerciseOrder)) {
            this.trainingStatsPanel.refresh();
        }
    }
}
