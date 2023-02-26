public class BenchPress {

    int weight, workingSets, firstSet, secondSet, thirdSet, fourthSet;

    public BenchPress() {

    }

    public void setWorkingWeight(int weight) throws IllegalWeightException {
        this.weight = weight;

        if (weight >= 0 && weight <= 50) {
            workingSets = 1;
        } else if (weight > 50 && weight <= 200) {
            workingSets = 2;
        } else if (weight > 200 && weight <= 400) {
            workingSets = 3;
        } else if (weight > 400 && weight <= 500) {
            workingSets = 4;
        } else {
            throw new IllegalWeightException("Weight must be greater than or equal to 0.");
        }
    }

    public void multiplier() throws IllegalWeightException {
        firstSet = (int) checkSetWeight(weight * 0.5);
        secondSet = (workingSets >= 2) ? (int) checkSetWeight(weight * 0.67) : 0;
        thirdSet = (workingSets >= 3) ? (int) checkSetWeight(weight * 0.8) : 0;
        fourthSet = (workingSets >= 4) ? (int) checkSetWeight(weight * 0.8) : 0;
    }

    private double checkSetWeight(double setWeight) {
        double roundedWeight = 5 * Math.floor(setWeight / 5);
        if (roundedWeight < 45) {
            return 45;
        }
        return roundedWeight;
    }
}
