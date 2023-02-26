import java.util.Scanner;

public class WarmupRepCalculator {
    public static void main(String[] args) throws IllegalWeightException {
        Scanner scanner = new Scanner(System.in);

        BenchPress bench = new BenchPress();

        int weight = 0;
        boolean inputValid = false;
        while (!inputValid) {
            System.out.print("Enter working weight: ");
            weight = scanner.nextInt();
            try {
                bench.setWorkingWeight(weight);
                inputValid = true;
            } catch (IllegalWeightException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        bench.multiplier();

        System.out.println("2x5 45");
        System.out.println("1x5 " + bench.firstSet);
        if (bench.workingSets >= 2) {
            System.out.println("1x5 " + bench.secondSet);
        }
        if (bench.workingSets >= 3) {
            System.out.println("1x5 " + bench.thirdSet);
        }
        System.out.println("5x5 " + weight);
    }
}