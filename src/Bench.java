import java.util.Objects;

public class Bench extends Lift {

    // firstSet, secondSet, thirdSet, fourthSet, fifthSet take percentages of by your working weight as a single, and using the 25RM, 15RM, 12RM, 10RM, 8RM, based off of that, respectively.

    public Bench() {
        this.weight = 0;
    }

    @Override
    protected boolean highWeight() {
        if (weight >= 245) {
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

        if (weight >= 0 && weight <= 45) {
            workingSets = 1;
            highWeight();
        } else if (weight > 45 && weight <= 225) {
            workingSets = 2;
            highWeight();
        } else if (weight > 225 && weight <= 405) {
            workingSets = 3;
            highWeight();
        } else if (weight > 405 && weight <= 495) {
            workingSets = 4;
            highWeight();
        } else if (weight > 495) {
            workingSets = 5;
            highWeight();
        } else {
            throw new IllegalWeightException("Weight must be greater than or equal to 0.");
        }
    }

    @Override
    public void multiplier() throws IllegalWeightException {
        firstSet = (int) checkSetWeight(weight * 0.55);
        secondSet = (workingSets >= 2) ? (int) checkSetWeight(weight * 0.67) : 0;
        thirdSet = (workingSets >= 3) ? (int) checkSetWeight(weight * 0.71) : 0;
        fourthSet = (workingSets >= 4) ? (int) checkSetWeight(weight * 0.75) : 0;
        fifthSet = (workingSets >= 5) ? (int) checkSetWeight(weight * 0.81) : 0;
    }

    private double checkSetWeight(double setWeight) {
        double roundedWeight = 2.5 * Math.floor(setWeight / 2.5);
        if (roundedWeight < 45) {
            return 45;
        }
        return roundedWeight;
    }

    @Override
    public void printSets() {
        System.out.println("Bench press sets:");
        if (Objects.equals(unit, "kg") || Objects.equals(unit, "kgs")) {
            System.out.println("2x5 20 kg (Warmup)");
            if (highWeight) {
                System.out.println("1x5 60 kg (Warmup)");
            }
            System.out.println("1x5 " + (int) (Math.floor(firstSet / 2.20462)) + " kg (Warmup)");
            if (workingSets >= 2) {
                System.out.println("1x5 " + (int) (Math.floor(secondSet / 2.20462)) + " kg (Warmup)");
            }
            if (workingSets >= 3) {
                System.out.println("1x5 " + (int) (Math.floor(thirdSet / 2.20462)) + " kg (Warmup)");
            }
            if (workingSets >= 4) {
                System.out.println("1x5 " + (int) (Math.floor(fourthSet / 2.20462)) + " kg (Warmup)");
            }
            if (workingSets >= 5) {
                System.out.println("1x5 " + (int) (Math.floor(fifthSet / 2.20462)) + " kg (Warmup)");
            }
            System.out.println("1x2 " + metricWeight + " kg (Potentiation)");
            System.out.println("5x5 " + metricWeight + " kg (Working Weight)");
            System.out.println("Don't be afraid to add in more warmup sets, if needed.");
        } else {
            System.out.println("2x5 45 lbs (Warmup)");
            if (highWeight) {
                System.out.println("1x5 135 lbs (Warmup)");
            }
            System.out.println("1x5 " + (firstSet % 1 == 0 ? String.format("%.0f", firstSet) : String.format("%.2f", firstSet)) + " lbs (Warmup)");
            if (workingSets >= 2) {
                System.out.println("1x5 " + (secondSet % 1 == 0 ? String.format("%.0f", secondSet) : String.format("%.2f", secondSet)) + " lbs (Warmup)");
            }
            if (workingSets >= 3) {
                System.out.println("1x5 " + (thirdSet % 1 == 0 ? String.format("%.0f", thirdSet) : String.format("%.2f", thirdSet)) + " lbs (Warmup)");
            }
            if (workingSets >= 4) {
                System.out.println("1x5 " + (fourthSet % 1 == 0 ? String.format("%.0f", fourthSet) : String.format("%.2f", fourthSet)) + " lbs (Warmup)");
            }
            if (workingSets >= 5) {
                System.out.println("1x5 " + (fifthSet % 1 == 0 ? String.format("%.0f", fifthSet) : String.format("%.2f", fifthSet)) + " lbs (Warmup)");
            }
            System.out.println("1x2 " + (weight % 1 == 0 ? String.format("%.0f", weight) : String.format("%.2f", weight)) + " lbs (Potentiation)");
            System.out.println("5x5 " + (weight % 1 == 0 ? String.format("%.0f", weight) : String.format("%.2f", weight)) + " lbs (Working Weight)");
            System.out.println("Don't be afraid to add in more warmup sets, if needed.");
        }
    }
}