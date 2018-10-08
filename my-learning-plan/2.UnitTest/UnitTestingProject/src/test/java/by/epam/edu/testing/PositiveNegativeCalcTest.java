package by.epam.edu.testing;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
/**
 * Created by Aliaksandr_Sheliutsi on 1/17/2017.
 */
public class PositiveNegativeCalcTest extends BaseCalculatorTest {
    private long value;
    private boolean expectedToBePositive;
    private boolean expectedToBeNegative;

    public PositiveNegativeCalcTest(long value, boolean expectedToBePositive, boolean expectedToBeNegative) {
        this.value = value;
        this.expectedToBePositive = expectedToBePositive;
        this.expectedToBeNegative = expectedToBeNegative;
    }

    @Test
    public void checkValuePositive() {
        assertEquals(calc.isPositive(value), expectedToBePositive);
    }

    @Test
    public void checkValueNegative() {
        assertEquals(calc.isNegative(value), expectedToBeNegative);
    }
}
