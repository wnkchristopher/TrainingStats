import controller.PdfPanelController;
import controller.StartPanelController;
import controller.TrainingStatsController;
import models.DataManager;
import views.*;
import configuration.Constants;
import views.Frame.FrameContentChanger;
import views.Frame.MainFrame;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;


public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        m.checkRequiredStructure();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DataManager dataManager = new DataManager(); //Model
                StartPanel startPanel = new StartPanel(); //View
                TrainingStatsPanel trainingStatsPanel = new TrainingStatsPanel();
                PdfPanel pdfPanel = new PdfPanel();

                FrameContentChanger frame = new MainFrame(startPanel, trainingStatsPanel, pdfPanel);

                StartPanelController startPanelController = new StartPanelController(dataManager, startPanel,
                        frame); //Controller
                TrainingStatsController trainingStatsController = new TrainingStatsController(dataManager, trainingStatsPanel,
                        frame);
                PdfPanelController pdfPanelController = new PdfPanelController(dataManager, pdfPanel,
                        frame);

                dataManager.notifyObservers(Constants.StartProgram);

            }
        });


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
