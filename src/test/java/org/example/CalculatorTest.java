package org.example;

// Импортируем инструменты JUnit
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    // Создаем экземпляр калькулятора, который будем проверять
    private final Calculator calculator = new Calculator();

    @Test // Эта аннотация говорит Java, что метод является тестом
    @DisplayName("Проверка сложения") // Красивое имя для отчета
    void testAdd() {
        double result = calculator.add(2.0, 3.0);
        // assertEquals(ожидание, реальность, сообщение_об_ошибке)
        assertEquals(5.0, result, "2 + 3 должно равняться 5");
    }

    @Test
    @DisplayName("Проверка вычитания")
    void testSubtract() {
        double result = calculator.subtract(10.0, 4.5);
        // Для double часто добавляют третий параметр - погрешность (delta), например 0.0001
        assertEquals(5.5, result, 0.0001, "10 - 4.5 должно равняться 5.5");
    }

    @Test
    @DisplayName("Проверка умножения")
    void testMultiply() {
        assertEquals(6.0, calculator.multiply(2.0, 3.0), "2 * 3 должно равняться 6");
        assertEquals(0.0, calculator.multiply(5.0, 0.0), "Умножение на 0 должно давать 0");
    }

    @Test
    @DisplayName("Проверка деления")
    void testDivide() {
        assertEquals(2.0, calculator.divide(10.0, 5.0), "10 / 5 должно равняться 2");
    }

    @Test
    @DisplayName("Проверка ошибки при делении на ноль")
    void testDivideByZero() {
        // Мы ожидаем, что этот код выбросит ошибку ArithmeticException
        assertThrows(ArithmeticException.class, () -> {
            calculator.divide(10.0, 0.0);
        }, "Деление на ноль должно выбрасывать ошибку");
    }
}