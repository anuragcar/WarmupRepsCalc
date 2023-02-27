public class BenchPress extends Lift {

    // firstSet, secondSet, thirdSet, fourthSet, fifthSet take percentages of by your working weight as a single, and using the 25RM, 15RM, 12RM, 10RM, 8RM, based off of that, respectively.
    int weight, workingSets, firstSet, secondSet, thirdSet, fourthSet, fifthSet;

    public BenchPress() {
        int weight = 0;
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
        } else if (weight > 500) {
            workingSets = 5;
        } else {
            throw new IllegalWeightException("Weight must be greater than or equal to 0.");
        }
    }

    public void multiplier() throws IllegalWeightException {
        firstSet = (int) checkSetWeight(weight * 0.55);
        secondSet = (workingSets >= 2) ? (int) checkSetWeight(weight * 0.67) : 0;
        thirdSet = (workingSets >= 3) ? (int) checkSetWeight(weight * 0.71) : 0;
        fourthSet = (workingSets >= 4) ? (int) checkSetWeight(weight * 0.75) : 0;
        fifthSet = (workingSets >= 5) ? (int) checkSetWeight(weight * 0.81) : 0;
    }

    private double checkSetWeight(double setWeight) {
        double roundedWeight = 5 * Math.floor(setWeight / 5);
        if (roundedWeight < 45) {
            return 45;
        }
        return roundedWeight;
    }

    public void printSets() {
        System.out.println("Bench press sets:");
        System.out.println("2x5 45 lbs (Warmup)");
        System.out.println("1x5 " + firstSet + " lbs (Warmup)");
        if (workingSets >= 2) {
            System.out.println("1x5 " + secondSet + " lbs (Warmup)");
        }
        if (workingSets >= 3) {
            System.out.println("1x5 " + thirdSet + " lbs (Warmup)");
        }
        if (workingSets >= 4) {
            System.out.println("1x5 " + fourthSet + " lbs (Warmup)");
        }
        if (workingSets >= 5) {
            System.out.println("1x5 " + fifthSet + " lbs (Warmup)");
        }
        System.out.println("1x2 " + weight + " lbs (Potentiation)");
        System.out.println("5x5 " + weight + " lbs (Working Weight)");
        System.out.println("Don't be afraid to add in more warmup sets, if needed.");
    }
}
