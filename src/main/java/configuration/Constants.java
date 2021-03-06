package configuration;

public class Constants {
    //Configurations
    public final static double defaultWeight = 75.0;
    public final static String BackgroundColor = "#E2E2E2";
    public final static String bodyWeight = "weight";
    public final static int ScrollSpeed = 8;

    //Paths
    public final static String PathLogoImage = "/icons/logo.png";
    public final static String PathEditImage = "/icons/edit_small.png";
    public final static String PathDeletionImage = "/icons/delete_small.png";
    public final static String PathBackImage = "/icons/angle_left.png";
    public final static String PathUpImage = "/icons/angle_up.png";
    public final static String PathDownImage = "/icons/angle_down.png";
    public final static String PathInfoImage = "/icons/help.png";
    public final static String PathInfoHoverImage = "/icons/help_hover.png";
    public final static String PathExerciseTxt = "./Data/exercises.txt";
    public final static String PathWeightTxt = "./Data/weight.txt";
    public final static String PathExerciseTextFiles = "./Data/Exercises/";

    //Notifications Observer
    public final static String StartProgram = "StartProgram";
    public final static String AddedExercise = "AddedExercise";
    public final static String DeletedExercise = "DeletedExercise";
    public final static String RenamedExercise = "RenamedExercise";
    public final static String ChangedOrder = "ChangedOrder";

    //Sizes
    public final static int ButtonTextSize = 14;
    public final static int ComboBoxTextSize = 14;
    public final static int HeadlineTextSize = 25;

    //Texts
    public final static String TrainingStatsInfoText =
            "<html><body>For body weight exercises " +
                    "enter a 'b' <br> or leave it empty<br>" +
                    "- extra weight: b+extra weight <br>" +
                    "- support weight: b-support weight";

    //Titles
    public final static String StartPanelTitle = "TrainStats";
    public final static String TrainingStatsTitle = "Add your training stats";
    public final static String PdfPanelTitle = "Generate PDF";
}
