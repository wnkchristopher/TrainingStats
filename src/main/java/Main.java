import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;


public class Main {

    public static String bodyweight = "weight";
    public static double defaultWeight = 75.0;

    public static void main(String[] args) {
        Main m = new Main();
        m.checkRequiredStructure();

        Frame frame = new Frame();
        frame.createFrame("TrainStats", 400, 480);
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
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
