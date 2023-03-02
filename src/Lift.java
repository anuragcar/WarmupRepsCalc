public abstract class Lift {

    int weight, metricWeight, workingSets;
    double firstSet, secondSet, thirdSet, fourthSet, fifthSet;
    boolean highWeight, isMetric;
    String unit;

    protected abstract boolean highWeight();
    public abstract void setWorkingWeight(int weight, String unit) throws IllegalWeightException;

    public abstract void multiplier() throws IllegalWeightException;
    private double checkSetWeight(double setWeight) {
        double roundedWeight = 5 * Math.floor(setWeight / 5);
        if (roundedWeight < 45) {
            return 45;
        }
        return roundedWeight;
    }
    public abstract void printSets();

}
