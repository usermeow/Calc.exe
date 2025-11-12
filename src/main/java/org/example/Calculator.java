package org.example;

public class Calculator {

    // Основной метод вычислений
    public Double calculate(double num1, double num2, char op, int precision1, int precision2, int manualPrecision) {
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
                outputPrecision = manualPrecision > 0 ? manualPrecision : (precision1 + precision2);
                break;
            case '/':
                if (num2 == 0) {
                    return null; // деление на ноль
                }
                result = num1 / num2;
                outputPrecision = manualPrecision > 0 ? manualPrecision : (precision1 + precision2 + 2);
                break;
            default:
                throw new IllegalArgumentException("Неизвестная операция: " + op);
        }

        // округляем результат
        return round(result, outputPrecision);
    }

    // Подсчёт знаков после запятой
    public int getDecimalPlaces(double number) {
        String text = String.valueOf(number);
        int index = text.indexOf('.');
        if (index == -1) return 0;
        return text.length() - index - 1;
    }

    // Округление числа
    public double round(double value, int precision) {
        double factor = Math.pow(10, precision);
        return Math.round(value * factor) / factor;
    }
}
