package org.example;

import java.util.Scanner;

public class InputUtils {
    private final Scanner scanner;

    public InputUtils(Scanner scanner) {
        this.scanner = scanner;
    }

    // Чтение, очистка и валидация ввода числа (возвращает строку для анализа точности)
    public String readCleanInput(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            // Оставляем цифры, точки, запятые, минус
            String cleaned = input.replaceAll("[^0-9.,-]", "");
            cleaned = fixMultipleDots(cleaned);
            cleaned = cleaned.replace(',', '.');

            if (isValidNumber(cleaned)) {
                if (!cleaned.equals(input)) {
                    System.out.println("⚠️ Введено с опечатками, использую число: " + cleaned);
                }
                return cleaned;
            } else {
                System.out.println("❌ Введите корректное число!");
            }
        }
    }

    public char getOperation() {
        System.out.print("Выберите операцию (+, -, *, /): ");
        char op = scanner.next().charAt(0);
        scanner.nextLine(); // Очистка буфера
        return op;
    }

    public int askManualPrecision(int defaultPrecision) {
        System.out.print("Введите точность (Enter — авто): ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) return Math.min(defaultPrecision, 8);

        try {
            int precision = Integer.parseInt(input);
            if (precision < 0) return Math.min(defaultPrecision, 8);
            return Math.min(precision, 12); // Ограничение макс точности
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Некорректный ввод, использую авто.");
            return Math.min(defaultPrecision, 8);
        }
    }

    public boolean askToContinue() {
        System.out.print("Хотите продолжить? (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        System.out.println();
        return !answer.equals("n");
    }

    // Утилитные (вспомогательные) методы
    public int getDecimalPlaces(String numberStr) {
        int index = numberStr.indexOf('.');
        return (index == -1) ? 0 : numberStr.length() - index - 1;
    }

    private boolean isValidNumber(String str) {
        if (str.isEmpty() || str.equals("-") || str.equals(".")) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String fixMultipleDots(String input) {
        int firstDot = input.indexOf('.');
        int firstComma = input.indexOf(',');
        int firstSeparator = -1;

        if (firstDot != -1 && firstComma != -1) firstSeparator = Math.min(firstDot, firstComma);
        else if (firstDot != -1) firstSeparator = firstDot;
        else if (firstComma != -1) firstSeparator = firstComma;

        if (firstSeparator != -1) {
            String before = input.substring(0, firstSeparator + 1);
            String after = input.substring(firstSeparator + 1).replaceAll("[.,]", "");
            return before + after;
        }
        return input;
    }
}