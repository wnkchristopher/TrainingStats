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

    public void addNewExercise(String exercise) { //DataBaseManager

        if (this.proveExerciseExists(exercise)) {
            return;
        }
        this.addExerciseToList(exercise);
        File file = new File(Constants.PathExerciseTextFiles + exercise + ".txt");
        try {
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void writeExerciseList(List<String> exerciseOrder) {
        try {
            PrintWriter writer = new PrintWriter(Constants.PathExerciseTxt);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String exercise : exerciseOrder) {
            this.addExerciseToList(exercise);
        }
    }

    private void addExerciseToList(String exercise) {
        try {     //add exercise to list of exercises
            FileWriter writer = new FileWriter(Constants.PathExerciseTxt, true);
            writer.write(exercise);
            writer.write("\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            filepath = Constants.PathWeightTxt;
        }

        if (sets.isEmpty()) {
            return false; //no sets to add
        }
        String line = DateManager.convertDateToString(date);
        for (TrainingSet set : sets) {
            line = line + "|" + set.getReps() + "|" + set.getWeight();
        }

        int index = this.getLineToInsert(exercise, date, exerciseType);

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
        Map<Date, List<TrainingSet>> stats = this.getExerciseStats("", ExerciseType.BODYWEIGHT);
        Map<Date, Double> weights = new HashMap<>();
        for (Map.Entry<Date, List<TrainingSet>> entry : stats.entrySet()) {
            weights.put(entry.getKey(), Double.valueOf(entry.getValue().get(0).getWeight()));
        }
        return weights;
    }

    public Map<Date, List<TrainingSet>> getExerciseStats(String exercise) {
        return this.getExerciseStats(exercise, ExerciseType.EXERCISE);
    }

    private Map<Date, List<TrainingSet>> getExerciseStats(String exercise, ExerciseType exerciseType) {
        Map<Date, List<TrainingSet>> stats = new HashMap<>();
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
                List<TrainingSet> sets = new LinkedList<>();
                for (int i = 1; i < split.length-1; i += 2) {
                    String reps = split[i];
                    String weight = split[i+1];
                    if(weight.contains("b")){
                        weight = this.calculateActualWeight(d, weight);
                    }
                    TrainingSet tmpTrainingSet = new TrainingSet(reps, weight);
                    sets.add(tmpTrainingSet);
                }
                stats.put(d, sets);
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
        if (exerciseType == ExerciseType.EXERCISE) {
            filePath = Constants.PathExerciseTextFiles + exercise + ".txt";
        } else if (exerciseType == ExerciseType.BODYWEIGHT) {
            filePath = Constants.PathWeightTxt;
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

    private String calculateActualWeight(Date date, String weight) {
        Double extraWeight = 0.0;
        if (weight.contains("+")) {
            String[] split = weight.split("\\+");
            extraWeight = Double.valueOf(split[split.length - 1]);
        } else if (weight.contains("-")) {
            String[] split = weight.split("-");
            extraWeight = -1.0 * Double.valueOf(split[split.length - 1]);
        }

        double actualWeight = this.getWeight(date) + extraWeight;

        return String.valueOf(actualWeight);
    }

    public double getWeight(Date date) {
        Map<Date, Double> weights = this.getWeights();

        long days = 0;
        Date chosenDate = null;
        boolean firstDay = true;


        for (Map.Entry<Date, Double> entry : weights.entrySet()) {
            long daysBetweenDates = DateManager.getDaysBetweenDates(date, entry.getKey());
            if (daysBetweenDates == 0) {
                return entry.getValue();
            }
            if (Math.abs(daysBetweenDates) < Math.abs(days) || firstDay) {
                days = daysBetweenDates;
                chosenDate = entry.getKey();
                firstDay = false;
            }
        }
        if (weights.isEmpty()) {//no entries in weight.txt
            return Constants.defaultWeight;
        } else {
            return weights.get(chosenDate); // closest weight
        }
    }


}
