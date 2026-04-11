public class Exo1 {
  public static void main(String[] args) {
    int n = 100;
    int count = 0;
    int maxPerfect = 0;

    for (int i = 1; i <= n; i++) {
      int sum = 0;
      String divisors = "";

      for (int j = 1; j <= i / 2; j++) {
        if (i % j == 0) {
          sum += j;
          divisors += j + " ";
        }
      }

      if (sum == i && i != 0) {
        count++;
        maxPerfect = i;

        System.out.println("Perfect number: " + i);
        System.out.println("Divisors: " + divisors);
        System.out.println();
      }
    }

    System.out.println("Total perfect numbers: " + count);
    System.out.println("Largest perfect number: " + maxPerfect);
  }
}
