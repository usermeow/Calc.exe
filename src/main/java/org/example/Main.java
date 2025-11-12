package org.example;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Calculator calculator = new Calculator();

    public static void main(String[] args) {
        runCalculator();
        scanner.close();
    }

    private static void runCalculator() {
        System.out.println("=== Калькулятор ===");

        while (true) {
            double number1 = readNumber("Введите число 1: ");
            double number2 = readNumber("Введите число 2: ");
            char operation = readOperation();

            int precision1 = calculator.getDecimalPlaces(number1);
            int precision2 = calculator.getDecimalPlaces(number2);

            int manualPrecision = 0;
            if (operation == '*' || operation == '/') {
                manualPrecision = askManualPrecision();
            }

            Double result = calculator.calculate(number1, number2, operation, precision1, precision2, manualPrecision);

            if (result == null) {
                System.out.println("Ошибка: деление на ноль!");
            } else {
                System.out.println("Результат: " + result);
            }

            if (!askContinue()) {
                System.out.println("Выход из программы.");
                break;
            }
            System.out.println();
        }
    }

    // Методы ввода и утилиты (берём из твоего старого кода)
    private static double readNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            String cleaned = input.replaceAll("[^0-9.,-]", "").replace(',', '.');
            try {
                return Double.parseDouble(cleaned);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число!");
            }
        }
    }

    private static char readOperation() {
        while (true) {
            System.out.print("Выберите операцию (+, -, *, /): ");
            String input = scanner.nextLine().trim();
            if (input.length() == 1 && "+-*/".contains(input)) {
                return input.charAt(0);
            }
            System.out.println("Ошибка: введите один из символов (+, -, *, /)");
        }
    }

    private static int askManualPrecision() {
        System.out.print("Введите количество знаков после запятой (Enter — авто): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return 0;
        try {
            return Math.max(0, Integer.parseInt(input));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static boolean askContinue() {
        System.out.print("Продолжить? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("д");
    }
}
