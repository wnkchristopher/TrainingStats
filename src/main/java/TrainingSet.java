public class TrainingSet {
    private double reps;
    private double weight;

    public TrainingSet(double repetitions, double weight) {
        this.reps = repetitions;
        this.weight = weight;
    }

    public double getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
