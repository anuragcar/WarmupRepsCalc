public abstract class Lift {

    int metricWeight, workingSets;
    double weight, firstSet, secondSet, thirdSet, fourthSet, fifthSet;
    boolean highWeight, isMetric;
    String unit;
    public float [] plates = new float[]{2.5f, 5f, 10f, 25f, 35f, 45f};

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
    public abstract void printSets(int bar);

}
