package by.epam.edu.autoframework.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Aliaksandr_Sheliutsi on 1/25/2017.
 */
public class DataProvider {
    public static DataProvider dataProvider;

    private String filePath = "src/test/resources/email.properties";
    private Properties prop = new Properties();
    private FileInputStream inputStream;


    private DataProvider() {
        try {
            inputStream = new FileInputStream(filePath);
            if (inputStream != null) {
                prop.load(inputStream);
            } else throw new FileNotFoundException();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Loading stream:" + e.getStackTrace().toString());
        }
    }


    public static DataProvider getInstance() {
        if(dataProvider == null) dataProvider = new DataProvider();
        return dataProvider;
    }

    public void closeProvider() {
        inputStream = null;
    }

    public String getProperty(String propName) {
        return prop.getProperty(propName);
    }

}
