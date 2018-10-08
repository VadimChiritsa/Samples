package by.epam.edu.testing;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by Aliaksandr_Sheliutsi on 1/17/2017.
 */
public class SqrtPowCalcTest extends BaseCalculatorTest {

    @Test(dataProvider = "sqrtDP")
    public void SqrtTest(double a) {
        assertEquals(calc.sqrt(a), Math.sqrt(a),
                "Invalid result of sqrt:");
    }
    @DataProvider(name = "sqrtDP")
    public Object[][] sqrtDataProvider(){
        return new Object[][]{
                {121},
                {0},
                {6.25}};
    }
    @Test(expectedExceptions = NumberFormatException.class)
    public void sqrtCrushTest(){
        calc.sqrt(-9);
    }

    @Test(dataProvider = "powDP")
    public void PowTest(double a, double b) {
        assertEquals(calc.pow(a,b), Math.pow(a,b),
                "Invalid result of pow:");
    }
    @DataProvider(name = "powDP")
    public Object[][] powDataProvider(){
        return new Object[][]{
                {3,3},
                {10, 0},
                {9, 2.5},
                {0, 5},
                {-3, 2},
                {-2.5, 3},
                {0, -1},
                {2, -1}};
    }
}
