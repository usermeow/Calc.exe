package org.example;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Calculator")
@Feature("Basic operations")
@Owner("QA Trainee") // Можно указать себя :)
class CalculatorTest {

    @Test
    @Story("Addition")
    @Description("Проверяем, что калькулятор правильно складывает два числа")
    @Severity(SeverityLevel.CRITICAL)
    void testAddition() {
        Calculator calculator = new Calculator();

        double result = performCalculation(calculator, 2.5, 3.5, '+', 2, 2, 0);
        verifyResult(result, 6.0);
    }

    @Test
    @Story("Division")
    @Description("Проверяем деление чисел с плавающей точкой с точностью до 5 знаков")
    @Severity(SeverityLevel.NORMAL)
    void testDivision() {
        Calculator calculator = new Calculator();

        double result = performCalculation(calculator, 23.11, 23.22, '/', 2, 2, 5);
        verifyResult(result, 0.99526, 0.00001);
    }

    // ---------- STEPS ----------

    @Step("Выполняем операцию {op} над {a} и {b}")
    double performCalculation(Calculator calculator, double a, double b, char op,
                              int aDecimals, int bDecimals, int manualPrecision) {
        return calculator.calculate(a, b, op, aDecimals, bDecimals, manualPrecision);
    }

    @Step("Проверяем, что результат равен {expected}")
    void verifyResult(double actual, double expected) {
        assertEquals(expected, actual);
    }

    @Step("Проверяем, что результат равен {expected} (с допуском {delta})")
    void verifyResult(double actual, double expected, double delta) {
        assertEquals(expected, actual, delta);
    }
}