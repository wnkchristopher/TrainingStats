import controller.StartPanelController;
import controller.TrainingStatsController;
import models.DataManager;
import views.StartFrame;
import views.StartPanel;
import models.Constants;
import views.TrainingStatsPanel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;


public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        m.checkRequiredStructure();

        int width = 400;
        int height = 480;

        StartFrame frame = new StartFrame(); //View

        DataManager dataManager = new DataManager(); //Model
        StartPanel startPanel = new StartPanel(dataManager); //View
        frame.createFrame(startPanel, "TrainStats", width, height);


        TrainingStatsPanel trainingStatsPanel = new TrainingStatsPanel(dataManager);

        StartPanelController startPanelController = new StartPanelController(dataManager, startPanel, trainingStatsPanel); //Controller

        TrainingStatsController trainingStatsController = new TrainingStatsController(dataManager, trainingStatsPanel);

    }


    /**
     * checks whether the necessary folders exist. If not, it creates them
     */
    public void checkRequiredStructure() {
        String data = "./Data";
        String exercises = Constants.PathExerciseTextFiles;
        String exercisesTxt = Constants.PathExerciseTxt;
        String weightTxt = Constants.PathWeightTxt;
        File file;
        Path path = Paths.get(data);
        if (!Files.exists(path)) {
            file = new File(data);
            file.mkdir();
            file = new File(exercisesTxt);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        path = Paths.get(exercises);
        if (!Files.exists(path)) {
            file = new File(exercises);
            file.mkdir();
        }
        path = Paths.get(weightTxt);
        if (!Files.exists(path)) {
            file = new File(weightTxt);
            try {
                if(!file.createNewFile()){
                    throw new IOException("Is not able to create a new file");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
