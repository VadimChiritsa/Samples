package by.epam.edu.testing.runner;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aliaksandr_Sheliutsi on 1/17/2017.
 */
public class CalcTestRunner {
    public static void main(String[] args) {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG tng = new TestNG();
        tng.addListener(tla);

        XmlSuite suite = new XmlSuite();
        suite.setName("ParallelSuite");

        List<String> files = new ArrayList<String>();
        files.add(".//src//test//resources//suites//calc.xml");
        suite.setSuiteFiles(files);

        suite.setParallel(XmlSuite.PARALLEL_CLASSES);
        suite.setThreadCount(2);

        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        tng.setXmlSuites(suites);
        tng.run();
    }
}
