import java.util.Objects;

public class Bench extends Lift {

    // firstSet, secondSet, thirdSet, fourthSet, fifthSet take percentages of by your working weight as a single, and using the 25RM, 15RM, 12RM, 10RM, 8RM, based off of that, respectively.

    private double[] workingSetWeights = new double[5];

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
        workingSetWeights[0] = firstSet;
        secondSet = (workingSets >= 2) ? (int) checkSetWeight(weight * 0.67) : 0;
        workingSetWeights[1] = secondSet;
        thirdSet = (workingSets >= 3) ? (int) checkSetWeight(weight * 0.71) : 0;
        workingSetWeights[2] = thirdSet;
        fourthSet = (workingSets >= 4) ? (int) checkSetWeight(weight * 0.75) : 0;
        workingSetWeights[3] = fourthSet;
        fifthSet = (workingSets >= 5) ? (int) checkSetWeight(weight * 0.81) : 0;
        workingSetWeights[4] = fifthSet;
    }

    private double checkSetWeight(double setWeight) {
        double roundedWeight = 2.5 * Math.floor(setWeight / 2.5);
        if (roundedWeight < 45) {
            return 45;
        }
        return roundedWeight;
    }

    @Override
    public void printSets(int barWeight) {
        System.out.println("Bench press sets:");

        if (Objects.equals(unit, "kg") || Objects.equals(unit, "kgs")) {
            System.out.println("2x5 20 kg (Warmup)");
            if (highWeight) {
                System.out.println("1x5 60 kg (Warmup)");
            }
        } else {
            System.out.println("2x5 45 lbs (Warmup)");

            if (highWeight) {
                System.out.println("1x5 135 lbs (Warmup)");
            }
        }
        for (int i = 0; i < workingSets; i++) {
            
            if (Objects.equals(unit, "kg") || Objects.equals(unit, "kgs")) {
                System.out.println("1x5 " + (int) (Math.floor(workingSetWeights[i] / 2.20462)) + " kgs (Warmup)");
            } else {        
                System.out.print("1x5 " + (workingSetWeights[i] % 1 == 0 ? String.format("%.0f", workingSetWeights[i]) : String.format("%.2f", workingSetWeights[i])) + " lbs (Warmup)" + " ");
                double weightWithoutBar = (weight - barWeight) / 2;
                boolean hasMoreOutput = false;
                System.out.print("(");
                for (int j = plates.length - 1; j >= 0; j--) {
                    int numPlates = (int) (weightWithoutBar / plates[j]);
                    if (numPlates > 0) {
                        if (hasMoreOutput) {
                            System.out.print(" ");
                        }
                        System.out.print(numPlates + "x" + plates[j]);
                        weightWithoutBar -= numPlates * plates[j];
                        hasMoreOutput = false;
                        for (int k = j - 1; k >= 0; k--) {
                            if ((int) (weightWithoutBar / plates[k]) > 0) {
                                hasMoreOutput = true;
                                break;
                            }
                        }
                    }
                }
                System.out.println(")");
            }
        }

        if (Objects.equals(unit, "kg") || Objects.equals(unit, "kgs")) {
            System.out.println("1x2 " + metricWeight + " kg (Potentiation)");
            System.out.println(")");
            System.out.println("5x5 " + metricWeight + " kg (Working Weight)");
            System.out.println("Don't be afraid to add in more warmup sets, if needed.");
        } else {
            double weightWithoutBar = (weight - barWeight) / 2;
            boolean hasMoreOutput = false;
            String workingWeightPlates = "";
            for (int a = 0; a < 2; a++) {
                if (a == 0) {
                    System.out.print("1x2 " + (weight % 1 == 0 ? String.format("%.0f", weight) : String.format("%.2f", weight)) + " lbs (Potentiation) ");
                    System.out.print("(");
                }
                else if (a == 1) {
                    System.out.print("5x5 " + (weight % 1 == 0 ? String.format("%.0f", weight) : String.format("%.2f", weight)) + " lbs (Working Weight) ");
                    weightWithoutBar = (weight - barWeight) / 2;
                    System.out.print("(");
                }

                for (int j = plates.length - 1; j >= 0; j--) {
                    int numPlates = (int) (weightWithoutBar / plates[j]);
                    if (numPlates > 0) {
                        if (hasMoreOutput) {
                            System.out.print(" ");
                        }
                        workingWeightPlates = numPlates + "x" + plates[j];
                        System.out.print(workingWeightPlates.trim());
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
                hasMoreOutput = false;
            }
            System.out.println("Don't be afraid to add in more warmup sets, if needed.");
        }


        /*
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
            int barWeight = 45;        
            float [] plates = new float[]{2.5f, 5f, 10f, 25f, 35f, 45f};

            System.out.println("2x5 45 lbs (Warmup)");

            if (highWeight) {
                System.out.println("1x5 135 lbs (Warmup)");
            }

            System.out.println("1x5 " + (firstSet % 1 == 0 ? String.format("%.0f", firstSet) : String.format("%.2f", firstSet)) + " lbs (Warmup)");

            if (workingSets >= 2) {
                System.out.print("1x5 " + (secondSet % 1 == 0 ? String.format("%.0f", secondSet) : String.format("%.2f", secondSet)) + " lbs (Warmup)" + " ");

                double weightWithoutBar = (weight - barWeight) / 2;
                boolean hasMoreOutput = false;
                System.out.print("(");
                for (int i = plates.length - 1; i >= 0; i--) {
                    int numPlates = (int) (weightWithoutBar / plates[i]);
                    if (numPlates > 0) {
                        if (hasMoreOutput) {
                            System.out.print(" ");
                        }
                        System.out.print(numPlates + "x" + plates[i]);
                        weightWithoutBar -= numPlates * plates[i];
                        hasMoreOutput = false;
                        for (int j = i - 1; j >= 0; j--) {
                            if ((int) (weightWithoutBar / plates[j]) > 0) {
                                hasMoreOutput = true;
                                break;
                            }
                        }
                    }
                }
                System.out.println(")");
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
*/
        }
    }