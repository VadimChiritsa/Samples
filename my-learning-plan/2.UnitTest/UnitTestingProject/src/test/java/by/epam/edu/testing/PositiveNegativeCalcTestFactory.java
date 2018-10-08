package by.epam.edu.testing;

import org.testng.annotations.Factory;

/**
 * Created by Aliaksandr_Sheliutsi on 1/17/2017.
 */
public class PositiveNegativeCalcTestFactory {
    @Factory
    public Object [] getTests() {
        return new Object[] {
                new PositiveNegativeCalcTest(10, true, false),
                new PositiveNegativeCalcTest(0, false, false),
                new PositiveNegativeCalcTest(-1, false, true),
                new PositiveNegativeCalcTest(-1000, false, true)
        };
    }
}
