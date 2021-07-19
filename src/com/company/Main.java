package com.company;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    static String[] Roman  = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    static String[] Arabic  = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    public static void main(String[] args) {
        ConverterToArabic converterToArabic = new ConverterToArabic();
        ConverterToRoman converterToRoman = new ConverterToRoman();

        try{
            Scanner in = new Scanner(System.in);
            System.out.print("Введите выражение: ");
            String expression = in.nextLine();
            String[] Symbols = expression.split(" ");

            int num1 = 0;
            int num2 = 0;

            if (Symbols.length != 3) {
                throw new Exception("Введен неверный формат выражения (пример: 2 + 3 или I * X)");
            }

            if ((IsAllowedArabic(Symbols[0]) == true & IsAllowedArabic(Symbols[2]) != true) | (IsAllowedRoman(Symbols[0]) == true & IsAllowedRoman(Symbols[2]) != true)) {
                throw new Exception("Оба числа должны быть арабскими или римскими и входить в диапозон от 1 до 10");
            }

            if (IsAllowedRoman(Symbols[0]) == true) {
                num1 = converterToArabic.toArabic(Symbols[0]);
                num2 = converterToArabic.toArabic(Symbols[2]);
            } else {
                num1 = Integer.parseInt(Symbols[0]);
                num2 = Integer.parseInt(Symbols[2]);
            }

            double result = 0;

            switch (Symbols[1]){
                case "+":
                    result = num1 + num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    double doubleNum1 = num1;
                    double doubleNum2 = num2;
                    result = doubleNum1 / doubleNum2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                default:
                    throw new Exception("Выбрана неверная арифметическая операция");
            }

            if (IsAllowedRoman(Symbols[0]) == true) {
                System.out.println("Результат: " + converterToRoman.toRoman((int) result));
            } else {
                System.out.println("Результат: " + result);
            }

        }

        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    static boolean IsAllowedArabic(String Symbol) {
        return Arrays.asList(Arabic).contains(Symbol);
    }

    static boolean IsAllowedRoman(String Symbol) {
        return Arrays.asList(Roman).contains(Symbol);
    }
}

class ConverterToRoman {
    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    public final static String toRoman(int number) {
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }
}

class ConverterToArabic {
    private final static TreeMap<String, Integer> map = new TreeMap<String, Integer>();

    static {
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
    }

    static String[] digits = {"I", "V", "X", "L", "C", "D", "M"};

    public final static int toArabic(String number) {
        number = number.toUpperCase();
        String[] romans = number.split("");
        int res = 0;

        for (int i = 0; i < romans.length; i++) {
            if (Arrays.asList(digits).indexOf(romans[i]) < Arrays.asList(digits).indexOf(romans[Math.min(romans.length - 1, i + 1)])) {
                res -= map.get(romans[i]);
            } else {
                res += map.get(romans[i]);
            }
        }
        return res;
    }
}
