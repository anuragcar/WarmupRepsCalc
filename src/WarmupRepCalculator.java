import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/*
Need to add:
1. Metric units
2. Remember previous workouts
3. Add other exercises
4. Add plates needed for each weight
5. Add UI
* */

/*
5x5 warmup reps calculator for the big three compound movements.
Calculations are based on Dr. Mike Israetel of Renaissance Periodization's video titled "How to Warm Up for Muscle Growth Training | Hypertrophy Made Simple #3."
Video link: youtube.com/watch?v=HDq-68SlPgQ
Don't be afraid to add in more warmup sets, if needed.
*/

public class WarmupRepCalculator {
    public static void main(String[] args) throws IllegalWeightException {
        Scanner scanner = new Scanner(System.in);

        int weight = 0;
        String userExercise;
        Object exerciseObject = null;
        boolean inputValid = false;

        while (!inputValid) {
            System.out.println("Select: Squat, BenchPress, Deadlift (CASE SENSITIVE)");
            userExercise = scanner.nextLine();
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

        System.out.print("Enter working weight: ");
        weight = scanner.nextInt();

        // Use reflection to call methods on objects
        try {
            Method setWeightMethod = exerciseObject.getClass().getMethod("setWorkingWeight", int.class);
            setWeightMethod.invoke(exerciseObject, weight);

            Method multiplierMethod = exerciseObject.getClass().getMethod("multiplier");
            multiplierMethod.invoke(exerciseObject);

            Method printSetsMethod = exerciseObject.getClass().getMethod("printSets");
            printSetsMethod.invoke(exerciseObject);
        } catch (Exception e) {
            System.out.println("Error setting working weight: " + e.getMessage());
        }
    }
}