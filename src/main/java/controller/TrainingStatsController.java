package controller;

import configuration.Constants;
import controller.validation.DateValidator;
import enums.PanelType;
import models.DataManager;
import models.DateManager;
import models.TrainingSet;
import views.ExercisePanel;
import views.MainFrame;
import views.extensions.PlaceholderTextField;
import views.TrainingStatsPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TrainingStatsController implements Observer {
    private DataManager dataManager;
    private TrainingStatsPanel trainingStatsPanel;
    private MainFrame frame;

    public TrainingStatsController(DataManager dataManager, TrainingStatsPanel trainingStatsPanel, MainFrame frame) {
        this.dataManager = dataManager;
        this.frame = frame;
        this.dataManager.addObserver(this);
        this.trainingStatsPanel = trainingStatsPanel;

        this.trainingStatsPanel.getBtnSubmit().addActionListener(e -> {
            String date = this.trainingStatsPanel.getTxtDate().getText();
            DateValidator dateValidator = new DateValidator();

            if (!dateValidator.verify(date)) {
                this.trainingStatsPanel.getTxtDate().setBorder(new LineBorder(Color.red));
                return;
            }

            Date dateOfTraining = DateManager.convertStringToDate(this.trainingStatsPanel.getTxtDate().getText());
            this.trainingStatsPanel.getTxtDate().setText(DateManager.convertDateToString(dateOfTraining));

            Map<String, ExercisePanel> exercisePanels = this.trainingStatsPanel.getExercisePanels();

            if (!this.validateSets(new ArrayList<>(exercisePanels.values()))) {
                return;
            }

            for (Map.Entry<String, ExercisePanel> entry : exercisePanels.entrySet()) {
                String exercise = entry.getKey();
                List<TrainingSet> sets = new ArrayList<>();
                Map<Integer, PlaceholderTextField[]> txtFields = entry.getValue().getTxtFields();

                for (int set = 1; set <= txtFields.size(); set++) {
                    String reps = txtFields.get(set)[0].getText();
                    String weight = txtFields.get(set)[1].getText();

                    if(!reps.isEmpty()){
                        sets.add(new TrainingSet(reps, weight));
                    }
                    //reset text fields
                    txtFields.get(set)[0].setText("");
                    txtFields.get(set)[0]
                            .setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));;
                    txtFields.get(set)[1].setText("");
                    txtFields.get(set)[1]
                            .setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));;
                }
                //checks if text fields are used
                if (!sets.isEmpty()) {
                    this.dataManager.addWorkout(exercise, dateOfTraining, sets);
                }
                this.trainingStatsPanel.getTxtDate()
                        .setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
            }
        });

        trainingStatsPanel.getBtnBack().addActionListener(e ->
            this.frame.changePanel(PanelType.START_PANEL)
        );
    }

    private boolean validateSets(List<ExercisePanel> exercisePanels) {
        boolean valid = true;
        for (ExercisePanel exercisePanel : exercisePanels) {
            for (int set = 1; set <= exercisePanel.getTxtFields().size(); set++) {
                JTextField txtReps = exercisePanel.getTxtFields().get(set)[0];
                JTextField txtWeight = exercisePanel.getTxtFields().get(set)[1];
                String reps = txtReps.getText();
                String weight = txtWeight.getText();
                if (!(reps.isEmpty() && weight.isEmpty())) {
                    if (!reps.matches("^[0-9]+$")) {
                        txtReps.setBorder(new LineBorder(Color.red));
                        valid = false;
                    }
                    if (!weight.isEmpty() && !weight.matches("^[0-9]*$") && !weight.equals("b")
                            && !weight.matches("^( )*b( )*(\\+|-)( )*[0-9]+( )*$")) {
                        txtWeight.setBorder(new LineBorder(Color.red));
                        valid = false;
                    } else{
                        if(weight.isEmpty()){
                            weight = "b";
                        }else{
                            weight = weight.replaceAll(" ", "");
                        }
                        txtWeight.setText(weight);
                    }

                }
            }
        }
        return valid;
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
            if (arg.equals(Constants.DeletedExercise) || arg.equals(Constants.RenamedExercise)) {
                this.trainingStatsPanel.getExercisePanels().remove(this.dataManager.getDeletedExercise());
            }
            this.trainingStatsPanel.refresh(this.dataManager.getExercises());
        }
    }
}
