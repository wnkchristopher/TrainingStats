package Controller;

import Model.Constants;
import Model.DataManger;
import View.ExercisePanel;
import com.sun.javafx.scene.traversal.Direction;

import java.util.Observable;
import java.util.Observer;

public class ExercisePanelController implements Observer {
    private DataManger dataManger;
    private ExercisePanel exercisePanel;

    public ExercisePanelController(DataManger dataManger, ExercisePanel exercisePanel) {
        this.dataManger = dataManger;
        this.dataManger.addObserver(this);
        this.exercisePanel = exercisePanel;

        this.exercisePanel.getBtnPlus().addActionListener(e ->
                this.exercisePanel.addNewSet()
        );

        this.exercisePanel.getBtnUp().addActionListener(e ->
            dataManger.changeExerciseOrder(this.exercisePanel.getExercise(), Direction.UP)
        );

        this.exercisePanel.getBtnDown().addActionListener(e ->
            dataManger.changeExerciseOrder(this.exercisePanel.getExercise(), Direction.DOWN)
        );
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
