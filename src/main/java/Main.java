import Controller.StartPanelController;
import Model.DataManger;
import View.StartFrame;
import View.StartPanel;

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

        DataManger dataManger = new DataManger(); //Model
        StartPanel startPanel = new StartPanel(dataManger); //View
        frame.createFrame(startPanel, "TrainStats", width, height);

        StartPanelController startPanelController = new StartPanelController(dataManger, startPanel); //Controller

    }


    /**
     * checks whether the necessary folders exist. If not, it creates them
     */
    public void checkRequiredStructure() {
        String data = "./Data";
        String exercises = "./Data/Exercises";
        String exercisesTxt = "./Data/exercises.txt";
        String weightTxt = "./Data/weight.txt";
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
