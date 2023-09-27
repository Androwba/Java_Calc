import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

class Main {
  private static int convertRomanToArabic(String roman) {
    HashMap<Character, Integer> romanToArabic = new HashMap<>();
    romanToArabic.put('I', 1);
    romanToArabic.put('V', 5);
    romanToArabic.put('X', 10);
    romanToArabic.put('L', 50);
    romanToArabic.put('C', 100);
    romanToArabic.put('D', 500);
    romanToArabic.put('M', 1000);

    int arabic = 0;
    int prevValue = 0;
    for (int i = roman.length() - 1; i >= 0; i--) {
      int currValue = romanToArabic.get(roman.charAt(i));
      if (currValue < prevValue) {
        arabic -= currValue;
      } else {
        arabic += currValue;
      }
      prevValue = currValue;
    }
    return arabic;
  }

  private static String convertArabicToRoman(int arabic) {
    int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] romanNumerals = {"M",  "CM", "D",  "CD", "C",  "XC", "L",
                              "XL", "X",  "IX", "V",  "IV", "I"};

    StringBuilder roman = new StringBuilder();
    int remaining = arabic;
    for (int i = 0; i < arabicValues.length; i++) {
      while (remaining >= arabicValues[i]) {
        roman.append(romanNumerals[i]);
        remaining -= arabicValues[i];
      }
    }
    return roman.toString();
  }

  public static String calc(String input) throws IOException {
    if (!input.contains(" ")) {
      throw new IOException(
          "Input must include spaces between operands and operators.");
    }
    String[] parts = input.split(" ");
    if (parts.length != 3) {
      throw new IOException("Invalid input only 2 operands are allowed");
    }
    String operator = parts[1];
    int result = 0;
    boolean isRoman = false;
    if (parts[0].matches("\\d+") && parts[2].matches("\\d+")) {
      int num1 = Integer.parseInt(parts[0]);
      int num2 = Integer.parseInt(parts[2]);

      if (num1 < 1 || num1 > 10 || num2 < 0 || num2 > 10) {
        throw new IOException(
            "Arabic numerals must represent numbers between 1 and 10");
      }

      switch (operator) {
      case "+":
        result = num1 + num2;
        break;
      case "-":
        result = num1 - num2;
        break;
      case "*":
        result = num1 * num2;
        break;
      case "/":
        if (num2 == 0) {
          throw new IOException("Division by zero is not allowed");
        }
        result = num1 / num2;
        break;
      default:
        throw new IOException("Invalid operator");
      }
    } else if (parts[0].matches("[IVXLCDM]+") &&
               parts[2].matches("[IVXLCDM]+")) {
      isRoman = true;
      int num1 = convertRomanToArabic(parts[0]);
      int num2 = convertRomanToArabic(parts[2]);
      switch (operator) {
      case "+":
        result = num1 + num2;
        break;
      case "-":
        result = num1 - num2;
        if (result < 1) {
          throw new IOException("Roman numbers doesn't have negative value");
        }
        break;
      case "*":
        result = num1 * num2;
        break;
      case "/":
        result = num1 / num2;
        break;
      default:
        throw new IOException("Invalid operator");
      }
    } else {
      throw new IOException("Invalid input");
    }
    if (isRoman) {
      return convertArabicToRoman(result);
    } else {
      return Integer.toString(result);
    }
  }

public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    try {
        while (true) {
            System.out.println("Enter operation:");
            String input = scanner.nextLine();
            System.out.println(calc(input));
        }
    } finally {
        scanner.close();
    }
  }
}
