package models;

import enums.ExerciseType;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DataBaseManager {
    public DataBaseManager() {

    }

    public void addNewExercise(String name) { //DataBaseManager

        if (this.proveExerciseExists(name)) {
            return;
        }
        try {     //add exercise to list of exercises
            FileWriter writer = new FileWriter(Constants.PathExerciseTxt, true);
            writer.write(name);
            writer.write("\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(Constants.PathExerciseTextFiles + name + ".txt");
    }

    public List<String> getExerciseList() {
        List<String> exerciseList = new ArrayList<>();
        try {
            String line;
            BufferedReader bufferReader = new BufferedReader(new FileReader(Constants.PathExerciseTxt));
            while ((line = bufferReader.readLine()) != null) {
                exerciseList.add(line);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return exerciseList;
    }

    public void writeExerciseList(List<String> exerciseOrder) {
        for (String exercise : exerciseOrder) {
            try {     //add exercise to list of exercises
                FileWriter writer = new FileWriter(Constants.PathExerciseTxt, true);
                writer.write(exercise);
                writer.write("\r\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isFileEmpty(String exercise) {
        File logFile = new File(Constants.PathExerciseTextFiles + exercise + ".txt");
        if (logFile.length() == 0) {
            return true;
        }
        return false;
    }

    public boolean writeExerciseStats(String exercise, Date date, List<TrainingSet> sets, ExerciseType exerciseType) {
        String filepath = "";
        if (exerciseType.equals(ExerciseType.EXERCISE)) {
            filepath = Constants.PathExerciseTextFiles + exercise + ".txt";
        } else if (exerciseType.equals(ExerciseType.BODYWEIGHT)) {
            filepath = "./Data/" + exercise + ".txt";
        }

        if (sets.isEmpty()) {
            return false; //no sets to add
        }
        String line = DateManager.convertDateToString(date);
        for (TrainingSet set : sets) {
            line = line + "|" + set.getReps() + "|" + set.getWeight();
        }

        int index = this.getLineToInsert(exercise, date, ExerciseType.EXERCISE);

        try {
            //create file
            PrintWriter writer =
                    new PrintWriter(new FileWriter(filepath, true));
            //writer.write(content);
            writer.close();
            //new stats getting insert sorted
            if (date != null) {
                Path path = FileSystems.getDefault()
                        .getPath(filepath);
                List<String> lines = Files.readAllLines(path, Charset.forName("ISO-8859-1"));
                lines.add(index, line);
                Files.write(path, lines);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }

    public Map<Date, Double> getWeights() {
        Map<Date, TrainingSet> stats = this.getExerciseStats("", ExerciseType.BODYWEIGHT);
        Map<Date, Double> weights = new HashMap<>();
        for (Map.Entry<Date, TrainingSet> entry : stats.entrySet()) {
            weights.put(entry.getKey(), Double.valueOf(entry.getValue().getWeight()));
        }
        return weights;
    }

    public Map<Date, TrainingSet> getExerciseStats(String exercise) {
        return this.getExerciseStats(exercise, ExerciseType.EXERCISE);
    }

    private Map<Date, TrainingSet> getExerciseStats(String exercise, ExerciseType exerciseType) {
        Map<Date, TrainingSet> stats = new HashMap<>();
        String filepath = "";
        if (exerciseType.equals(ExerciseType.BODYWEIGHT)) {
            filepath = Constants.PathWeightTxt;
        } else if (exerciseType.equals(ExerciseType.EXERCISE)) {
            filepath = Constants.PathExerciseTextFiles + exercise + ".txt";
        }

        try {
            String line;
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(filepath));
            while ((line = bufferedReader.readLine()) != null) {
                Date d = this.getDateOfLine(line);
                if (d == null) {
                    break;
                }
                String[] split = line.split("\\|");
                for (int i = 0; i < split.length; i += 2) {
                    TrainingSet tmpTrainingSet = new TrainingSet(split[i], split[i+1]);
                    stats.put(d, tmpTrainingSet);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return stats;
    }

    private Date getDateOfLine(String line) {
        int i = line.indexOf("|");
        if (i < 0) {
            return null;  //may be Exception later
        }
        String stringDate = line.substring(0, i);
        return DateManager.convertStringToDate(stringDate);
    }

    private int getLineToInsert(String exercise, Date date, ExerciseType exerciseType) {
        if (date == null) {
            return 0;
        }

        String filePath = "";
        if (exerciseType.equals(ExerciseType.EXERCISE)) {
            filePath = Constants.PathExerciseTextFiles + exercise + ".txt";
        } else if (exerciseType.equals(ExerciseType.BODYWEIGHT)) {
            filePath = "./Data/" + exercise + ".txt";
        }

        int lineCounter = 0;
        try {
            String stringDate;
            String line;
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine()) != null) {

                Date d = this.getDateOfLine(line);
                if (d == null) {
                    break;
                }
                if (d.before(date)) {
                    lineCounter++;
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (lineCounter < 0) {
            lineCounter = 0;
        }

        return lineCounter;
    }

    private int getLineOfDate(String exercise, Date date, ExerciseType exerciseType) {
        String filePath = Constants.PathExerciseTextFiles + exercise + ".txt";
        if (exerciseType.equals(ExerciseType.BODYWEIGHT)) {
            filePath = Constants.PathWeightTxt;
        }
        try {
            int lineNumber = 0;
            String line;
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;
                Date d = this.getDateOfLine(line);
                if (d == null) {
                    break;
                }
                if (d.equals(date)) {
                    return lineNumber;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public boolean deleteExercise(String exercise) {
        String path = Constants.PathExerciseTextFiles + exercise + ".txt";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        } else {
            return false;
        }
        List<String> exercises = this.getExerciseList();
        exercises.remove(exercise);
        this.writeExerciseList(exercises);

        return true;
    }

    public boolean changeExerciseName(String exercise, String newName) {

        if (new File(Constants.PathExerciseTextFiles + newName + ".txt").exists()) {
            return false;
        }
        try {
            Files.move(Paths.get(Constants.PathExerciseTextFiles + exercise + ".txt"),
                    Paths.get(Constants.PathExerciseTextFiles + newName + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List exercises = this.getExerciseList();
        int index = exercises.indexOf(exercise);
        exercises.remove(index);
        exercises.add(index, newName);
        this.writeExerciseList(exercises);

        return true;
    }

    private boolean proveExerciseExists(String exercise_name) { //maybe
        Iterator iterator = getExerciseList().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(exercise_name)) {
                return true;
            }
        }
        return false;
    }

}
