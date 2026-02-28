public class Factorial {

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Please provide a number as an argument.");
      return;
    }

    try {
      int number = Integer.parseInt(args[0]);

      if (number < 0) {
        System.out.println("Factorial is not defined for negative numbers.");
        return;
      }

      long resultIterative = factorialIterative(number);
      long resultRecursive = factorialRecursive(number);

      System.out.println("Iterative factorial of " + number + " is: " + resultIterative);
      System.out.println("Recursive factorial of " + number + " is: " + resultRecursive);

    } catch (NumberFormatException e) {
      System.out.println("Invalid number format. Please enter a valid integer.");
    } catch (ArithmeticException e) {
      System.out.println("Overflow occurred! Number is too large to compute factorial.");
    } catch (Exception e) {
      System.out.println("An unexpected error occurred: " + e.getMessage());
    }
  }

  // Iterative factorial using long
  public static long factorialIterative(int n) {
    long result = 1;
    for (int i = 2; i <= n; i++) {
      result = Math.multiplyExact(result, i);
    }
    return result;
  }

  // Recursive factorial using long
  public static long factorialRecursive(int n) {
    if (n == 0 || n == 1) {
      return 1;
    }
    return Math.multiplyExact(n, factorialRecursive(n - 1)); // Handles overflow
  }
}
