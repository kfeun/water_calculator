import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {
    @Test
    void calculateWaterAmount() {

        int[] landscape = new int[]{1, 0, 3, 0, 2, 0, 4, 1, 4, 2};
        int waterAmount = Calculator.calculateWaterAmount(landscape);

        Assertions.assertEquals(11, waterAmount);

    }

}