package by.epam.edu.testing;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class SumSubCalcTest extends BaseCalculatorTest {
	
	@Test(dataProvider = "sumDP")
	public void SumTest(long a, long b){
		assertEquals(calc.sum(a, b), a + b,
				"Invalid result of sum:");
	}

	@Test(dataProvider = "subDP")
	public void SubTest(long a, long b) {
		assertEquals(calc.sub(a, b), a - b,
				"Invalid result of div:");

	}
	@Test(dataProvider = "doubleSumDP")
	public void doubleSumTest(double a, double b){
		assertEquals(calc.sum(a, b), a + b ,
				"Invalid result of sum:");
	}

	@Test(dataProvider = "doubleSubDP")
	public void doubleSubTest(double a, double b) {
		assertEquals(calc.sub(a, b), a - b,
				"Invalid result of div:");

	}




	@DataProvider(name = "sumDP")
	public Object[][] sumDataProvider(){
		return new Object[][]{
				{1,	2},
				{3,	-2},
				{-3,-4},
				{3,	0}};
	}
	

	@DataProvider(name = "subDP")
	public Object[][] subDataProvider(){
		return new Object[][]{
				{1,	2},
				{3,	-2},
				{-3,-4},
				{8,	0},
				{3,	3}};
	}

    @DataProvider(name = "doubleSumDP")
    public Object[][] doubleSumDataProvider(){
        return new Object[][]{
                {1.6, 2.7},
                {3.1, -2.2},
                {-3.0, -4.0},
                {3.4, 0}};
    }


    @DataProvider(name = "doubleSubDP")
    public Object[][] doubleSubDataProvider(){
        return new Object[][]{
                {1.4, 2.2},
                {3.6, -2.9},
                {-3.1, 0.1},
                {8.6, 0},
                {3.1, 3.1}};
    }
}

