package org.example;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        runCalculator();
        scanner.close();
    }

    // 🧮 Главный метод: управляет всем процессом
    private static void runCalculator() {
        System.out.println("=== Калькулятор v2.0 ===");

        while (true) {
            double number1 = readNumber("Введите число 1: ");
            double number2 = readNumber("Введите число 2: ");
            char operation = readOperation();

            Double result = calculate(number1, number2, operation);
            if (result != null) {
                printResult(number1, number2, operation, result);
            }

            if (!askContinue()) {
                System.out.println("Программа завершена.");
                break;
            }
            System.out.println();
        }
    }

    // 🔢 Чтение и очистка числа
    private static double readNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            String cleaned = cleanInput(input);

            try {
                double value = Double.parseDouble(cleaned.replace(',', '.'));
                if (!cleaned.equals(input)) {
                    System.out.println("⚠️ Исправлено на: " + cleaned);
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число!");
            }
        }
    }

    // 🧹 Очистка строки от мусора и двойных точек
    private static String cleanInput(String input) {
        String cleaned = input.replaceAll("[^0-9.,-]", "");
        cleaned = fixMultipleDots(cleaned);
        cleaned = cleaned.replace(',', '.');
        return cleaned;
    }

    // ⚙️ Исправление множественных разделителей
    private static String fixMultipleDots(String input) {
        int firstDot = input.indexOf('.');
        if (firstDot == -1) return input;
        String before = input.substring(0, firstDot + 1);
        String after = input.substring(firstDot + 1).replaceAll("[.,]", "");
        return before + after;
    }

    // ➕ Чтение операции
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

    // 🧠 Основная логика вычисления
    private static Double calculate(double num1, double num2, char op) {
        int precision1 = getDecimalPlaces(num1);
        int precision2 = getDecimalPlaces(num2);
        Double result = null;
        int outputPrecision;

        switch (op) {
            case '+':
                result = num1 + num2;
                outputPrecision = Math.max(precision1, precision2);
                break;
            case '-':
                result = num1 - num2;
                outputPrecision = Math.max(precision1, precision2);
                break;
            case '*':
                result = num1 * num2;
                outputPrecision = askManualPrecision(precision1 + precision2);
                break;
            case '/':
                if (num2 == 0) {
                    System.out.println("Ошибка: деление на ноль!");
                    return null;
                }
                result = num1 / num2;
                outputPrecision = askManualPrecision(precision1 + precision2 + 2);
                break;
            default:
                System.out.println("Неизвестная операция.");
                return null;
        }

        printFormattedResult(result, outputPrecision);
        return result;
    }

    // 🎯 Подсчёт количества знаков после запятой (через строку)
    private static int getDecimalPlaces(double number) {
        String text = String.valueOf(number);
        int index = text.indexOf('.');
        if (index == -1) return 0;
        return text.length() - index - 1;
    }

    // 🧩 Запрос пользовательской точности
    private static int askManualPrecision(int defaultPrecision) {
        System.out.print("Введите количество знаков после запятой (Enter — авто): ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) return Math.min(defaultPrecision, 8);

        try {
            int precision = Integer.parseInt(input);
            if (precision < 0) return Math.min(defaultPrecision, 8);
            return Math.min(precision, 12);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Некорректный ввод, использую авто.");
            return Math.min(defaultPrecision, 8);
        }
    }

    // 🖨️ Форматированный вывод результата
    private static void printFormattedResult(Double result, int precision) {
        String format = "%." + precision + "f%n";
        System.out.printf("Результат: " + format, result);
    }

    // 💬 Вывод краткой информации об операции
    private static void printResult(double n1, double n2, char op, double res) {
        System.out.println("----------------------------");
        System.out.printf("Операция: %.6f %c %.6f = %.6f%n", n1, op, n2, res);
        System.out.println("----------------------------");
    }

    // 🔁 Продолжить?
    private static boolean askContinue() {
        System.out.print("Хотите продолжить? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("д");
    }
}
