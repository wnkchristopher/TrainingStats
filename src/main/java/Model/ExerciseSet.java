package Model;

public class ExerciseSet implements Comparable<ExerciseSet> {
    private String exercise;
    private int set;

    public ExerciseSet(String exercise, int set) {
        if (exercise == null) {
            throw new NullPointerException();
        }
        if (exercise.isEmpty() || set < 1) {
            throw new IllegalArgumentException();
        }
        this.exercise = exercise;
        this.set = set;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public String getExercise() {
        return this.exercise;
    }

    public int getSet() {
        return this.set;
    }

    @Override
    public int compareTo(ExerciseSet o) {
        if (o.getExercise().compareTo(this.exercise) == 0) {
            if (set < o.getSet()) {
                return -1;
            }
            return 1;
        }
        return this.exercise.compareTo(o.getExercise());
    }
}
