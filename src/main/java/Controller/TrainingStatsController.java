package Controller;

import Model.DataManger;
import View.TrainingStatsPanel;

import java.util.Observable;
import java.util.Observer;

public class TrainingStatsController implements Observer {
    private DataManger dataManger;
    private TrainingStatsPanel trainingStatsPanel;

    public TrainingStatsController(DataManger dataManger, TrainingStatsPanel trainingStatsPanel) {
        this.dataManger = dataManger;
        this.trainingStatsPanel = trainingStatsPanel;


    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
