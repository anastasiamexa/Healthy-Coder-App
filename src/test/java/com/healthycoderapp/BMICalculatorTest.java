package test.java.com.healthycoderapp;

import main.java.com.healthycoderapp.BMICalculator;
import main.java.com.healthycoderapp.Coder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {
    // Runs before all tests only once
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all unit tests");
    }

    // Runs after all tests only once
    @AfterAll
    static void afterAll() {
        System.out.println("After all unit tests");
    }

    @ParameterizedTest(name= "weight={0}, height={1}")
    @CsvFileSource(resources = "/test/resources/diet-recommended-input-data.csv", numLinesToSkip = 1)
    void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {
        // given
        double weight = coderWeight;
        double height = coderHeight;

        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // then
        assertTrue(recommended);
    }

    @ParameterizedTest(name= "weight={0}, height={1}")
    @CsvSource(value = {"63.0, 1.72", "65.0, 1.75", "70.0, 1.78"})
    void should_ReturnFalse_When_DietRecommended(Double coderWeight, Double coderHeight) {
        // given
        double weight = coderWeight;
        double height = coderHeight;

        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // then
        assertFalse(recommended);
    }

    @Test
    void should_ThrowArithmeticException_When_HeightZero() {
        // given
        double weight = 50.0;
        double height = 0.0;

        // when
        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

        // then
        assertThrows(ArithmeticException.class, executable);
    }

    @Test
    void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
        // given
        Coder coder1 = new Coder(1.80, 60.0);
        Coder coder2 = new Coder(1.82, 98.0);
        Coder coder3 = new Coder(1.82, 64.7);

        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(List.of(new Coder[]{coder1, coder2, coder3}));

        // then
        assertAll("coders", // If any of the assertions fail, the test will report all the failures.
                () -> assertEquals(1.82, coderWorstBMI.getHeight()), // or assertSame
                () -> assertEquals(98.0, coderWorstBMI.getWeight())
        );
    }

    @Test
    void should_ReturnNullWorstBMI_When_CoderListEmpty() {
        // given
        List<Coder> coders = List.of();

        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then
        assertNull(coderWorstBMI);
    }

    @Test
    void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
        // given
        Coder coder1 = new Coder(1.80, 60.0);
        Coder coder2 = new Coder(1.82, 98.0);
        Coder coder3 = new Coder(1.82, 64.7);
        List<Coder> coders = List.of(coder1, coder2, coder3);
        double[] expected = {18.52, 29.59, 19.53};

        // when
        double[] bmiScores = BMICalculator.getBMIScores(coders);

        // then
        assertArrayEquals(expected, bmiScores);
    }

    @Test
    void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements() {
        // given
        List<Coder> coders = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            coders.add(new Coder(1.0 + i, 10.0 + i));
        }

        // when
        Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

        // then
        assertTimeout(Duration.ofMillis(500), executable);
    }
}
