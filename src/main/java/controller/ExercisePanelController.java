package controller;

import models.DataManager;
import views.ExercisePanel;
import com.sun.javafx.scene.traversal.Direction;

import java.util.Observable;
import java.util.Observer;

public class ExercisePanelController implements Observer {
    private DataManager dataManager;
    private ExercisePanel exercisePanel;

    public ExercisePanelController(DataManager dataManager, ExercisePanel exercisePanel) {
        this.dataManager = dataManager;
        this.dataManager.addObserver(this);
        this.exercisePanel = exercisePanel;

        this.exercisePanel.getBtnPlus().addActionListener(e ->
                this.exercisePanel.addNewSet()
        );

        this.exercisePanel.getBtnUp().addActionListener(e ->
            dataManager.changeExerciseOrder(this.exercisePanel.getExercise(), Direction.UP)
        );

        this.exercisePanel.getBtnDown().addActionListener(e ->
            dataManager.changeExerciseOrder(this.exercisePanel.getExercise(), Direction.DOWN)
        );
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
