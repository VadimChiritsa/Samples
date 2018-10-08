package by.epam.edu.testing;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by Aliaksandr_Sheliutsi on 1/17/2017.
 */
public class MultDivCalcTest extends BaseCalculatorTest {

    @Test(dataProvider = "multDP")
    public void MultTest(long a, long b){
        assertEquals(calc.mult(a, b), a * b,
                "Invalid result of multiplication:");
    }

    @DataProvider(name = "multDP")
    public Object[][] multDataProvider(){
        return new Object[][]{
                {4, 5},
                {3,	-2},
                {-3, -4},
                {-3, 0}};
    }

    @Test(dataProvider = "doubleMultDP")
    public void doubleMultTest(double a, double b){
        assertEquals(calc.mult(a, b), a * b,
                "Invalid result of multiplication:");
    }

    @DataProvider(name = "doubleMultDP")
    public Object[][] doubleMultDataProvider(){
        return new Object[][]{
                {4.1, 5.5},
                {3.6, -2.0},
                {-3.9, -4.8,},
                {-3.222, 0}};
    }

    @Test(dataProvider = "divDP")
    public void DivTest(long a, long b){
        assertEquals(calc.div(a, b), a / b,
                "Invalid result of division:");
    }
    @DataProvider(name = "divDP")
    public Object[][] divDataProvider(){
        return new Object[][]{
                {20,	5},
                {-8,	4},
                {-6,    -2},
                {7,    -3},//Not actually sure about
                {9,    5},//such tests
                {0,	-3}};
    }
    @Test(expectedExceptions = NumberFormatException.class)
    public void DivCrushTest(){
        calc.div(5,0);
    }


    @Test(dataProvider = "doubleDivDP")
    public void doubleDivTest(double a, double b){
        assertEquals(calc.div(a, b), a / b,
                "Invalid result of division:");
    }

    @DataProvider(name = "doubleDivDP")
    public Object[][] doubleDivDataProvider(){
        return new Object[][]{
                {4.4, 2.2},
                {5,	0},
                {6.8, -4.0},
                {-23.76, -3.3},
                {0, -9.1}};
    }
}
