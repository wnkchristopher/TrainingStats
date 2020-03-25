package Model;

import Enum.ExerciseType;
import com.sun.javafx.scene.traversal.Direction;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DataManger extends Observable {
    CalculateStats calculateStats;

    public DataManger() {
        this.calculateStats = new CalculateStats();
    }

    public void addNewExercise(String name) {

        if (proveExerciseExists(name)) {
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
        this.writeExerciseStats(name, null, "", ExerciseType.EXERCISE);
    }

    public boolean proveExerciseExists(String exercise_name) {
        Iterator iterator = getExerciseList().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(exercise_name)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getExerciseList() {
        List<String> exerciseList = new ArrayList<>();
        try {
            String line;
            BufferedReader bufferreader = new BufferedReader(new FileReader(Constants.PathExerciseTxt));
            while ((line = bufferreader.readLine()) != null) {
                exerciseList.add(line);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return exerciseList;
    }

    public void writeExerciseTxt(List<String> exerciseOrder) {

    }

    public void changeExerciseOrder(String exercise, Direction direction) {
        List<String> exercises = this.getExerciseList();
        int index = exercises.indexOf(exercise);
        if (direction == Direction.UP && index > 0) {
            Collections.swap(exercises, index, index-1);
        } else if (direction == Direction.DOWN && index+1 < exercises.size()) {
            Collections.swap(exercises, index, index+1);
        }

        this.changeExerciseOrder(exercises);

        this.setChanged();
        this.notifyObservers(Constants.changedExerciseOrder);  //why is update 12 times (quantity of exercises) called?
    }


    public void changeExerciseOrder(List<String> exerciseOrder) {
        try {
            PrintWriter writer = new PrintWriter(Constants.PathExerciseTxt);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String s : exerciseOrder) {
            this.addNewExercise(s);
        }
    }

    public String convertDateToString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String stringDate = calendar.get(Calendar.DAY_OF_MONTH) + "." +
                (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);

        return stringDate;
    }

    public void addWorkout(String exercise, Date dateOfTraining, String content) {
        if (!content.isEmpty()) {
            this.writeExerciseStats(exercise, dateOfTraining, content, ExerciseType.EXERCISE);
        }
    }

    public Date convertToDate(String sDate) {
        Date date;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            date = dateFormatter.parse(sDate);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    private boolean isFileEmpty(String exercise) {
        File logFile = new File(Constants.PathExerciseTextFiles + exercise + ".txt");
        if (logFile.length() == 0) {
            return true;
        }
        return false;
    }


    public void writeExerciseStats(String exercise, Date dateOfTraining, String content, ExerciseType exerciseType) {
        int line = this.getLineToInsert(exercise, dateOfTraining, exerciseType);
        String filepath = "";
        if (exerciseType.equals(ExerciseType.EXERCISE)) {
            filepath = Constants.PathExerciseTextFiles + exercise + ".txt";
        } else if (exerciseType.equals(ExerciseType.BODYWEIGHT)) {
            filepath = "./Data/" + exercise + ".txt";
        }
        try {
            //create file
            PrintWriter writer =
                    new PrintWriter(new FileWriter(filepath, true));
            //writer.write(content);
            writer.close();
            //new stats getting insert sorted
            if (dateOfTraining != null) {
                Path path = FileSystems.getDefault()
                        .getPath(filepath);
                List<String> lines = Files.readAllLines(path, Charset.forName("ISO-8859-1"));
                lines.add(line, content);
                Files.write(path, lines);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Date getDateOfLine(String line) {
        int i = line.indexOf("|");
        if (i < 0) {
            return null;  //may be Exception later
        }
        String stringDate = line.substring(0, i);
        return this.convertToDate(stringDate);
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
            filePath = "./Data/" + exercise + ".txt";
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

    private List<TrainingSet> getTrainingSets(String exercise, Date date, ExerciseType exerciseType) {
        String filePath = Constants.PathExerciseTextFiles + exercise + ".txt";
        if (exerciseType.equals(ExerciseType.BODYWEIGHT)) {
            filePath = "./Data/" + exercise + ".txt";
        }
        List<TrainingSet> list = new LinkedList<>();
        String line = "";
        int lineNumber = this.getLineOfDate(exercise, date, exerciseType);
        try {
            line = Files.readAllLines(Paths.get(filePath)).get(lineNumber - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] split = line.split("\\|");

        for (int i = 1; i < split.length; i += 2) {
            if (split[i + 1].contains("b")) {
                split[i + 1] = this.calculateActualWeight(date, split[i + 1]);
            }
            list.add(new TrainingSet(Double.valueOf(split[i]), Double.valueOf(split[i + 1])));
        }

        return list;

    }

    public Map<Date, List<TrainingSet>> getStatsBetweenDates
            (String exercise, Date from, Date to, ExerciseType exerciseType) {
        String filepath = "";
        if (exerciseType.equals(ExerciseType.EXERCISE)) {
            filepath = Constants.PathExerciseTextFiles + exercise + ".txt";
        } else if (exerciseType.equals(ExerciseType.BODYWEIGHT)) {
            filepath = "./Data/" + exercise + ".txt";
        }

        Map<Date, List<TrainingSet>> tmpMap = new HashMap<>();
        try {
            String line;
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(filepath));
            while ((line = bufferedReader.readLine()) != null) {
                Date d = this.getDateOfLine(line);
                if (d == null) {
                    break;
                }
                if ((d.after(from) && d.before(to)) || d.equals(from) || d.equals(to)) {
                    tmpMap.put(d, this.getTrainingSets(exercise, d, exerciseType));
                }

            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return (new TreeMap<>(tmpMap));
    }

    public int getHighestSet(Date from, Date to, String exercise, ExerciseType exerciseType) {
        int highestSet = 0;
        Map<Date, List<TrainingSet>> map = this.getStatsBetweenDates(exercise, from, to, exerciseType);
        for (Map.Entry<Date, List<TrainingSet>> m :
                this.getStatsBetweenDates(exercise, from, to, exerciseType).entrySet()) {
            int i = 0;
            for (TrainingSet t : m.getValue()) {
                i++;
            }
            if (i > highestSet) {
                highestSet = i;
            }
        }

        return highestSet;
    }

    public List<TrainingSet> getListOfSet(int set, Date from, Date to, String exercise, ExerciseType exerciseType) {
        List<TrainingSet> listStats = new LinkedList<>();
        for (Map.Entry<Date, List<TrainingSet>> m :
                this.getStatsBetweenDates(exercise, from, to, exerciseType).entrySet()) {
            try {
                listStats.add
                        (new TrainingSet(m.getValue().get(set - 1).getReps(), m.getValue().get(set - 1).getWeight()));
            } catch (IndexOutOfBoundsException e) {
                listStats.add(null);
            }
        }
        return listStats;
    }

    public float getFrequencyPerWeek(Date from, Date to, String exercise) {
        float days = (float) this.calculateStats.calculateDaysBetweenDates(to, from);
        float weeks = days / 7;
        float quantityWorkouts = (float) this.getListOfSet(1, from, to, exercise, ExerciseType.EXERCISE).size();
        return (quantityWorkouts / weeks);

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
        this.changeExerciseOrder(exercises);

        return true;
    }

    //includes bodyweight
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
        Map<Date, Double> weights = this.getWeightMap();

        long days = 0;
        Date chosenDate = null;
        boolean firstDay = true;


        for (Map.Entry<Date, Double> entry : weights.entrySet()) {
            long diff = date.getTime() - entry.getKey().getTime();
            long daysBetweenDates = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
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

    private Map<Date, Double> getWeightMap() {
        Map<Date, Double> weights = new HashMap<>();
        String filepath = "./Data/" + Constants.bodyWeight + ".txt";

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
                String weight = split[split.length - 1];
                weights.put(d, Double.valueOf(weight));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return weights;
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
        this.changeExerciseOrder(exercises);

        return true;
    }

    public String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        String dateToday = dtf.format(now);
        return dateToday;
    }
}
