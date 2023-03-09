import java.util.Scanner;

public class Squat extends Lift {
    // Copied from BenchPress, not done. 02-26-2023.
    // firstSet, secondSet, thirdSet, fourthSet, fifthSet take percentages of by your working weight as a single, and using the 25RM, 15RM, 12RM, 10RM, 8RM, based off of that, respectively.
    
    private double[] workingWeights = new double[6];

    public Squat() {
        this.weight = 135;
        this.unit = "lb";
    }

    protected boolean highWeight() {
        if (weight >= 375) {
            highWeight = true;
        }
        if (unit.equals("kg")|| (unit.equals("kgs"))) {
            highWeightSet = 60;
        }
        else {
            highWeightSet = 135;
        }
        return this.highWeight;
    }

    @Override
    public void setWorkingWeight(double weight, String unit) throws IllegalWeightException {
        this.weight = weight;
        this.unit = unit;

        switch (unit) {
            case "kg", "kgs" -> {
                this.weight = (int) (weight * 2.20462);
            }
            case "lb", "lbs" -> {
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
        } else if (weight > 225 && weight <= 315) {
            workingSets = 3;
            highWeight();
        } else if (weight > 315 && weight <= 405) {
            workingSets = 4;
            highWeight();
        } else if (weight > 405 && weight <= 495) {
            workingSets = 5;
            highWeight();
        } else if (weight > 495) {
            workingSets = 6;
            highWeight();
        } else {
            throw new IllegalWeightException("Weight must be greater than or equal to 0.");
        }
    }

    @Override
    public void weightMultiplier() throws IllegalWeightException {
        firstSet = (int) checkSetWeight(weight * 0.45);
        workingWeights[0] = firstSet;
        secondSet = (int) checkSetWeight(weight * 0.55);
        workingWeights[0] = secondSet;
        thirdSet = (workingSets >= 2) ? (int) checkSetWeight(weight * 0.67) : 0;
        workingWeights[1] = thirdSet;
        fourthSet = (workingSets >= 3) ? (int) checkSetWeight(weight * 0.71) : 0;
        workingWeights[2] = fourthSet;
        fifthSet = (workingSets >= 4) ? (int) checkSetWeight(weight * 0.75) : 0;
        workingWeights[3] = fifthSet;
        sixthSet = (workingSets >= 5) ? (int) checkSetWeight(weight * 0.81) : 0;
        workingWeights[4] = sixthSet;
    }

    private double checkSetWeight(double setWeight) {
        double roundedWeight = Math.round(setWeight * (1.0 / 2.5)) / (1.0 / 2.5);
        if (roundedWeight < 45) {
            return 45;
        }
        return roundedWeight;
    }
    
    public void initializePlates() {
        if (unit.equals("kg")|| (unit.equals("kgs"))) {
            plates = new float[]{0.45f, 1.13f, 2.27f, 4.5f, 11.35f, 15.9f, 20.4f};
        } else {
            plates = new float[]{1f, 2.5f, 5f, 10f, 25f, 35f, 45f};
        }
        
    }
    public void calculateSets(int barWeight) {
        double measurementMultiplier = 0;
        initializePlates();

        if (unit.equals("kg")|| (unit.equals("kgs"))) {
            measurementMultiplier = 0.45359237;
        } else {
            measurementMultiplier = 1;
        }

        // Initial Warmups
        System.out.println("2x5 " + barWeight + " " + unit + " (Warmup)");
        if (highWeight) {
            System.out.println("1x5 " + highWeightSet + " " + unit + " " + "(Warmup)");
        }

        int reps = 5;
        // Dynamic Warmups
        for (int i = 0; i < workingSets; i++) {
            double weightWithoutBar;
            double weight = workingWeights[i] * measurementMultiplier;
            double roundedWeight = Math.round(weight/5) * 5;
            
            if (roundedWeight == 0) {
                continue;
            }
            if (unit.equals("kg")|| (unit.equals("kgs"))) {
                weightWithoutBar = (roundedWeight - barWeight);
            } else {
                weightWithoutBar = (roundedWeight - barWeight) / 2;
            }

            if (reps <= 2) {
                reps = 2;
            }

            System.out.print("1x" + reps + " " + (roundedWeight % 1 == 0 ? String.format("%.0f", roundedWeight) : String.format("%.2f", roundedWeight)) + " " + unit + " (Warmup)" + " ");
            reps--; reps--;
            boolean hasMoreOutput = false;
            System.out.print("(");
            for (int j = plates.length - 1; j >= 0; j--) {
                int numPlates = (int) (weightWithoutBar / plates[j]);
                if (numPlates > 0) {
                    if (hasMoreOutput) {
                        System.out.print(" ");
                    }
                    System.out.print(numPlates + "x" + (plates[j] % 1 == 0 ? String.format("%.0f", plates[j]) : String.format("%.1f", plates[j])));
                    weightWithoutBar -= numPlates * plates[j];
                    hasMoreOutput = false;
                    for (int k = j - 1; k >= 0; k--) {
                        if ((int) (weightWithoutBar / plates[k]) > 0) {
                            hasMoreOutput = true;
                        }
                    }
                }
            }
            System.out.println(")");
        }

        // Potentiation & Working Sets
        boolean hasMoreOutput = false;
        for (int i = 0; i < 2; i++) {
            double weightWithoutBar = 0.0;
            double workingWeight = weight * measurementMultiplier;
            double roundedWeight = Math.round(workingWeight/5) * 5;
            
            if (unit.equals("kg")|| (unit.equals("kgs"))) {
                weightWithoutBar = (roundedWeight - barWeight);
            } else {
                weightWithoutBar = (roundedWeight - barWeight) / 2;
            }
            if (i == 0) {
                System.out.print("1x2 " + (weight % 1 == 0 ? String.format("%.0f", roundedWeight) : String.format("%.2f", roundedWeight)) + " " + unit + " (Potentiation) ");
                System.out.print("(");
            } else if (i == 1) {
                System.out.print("5x5 " + (weight % 1 == 0 ? String.format("%.0f", roundedWeight) : String.format("%.2f", roundedWeight)) + " " + unit + " (Working Weight) ");
                weightWithoutBar = (weight - barWeight) / 2;
                System.out.print("(");
            }
            
            for (int j = plates.length - 1; j >= 0; j--) {
                int numPlates = (int) (weightWithoutBar / plates[j]);
                if (numPlates > 0) {
                    if (hasMoreOutput) {
                        System.out.print(" ");
                    }
                    System.out.print(numPlates + "x" + (plates[j] % 1 == 0 ? String.format("%.0f", plates[j]) : String.format("%.1f", plates[j])));
                    weightWithoutBar -= numPlates * plates[j];
                    hasMoreOutput = false;
                    for (int k = j - 1; k >= 0; k--) {
                        if ((int) (weightWithoutBar / plates[k]) > 0) {
                            hasMoreOutput = true;
                        }
                    }
                }
            }
            System.out.println(")");
        }
    }

    @Override
    public void printSets(int barWeight) {
        Scanner scanner = new Scanner(System.in);
        // Powerlifting bars can be as heavy as 45 lbs and technique bars as light as 15. Any lighter than 15 and you probably don't need a warmup.
        if (barWeight >= workingWeights[0] || barWeight > 55 || barWeight < 15) {
            while (barWeight >= workingWeights[0] || barWeight > 55 || barWeight < 15) {
                System.out.println("Please enter a valid bar weight.");
                barWeight = scanner.nextInt();
            }
        }
        scanner.close();

        System.out.println("Bench press sets:");
        calculateSets(barWeight);
        System.out.println("Don't be afraid to add in more warmup sets, if needed.");
    }
}