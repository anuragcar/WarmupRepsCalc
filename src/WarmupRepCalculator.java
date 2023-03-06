import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
5x5 warmup reps calculator for the big three compound movements.
Calculations are based on Dr. Mike Israetel of Renaissance Periodization's video titled "How to Warm Up for Muscle Growth Training | Hypertrophy Made Simple #3" (youtube.com/watch?v=HDq-68SlPgQ).
Don't be afraid to add in more warmup sets, if needed.

To-do:
1. Code cleanup
2. Add custom bar weights.
3. Add UI (JavaFX).
*/

public class WarmupRepCalculator {
    public static void main(String[] args) throws IllegalWeightException {
        Scanner scanner = new Scanner(System.in);

        int weight = 0;
        String userExercise, userExerciseFirstLetter, userExerciseRest, unit = "";
        Object exerciseObject = null;
        boolean inputValid = false;

        while (!inputValid) {
            System.out.println("Select: Squat, Bench, Deadlift");
            userExercise = scanner.nextLine();
            userExerciseFirstLetter = userExercise.substring(0, 1).toUpperCase();
            userExerciseRest = userExercise.substring(1).toLowerCase();
            userExercise = userExerciseFirstLetter + userExerciseRest;

            try {
                // Dynamically instantiates the selected class by user input
                Class<?> exerciseClass = Class.forName(userExercise);
                Constructor<?> exerciseConstructor = exerciseClass.getConstructor();
                exerciseObject = exerciseConstructor.newInstance();
                inputValid = true;
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     InvocationTargetException | NoSuchMethodException | NoClassDefFoundError e) {
                System.out.println("Invalid exercise selected.");
            }
        }

        while (!unit.equals("kg") && !unit.equals("lb")) {
            System.out.println("Select your measurement system (kg or lb):");
            unit = scanner.next();
            if (!unit.equals("kg") && !unit.equals("lb")) {
                System.out.println("Invalid measurement system selected.");
            }
        }
        
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter working weight: ");
            try {
                weight = scanner.nextInt();
                if (weight < 0) {
                    throw new IllegalWeightException("Weight cannot be negative.");
                }
                validInput = true;
            } catch (InputMismatchException | IllegalWeightException e) {
                System.out.println("Weight has to be entered as an integer.");
            }
        }

        /*
        int bar = 0;
        boolean barValid = false;
        while (!barValid) {
            System.out.print("Enter bar weight: ");
            try {
                bar = scanner.nextInt();
                if (weight < 0) {
                    throw new IllegalWeightException("Weight cannot be negative.");
                }
                barValid = true;
            } catch (InputMismatchException | IllegalWeightException e) {
                System.out.println("Weight has to be entered as an integer.");
            }
        }
        */

        scanner.close();
        
        // Use reflection to call methods on objects
        try {
            Method setWeightMethod = exerciseObject.getClass().getMethod("setWorkingWeight", int.class, String.class);
            setWeightMethod.invoke(exerciseObject, weight, unit);

            Method multiplierMethod = exerciseObject.getClass().getMethod("multiplier");
            multiplierMethod.invoke(exerciseObject);

            Method printSetsMethod = exerciseObject.getClass().getMethod("printSets"/*, int.class*/);
            printSetsMethod.invoke(exerciseObject/*, bar*/);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error setting working weight: " + e.getMessage());
        }
    }
}