package by.epam.edu.testing;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by Aliaksandr_Sheliutsi on 1/17/2017.
 */
public class TrigonometryTest extends BaseCalculatorTest{

    @Test(dataProvider = "sinDP")
    public void SinTest(double a){
        assertEquals(calc.sin(a), Math.sin(a),
                "Invalid result of sin:");
    }
    @DataProvider(name = "sinDP")
    public Object[][] sinDataProvider(){
        return new Object[][]{
                {Math.PI/6},
                {0},
                {Math.PI/4},
                {Math.PI * -1},
                {Math.PI * 4}};
    }

    @Test(dataProvider = "cosDP")
    public void CosTest(double a){
        assertEquals(calc.cos(a), Math.cos(a),
                "Invalid result of cos:");
    }
    @DataProvider(name = "cosDP")
    public Object[][] cosDataProvider(){
        return new Object[][]{
                {Math.PI/6},
                {0},
                {Math.PI/4},
                {Math.PI * -1},
                {Math.PI * 4}};
    }

    @Test(dataProvider = "tgDP")
    public void tgTest(double a){
        assertEquals(calc.tg(a), Math.tan(a),
                "Invalid result of tg:");
    }

    @DataProvider(name = "tgDP")
    public Object[][] tgDataProvider(){
        return new Object[][]{
                {Math.PI/6},
                {Math.PI/4},
                {Math.PI/2},
                {Math.PI * -1},
                {Math.PI * 3}};
    }


    @Test(dataProvider = "ctgDP")
    public void ctgTest(double a){
        assertEquals(calc.ctg(a), 1 / Math.tanh(a),
                "Invalid result of ctg:");
    }

    @DataProvider(name = "ctgDP")
    public Object[][] ctgDataProvider(){
        return new Object[][]{
                {Math.PI/6},
                {Math.PI/4},
                {0},
                {Math.PI * -1},
                {Math.PI * 1.5},
                {Math.PI * 3}};
    }


}
