package Controller;

import Model.Constants;
import Model.DataManger;
import Model.ExerciseSet;
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

            Map<ExerciseSet, PlaceholderTextField[]> txtFields = this.trainingStatsPanel.getTxtFields();

            Date dateOfTraining = dataManger.convertToDate(this.trainingStatsPanel.getTxtDate().getText());
            if (dateOfTraining == null) {
                JOptionPane.showMessageDialog(null, "Format of date is wrong",
                        "Error: Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<ExerciseSet, String[]> tmpMap = new HashMap<>();
            for (ExerciseSet exerciseSet : txtFields.keySet()) {    //Get content of textFields
                String reps = txtFields.get(exerciseSet.getExercise())[0].getText();
                String weight = txtFields.get(exerciseSet.getExercise())[1].getText();
                String[] s = new String[2];
                s[0] = reps;
                s[1] = weight;
                if (!s[0].isEmpty()) {
                    if (s[1].isEmpty() || s[1].contains("b")) {
                        String digits = s[1].replaceAll("\\D+", "");
                        if (s[1].contains("+")) {
                            s[1] = "b+" + digits;
                            tmpMap.put(exerciseSet, s);
                        } else if (s[1].contains("-")) {
                            s[1] = "b-" + digits;
                            tmpMap.put(exerciseSet, s);
                        }
                    } else {
                        tmpMap.put(exerciseSet, s);
                    }
                }

                //refresh for next entry
                txtFields.get(exerciseSet)[0].setText("");
                txtFields.get(exerciseSet)[1].setText("");
            }
            dataManger.addWorkout(tmpMap, dateOfTraining);
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == this.dataManger && arg.equals(Constants.changedExerciseOrder)) {
            this.trainingStatsPanel.refresh();
        }
    }
}
