package models;

public class TrainingSet {
    private String reps;
    private String weight;

    public TrainingSet(String repetitions, String weight) {
        this.reps = repetitions;
        this.weight = weight;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
