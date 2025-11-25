package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Создаем наши объекты-помощники
        Calculator calculator = new Calculator();
        InputUtils inputUtils = new InputUtils(scanner);

        System.out.println("=== Добро пожаловать в Калькулятор ===");

        while (true) {
            // 1. Получение данных
            String strNum1 = inputUtils.readCleanInput("Введите число 1: ");
            String strNum2 = inputUtils.readCleanInput("Введите число 2: ");

            double num1 = Double.parseDouble(strNum1);
            double num2 = Double.parseDouble(strNum2);

            int prec1 = inputUtils.getDecimalPlaces(strNum1);
            int prec2 = inputUtils.getDecimalPlaces(strNum2);

            // 2. Выбор операции
            char operation = inputUtils.getOperation();

            // 3. Вычисление и определение формата
            Double result = null;
            int finalPrecision = 0;
            try {
                switch (operation) {
                    case '+':
                        result = calculator.add(num1, num2);
                        finalPrecision = Math.max(prec1, prec2);
                        break;
                    case '-':
                        result = calculator.subtract(num1, num2);
                        finalPrecision = Math.max(prec1, prec2);
                        break;
                    case '*':
                        result = calculator.multiply(num1, num2);
                        finalPrecision = inputUtils.askManualPrecision(prec1 + prec2);
                        break;
                    case '/':
                        result = calculator.divide(num1, num2);
                        finalPrecision = inputUtils.askManualPrecision(prec1 + prec2 + 2);
                        break;
                    default:
                        System.out.println("❌ Неизвестная операция!");
                }
            } catch (ArithmeticException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            // 4. Вывод результата
            if (result != null) {
                String format = "%." + finalPrecision + "f%n";
                System.out.printf("Результат: " + format, result);
            }

            // 5. Проверка на выход
            if (!inputUtils.askToContinue()) {
                System.out.println("Программа завершена.");
                break;
            }
        }
        scanner.close();
    }
}