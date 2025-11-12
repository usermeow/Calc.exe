package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    void testAddition() {
        double result = calculator.calculate(2.5, 3.5, '+', 1, 1, 0);
        assertEquals(6.0, result);
    }

    @Test
    void testDivision() {
        double result = calculator.calculate(23.11, 23.22, '/', 2, 2, 5);
        assertEquals(0.99526, result, 0.00001);
    }

    @Test
    void testDivisionByZero() {
        Double result = calculator.calculate(10, 0, '/', 1, 1, 0);
        assertNull(result);
    }

    @Test
    void testInvalidOperator() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(5, 3, '%', 1, 1, 0));
    }
}