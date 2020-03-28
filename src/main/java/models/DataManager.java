package models;

import configuration.Constants;
import database.DataBaseManager;
import pdfGeneration.CalculateStats;
import enums.ExerciseType;
import com.sun.javafx.scene.traversal.Direction;
import java.util.*;
import java.util.stream.Collectors;

public class DataManager extends Observable {
    private CalculateStats calculateStats;
    private DataBaseManager dataBaseManager;
    private String newExercise;
    private String deletedExercise;


    public DataManager() {
        this.calculateStats = new CalculateStats();
        this.dataBaseManager = new DataBaseManager();
        this.setChanged();
    }

    public List<String> getExercises() {
        return this.dataBaseManager.getExerciseList();
    }

    public void addExercise(String exercise) {
        this.dataBaseManager.addNewExercise(exercise);
        this.newExercise = exercise;

        this.setChanged();
        this.notifyObservers(Constants.AddedExercise);
    }

    public void changeExerciseOrder(String exercise, Direction direction) {
        List<String> exercises = this.dataBaseManager.getExerciseList();
        int index = exercises.indexOf(exercise);
        if (direction == Direction.UP && index > 0) {
            Collections.swap(exercises, index, index - 1);
        } else if (direction == Direction.DOWN && index + 1 < exercises.size()) {
            Collections.swap(exercises, index, index + 1);
        }

        this.dataBaseManager.writeExerciseList(exercises);

        this.setChanged();
        this.notifyObservers(Constants.ChangedOrder);
    }


    public void addWorkout(String exercise, Date dateOfTraining, List<TrainingSet> sets) {
        if (!sets.isEmpty()) {
            this.dataBaseManager.writeExerciseStats(exercise, dateOfTraining, sets, ExerciseType.EXERCISE);
        }
    }

    public void addWeight(Date dateOfWeight, Double weight) {
        List<TrainingSet> sets = new ArrayList<>();
        sets.add(new TrainingSet("0", String.valueOf(weight)));
        this.dataBaseManager.writeExerciseStats("", dateOfWeight, sets, ExerciseType.BODYWEIGHT);
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

    private List<TrainingSet> getTrainingSets(String exercise, Date date, ExerciseType exerciseType) { //Should use getExerciseStats and search for specific date

        List<TrainingSet> sets = new LinkedList<>(); //Contains bodyweight
        if (exerciseType.equals(ExerciseType.EXERCISE)) {
            sets = this.dataBaseManager.getExerciseStats(exercise).get(date);
        }

        return sets;

    }

    public Map<Date, Double> getWeightsBetweenDates(Date from, Date to) {
        Map<Date, Double> weights = this.dataBaseManager.getWeights();
        weights.entrySet().stream().filter(m -> DateManager.isDateBetween(m.getKey(), from, to))
                .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));

        return (new TreeMap<>(weights));
    }

    public Map<Date, List<TrainingSet>> getStatsBetweenDates
            (String exercise, Date from, Date to, ExerciseType exerciseType) {  //Should use getExerciseStats
        if(exerciseType == ExerciseType.BODYWEIGHT) {
            Map<Date, List<TrainingSet>> weights = new TreeMap<>();
            for(Map.Entry<Date, Double> entry: this.getWeightsBetweenDates(from, to).entrySet()){
                List<TrainingSet> list = new ArrayList<>();
                list.add(new TrainingSet("0", String.valueOf(entry.getValue())));
                weights.put(entry.getKey(), list);
            }
            return weights;
        }

        Map<Date, List<TrainingSet>> sets = this.dataBaseManager.getExerciseStats(exercise);

        //filters map
        sets = sets.entrySet().stream().filter(m -> DateManager.isDateBetween(m.getKey(), from, to))
                .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));

        //sorts map
        return (new TreeMap<>(sets));
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

    public float getFrequencyPerWeek(Date from, Date to, String exercise) {
        float days = (float) this.calculateStats.calculateDaysBetweenDates(to, from);
        float weeks = days / 7;
        float quantityWorkouts = (float) this.getStatsBetweenDates(exercise, from, to, ExerciseType.EXERCISE).size();
        return (quantityWorkouts / weeks);

    }

    public double getWeight(Date date) {
        return this.dataBaseManager.getWeight(date);
    }

    public boolean deleteExercise(String exercise) {
        if(this.dataBaseManager.deleteExercise(exercise)){
            this.deletedExercise = exercise;
            this.setChanged();
            this.notifyObservers(Constants.DeletedExercise);
            return true;
        }
        return false;
    }


    private boolean checkIfExerciseExists(String exercise) {
        return this.getExercises().contains(exercise);
    }

    public boolean changeExerciseName(String exercise, String newName) {
        if(this.dataBaseManager.changeExerciseName(exercise, newName)){
            this.newExercise = newName;
            this.deletedExercise = exercise;
            this.setChanged();
            this.notifyObservers(Constants.RenamedExercise);
            return true;
        }
        return false;
    }

    public String getNewExercise() {
        return newExercise;
    }

    public String getDeletedExercise() {
        return deletedExercise;
    }
}
