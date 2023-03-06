public class Deadlift extends Lift {
    // firstSet, secondSet, thirdSet, fourthSet, fifthSet take percentages of by your working weight as a single, and using the 42RM, 20RM, 10RM, 8RM, 5RM, based off of that, respectively.
    // Since deadlift working weights tend to be big, it made sense to start with a low percentage that will likely still be a high absolute weight, and gradually
    // increase percentages from there.
    // It does not make sense to include a warmup with just the bar for a deadlift.
    // Without bumper plates on the bar, the range of motion (ROM) of the deadlift will be significant, turning it into a Romanian style deadlift.
    // Instead, we use highWeight. If true, we include a set of 135 (2 45's), to have equal ROM throughout all sets.
    int sixthSet;

    public Deadlift() {
        int weight = 0;
    }

    @Override
    protected boolean highWeight() {
        if (weight >= 340) {
            highWeight = true;
        }
        return this.highWeight;
    }

    @Override
    public void setWorkingWeight(int weight, String unit) throws IllegalWeightException {
        this.weight = weight;
        this.unit = unit;

        switch (unit) {
            case "kg", "kgs" -> {
                metricWeight = weight;
                this.weight = (int) (metricWeight * 2.20462);
            }
            case "lb", "lbs" -> {
                metricWeight = (int) (weight * 0.45359237);
                this.weight = weight;
            }
            default -> throw new IllegalWeightException("Invalid weight.");
        }

        if (weight >= 0 && weight <= 135) {
            workingSets = 2;
            highWeight();
        } else if (weight > 135 && weight <= 185) {
            workingSets = 2;
            highWeight();
        } else if (weight > 185 && weight <= 225) {
            workingSets = 3;
            highWeight();
        } else if (weight > 225 && weight <= 405) {
            workingSets = 4;
            highWeight();
        } else if (weight > 405 && weight <= 585) {
            workingSets = 5;
            highWeight();
        } else if (weight > 585) {
            workingSets = 6;
            highWeight();
        } else {
            throw new IllegalWeightException("Weight must be greater than or equal to 0.");
        }
    }

    @Override
    public void multiplier() throws IllegalWeightException {
        firstSet = (int) checkSetWeight(weight * 0.4);
        secondSet = (workingSets >= 2) ? (int) checkSetWeight(weight * 0.6) : 0;
        thirdSet = (workingSets >= 3) ? (int) checkSetWeight(weight * 0.71) : 0;
        fourthSet = (workingSets >= 4) ? (int) checkSetWeight(weight * 0.75) : 0;
        fifthSet = (workingSets >= 5) ? (int) checkSetWeight(weight * 0.81) : 0;
        sixthSet = (workingSets >= 5) ? (int) checkSetWeight(weight * 0.89) : 0;
    }

    private double checkSetWeight(double setWeight) {
        double roundedWeight = 2.5 * Math.floor(setWeight / 2.5);
        if (roundedWeight < 45) {
            return 45;
        }
        return roundedWeight;
    }

    @Override
    public void printSets(/*int bar*/) {
        System.out.println("Deadlift sets:");
        if (highWeight) {
            System.out.println("1x5 135 lbs (Warmup)");
        }
        System.out.println("1x5 " + firstSet + " lbs (Warmup)");
        if (workingSets >= 2) {
            System.out.println("1x5 " + secondSet + " lbs (Warmup)");
        }
        if (workingSets >= 3) {
            System.out.println("1x5 " + thirdSet + " lbs (Warmup)");
        }
        if (workingSets >= 4) {
            System.out.println("1x3 " + fourthSet + " lbs (Warmup)");
        }
        if (workingSets >= 5) {
            System.out.println("1x2 " + fifthSet + " lbs (Warmup)");
        }
        if (workingSets >= 6) {
            System.out.println("1x1 " + sixthSet + " lbs (Warmup)");
        }
        System.out.println("1x2 " + weight + " lbs (Potentiation)");
        System.out.println("5x5 " + weight + " lbs (Working Weight)");
        System.out.println("Don't be afraid to add in more warmup sets, if needed.");
    }

    @Override
    public void calculateSets() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateSets'");
    }
}
