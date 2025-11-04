package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            double[] numbers = new double[2];
            String[] inputs = new String[2];

            inputs[0] = readCleanInput(scanner, "Введите число 1: ");
            inputs[1] = readCleanInput(scanner, "Введите число 2: ");

            numbers[0] = Double.parseDouble(inputs[0].replace(',', '.'));
            numbers[1] = Double.parseDouble(inputs[1].replace(',', '.'));

            int precision1 = getDecimalPlaces(inputs[0]);
            int precision2 = getDecimalPlaces(inputs[1]);

            System.out.print("Выберите операцию (+, -, *, /): ");
            char operation = scanner.next().charAt(0);
            scanner.nextLine(); // очистка буфера

            Double result = null;
            int outputPrecision = 0;

            switch (operation) {
                case '+':
                    result = numbers[0] + numbers[1];
                    outputPrecision = Math.max(precision1, precision2);
                    break;

                case '-':
                    result = numbers[0] - numbers[1];
                    outputPrecision = Math.max(precision1, precision2);
                    break;

                case '*':
                    result = numbers[0] * numbers[1];
                    outputPrecision = askManualPrecision(scanner, precision1 + precision2);
                    break;

                case '/':
                    if (numbers[1] == 0) {
                        System.out.println("Ошибка: деление на ноль!");
                    } else {
                        result = numbers[0] / numbers[1];
                        outputPrecision = askManualPrecision(scanner, precision1 + precision2 + 2);
                    }
                    break;

                default:
                    System.out.println("Неизвестная операция!");
                    break;
            }

            if (result != null) {
                String format = "%." + outputPrecision + "f%n";
                System.out.printf("Результат: " + format, result);
            }

            System.out.print("Хотите продолжить? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals("n")) {
                System.out.println("Программа завершена.");
                break;
            }

            System.out.println();
        }

        scanner.close();
    }

    // ✅ Чтение и очистка пользовательского ввода
    private static String readCleanInput(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            String cleaned = input.replaceAll("[^0-9.,-]", "");
            cleaned = fixMultipleDots(cleaned);
            cleaned = cleaned.replace(',', '.');

            if (cleaned.isEmpty() || cleaned.equals("-") || cleaned.equals(".")) {
                System.out.println("Введите корректное число!");
                continue;
            }

            try {
                Double.parseDouble(cleaned);
                if (!cleaned.equals(input)) {
                    System.out.println("⚠️ Введено с опечатками, использую число: " + cleaned);
                }
                return cleaned;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: невозможно распознать число. Попробуйте снова.");
            }
        }
    }

    // ✅ Исправляем ввод, если много точек/запятых
    private static String fixMultipleDots(String input) {
        int firstDot = input.indexOf('.');
        int firstComma = input.indexOf(',');

        int firstSeparator = -1;
        if (firstDot != -1 && firstComma != -1)
            firstSeparator = Math.min(firstDot, firstComma);
        else if (firstDot != -1)
            firstSeparator = firstDot;
        else if (firstComma != -1)
            firstSeparator = firstComma;

        if (firstSeparator != -1) {
            String before = input.substring(0, firstSeparator + 1);
            String after = input.substring(firstSeparator + 1).replaceAll("[.,]", "");
            return before + after;
        }

        return input;
    }

    // ✅ Подсчёт количества цифр после запятой
    private static int getDecimalPlaces(String number) {
        number = number.replace(',', '.');
        int index = number.indexOf('.');
        if (index == -1) return 0;
        return number.length() - index - 1;
    }

    // ✅ Ввод точности вручную (только для * и /)
    private static int askManualPrecision(Scanner scanner, int defaultPrecision) {
        System.out.print("Введите количество знаков после запятой (Enter — авто): ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return Math.min(defaultPrecision, 8); // авто-режим
        }

        try {
            int precision = Integer.parseInt(input);
            if (precision < 0) {
                System.out.println("⚠️ Точность не может быть отрицательной. Использую авто.");
                return Math.min(defaultPrecision, 8);
            }
            if (precision > 12) {
                System.out.println("⚠️ Слишком большое число, ограничено 12 знаками.");
                return 12;
            }
            return precision;
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Некорректный ввод, использую авто.");
            return Math.min(defaultPrecision, 8);
        }
    }
}
