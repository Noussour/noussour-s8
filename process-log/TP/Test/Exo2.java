public class Exo2 {
  public static void main(String[] args) {
    int[] tab = { 4, 7, 2, 9, 7, 5, 2, 8 };

    System.out.println("Occurrences:");
    for (int i = 0; i < tab.length; i++) {
      int count = 0;
      boolean alreadyCounted = false;

      for (int k = 0; k < i; k++) {
        if (tab[i] == tab[k]) {
          alreadyCounted = true;
          break;
        }
      }

      if (!alreadyCounted) {
        for (int j = 0; j < tab.length; j++) {
          if (tab[i] == tab[j]) {
            count++;
          }
        }
        System.out.println(tab[i] + " -> " + count);
      }
    }

    int maxCount = 0;
    int mostFrequent = tab[0];

    for (int i = 0; i < tab.length; i++) {
      int count = 0;

      for (int j = 0; j < tab.length; j++) {
        if (tab[i] == tab[j]) {
          count++;
        }
      }

      if (count > maxCount) {
        maxCount = count;
        mostFrequent = tab[i];
      }
    }

    System.out.println("Most frequent element: " + mostFrequent);

    System.out.println("Unique elements:");
    for (int i = 0; i < tab.length; i++) {
      int count = 0;

      for (int j = 0; j < tab.length; j++) {
        if (tab[i] == tab[j]) {
          count++;
        }
      }

      if (count == 1) {
        System.out.print(tab[i] + " ");
      }
    }
    System.out.println();

    for (int i = 0; i < tab.length / 2; i++) {
      int temp = tab[i];
      tab[i] = tab[tab.length - 1 - i];
      tab[tab.length - 1 - i] = temp;
    }

    System.out.print("Reversed array: ");
    for (int i = 0; i < tab.length; i++) {
      System.out.print(tab[i] + " ");
    }
    System.out.println();

    for (int i = 0; i < tab.length - 1; i++) {
      for (int j = i + 1; j < tab.length; j++) {
        if (tab[i] > tab[j]) {
          int temp = tab[i];
          tab[i] = tab[j];
          tab[j] = temp;
        }
      }
    }

    System.out.print("Sorted array: ");
    for (int i = 0; i < tab.length; i++) {
      System.out.print(tab[i] + " ");
    }
  }
}
