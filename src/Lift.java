public abstract class Lift {

    int workingSets;
    double weight, firstSet, secondSet, thirdSet, fourthSet, fifthSet;
    boolean highWeight, isMetric;
    String unit;
    public float [] plates;

    protected abstract boolean highWeight();
    public abstract void setWorkingWeight(int weight, String unit) throws IllegalWeightException;
    public abstract void weightMultiplier() throws IllegalWeightException;
    private double checkSetWeight(double setWeight) {
        double roundedWeight = 5 * Math.floor(setWeight / 5);
        if (roundedWeight < 45) { return 45; }
        return roundedWeight;
    }
    public abstract void calculateSets(int barWeight);
    public abstract void printSets(int barWeight);

}
