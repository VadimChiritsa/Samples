package by.epam.edu.testing;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.epam.tat.module4.Calculator;;

public class BaseCalculatorTest {
	protected Calculator calc;
	
	@BeforeClass(groups = "Preparation")
	public void SetUp(){
		calc = new Calculator();
	}

	@AfterSuite(groups = "Preparation")
	public void Dispose(){
		System.out.println("All tests have ran.");
	}
	
	
}
