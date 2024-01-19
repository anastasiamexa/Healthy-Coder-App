package test.java.com.healthycoderapp;

import main.java.com.healthycoderapp.BMICalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {
    @Test
    void should_ReturnTrue_When_DietRecommended() {
        // given
        double weight = 89.0;
        double height = 1.72;

        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // then
        assertTrue(recommended);
    }

    @Test
    void should_ReturnFalse_When_DietRecommended() {
        // given
        double weight = 50.0;
        double height = 1.92;

        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // then
        assertFalse(recommended);
    }
}
