package configuration;

public class Constants {
    //Configurations
    public final static double defaultWeight = 75.0;
    public final static String BackgroundColor = "#ffddc1";
    public final static String bodyWeight = "weight";
    public final static int ScrollSpeed = 8;

    //Paths
    public final static String PathEditImage = "resources/img/edit_small.png";
    public final static String PathDeletionImage = "resources/img/delete_small.png";
    public final static String PathExerciseTxt = "./Data/exercises.txt";
    public final static String PathWeightTxt = "./Data/weight.txt";
    public final static String PathExerciseTextFiles = "./Data/Exercises/";
    public final static String PathBackImage = "resources/img/angle_left.png";

    //Notifications Observer
    public final static String StartProgram = "StartProgram";
    public final static String AddedExercise = "AddedExercise";
    public final static String DeletedExercise = "DeletedExercise";
    public final static String RenamedExercise = "RenamedExercise";
    public final static String ChangedOrder = "ChangedOrder";

    //Texts
    public final static String TrainingStatsTitle = "Add your training stats";
    public final static String TrainingStatsInfoText =
            "<html><body>For body weight exercises " +
                    "enter a 'b' <br> or leave it empty<br>" +
                    "- extra weight: b+extra weight <br>" +
                    "- support weight: b-support weight";
}
